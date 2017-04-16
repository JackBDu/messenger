package main.java.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Message {
	String content;
	int id;
	
	User sender;
	Map<String, Boolean> receivers;
	
	Message(String msg, User sndr, ArrayList<User> userInMsg){
		this.content = msg;
		this.id = this.content.hashCode();
		this.sender = sndr;
		this.receivers = new HashMap<String, Boolean>();
		for (User usr: userInMsg){
			this.receivers.put(usr.name, false);
		}		
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Map<String, Boolean> getReceivers() {
		return receivers;
	}

	public void setReceivers(Map<String, Boolean> receivers) {
		this.receivers = receivers;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + "]";
	}	
	
}
