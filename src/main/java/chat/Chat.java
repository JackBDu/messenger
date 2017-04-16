package main.java.chat;

import java.util.ArrayList;

public class Chat {
	String id;
	ArrayList<User> users;
	
	Chat(String cid, ArrayList<User> usrs){
		this.id = cid;
		this.users = usrs;
	}

	public String getId() {
		return id;
	}
	
}
