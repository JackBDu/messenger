package main.java.chat;

import java.util.ArrayList;

public class Chat {
	String id;
	ArrayList<User> users;
	ArrayList<Message> messages;
	
	Chat(String cid, ArrayList<User> usrs){
		this.id = cid;
		this.users = usrs;
		this.messages = new ArrayList<Message>();
	}

	public String getId() {
		return id;
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public ArrayList<Message> getMessages(){
		return messages;
	}
	
	public boolean updateUsers(User u){
		if (this.users.add(u)) return true;
		else return false;
	}
}
