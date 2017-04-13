package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server {
	public static int port = 4444; //de standaard port voor de server
	public static ArrayList<Connection> connections = new ArrayList();
	public static ArrayList<Session> sessions = new ArrayList();
	
	//Waardes voor debugging
	public static boolean logpackets = false;
	
	public static void main(String[] args) throws IOException {
		for (String s : args) {
		      if (s.indexOf("-port:") != -1) {
		        port = Integer.parseInt(s.substring(s.indexOf(":") + 1));
		      }
		      if (s.indexOf("-log") != -1) {
		    	logpackets = true;
			  }
		}
		System.out.println("Starting server...");
		new Server().runServer();
	}
	
	public void runServer() throws IOException {
		ServerSocket server = new ServerSocket(port);//Maak een nieuwe server socket aan.
		System.out.println("");
		System.out.println("Started server @");
		System.out.println("local IP:   " + getIp(true));
		System.out.println("ext. IP:    " + getIp(false) + ":" + port);
		System.out.println("");
		
		while (true) {
			Socket socket = server.accept();	//De code staat hier stil, totdat er een verbinding wordt geopend.
			new Connection(socket).start();	//Hier wordt een nieuwe thread gemaakt waarmee dan gepraat kan worden met deze client
		}
	}
		
	
	//Een method om je externe en interne IP te krijgen, zodat een andere gebruiker weet waarmee hij/zij kan verbinden.
	public String getIp(boolean local) throws UnknownHostException, IOException {
		if (local) {
			//intern ip wordt via standaard InetAddress methods opgehaald
			return InetAddress.getLocalHost().getHostAddress();
		} else {
			//extern ip wordt via een amazon API opgevraagd
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			return in.readLine();
		}
	}

	
}
