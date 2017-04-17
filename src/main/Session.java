package main;

import java.util.ArrayList;
import main.Client;

public class Session {
	public String username;
	public String password;
	public ArrayList<Client> users = new ArrayList<Client>();
	
	public Session(String usern, String passw) {
		username = usern;
		password = passw;
		System.out.println("Session created. username: " + usern + " password: " + passw);
	}
	
	public boolean addUser(String ip, String machineip) {
		boolean userExists = false;
		for (Client u : users) {
			if (ip.indexOf(u.ip) != -1) {
				if (machineip.indexOf(u.localip) != -1) {
					userExists = true;
					System.out.println("Client already connected");
					break;
				}
			}
		}
		if (!userExists) {
			System.out.println("Added " + ip + " to session " + username);
			users.add(new Client(ip, machineip));
			return true;
		} else {
			return false;
		}
	}
	
	public void removeUser(String ip, String machineip) {
		for (Client u : users) {
			if (ip.indexOf(u.ip) != -1) {
				if (machineip.indexOf(u.localip) != -1) {
					users.remove(u);
					break;
				}
			}
		}
	}
	
	public void updatePos(String ip, String machineip, String xpos, String ypos, String rotation) {
		for (Client c : users) {
			if (c.ip.contains(ip)) {
				c.x = Integer.parseInt(xpos);
				c.y = Integer.parseInt(ypos);
				c.rotation = Double.parseDouble(rotation);
			}
		}

	}
	
	public String getData(String clientIp, String machineip) {
		int i = 0;
		String returnData = "";
		for (Client c : users) {
					returnData += "client:" + c.ip + "*" + c.localip + "*" + c.x + "*" + c.y + "*" + c.rotation + "|";
		}
		return returnData;
	}
	
}
