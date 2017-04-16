package main.java.chat;

import java.util.ArrayList;
import java.util.Map;

public class Message {
	String content;
	int id;
	
	User sender;
	Map<User, Boolean> receivers;
	
	Message(String msg, User sndr, ArrayList<User> userInMsg){
		this.content = msg;
		this.id = this.content.hashCode();
		this.sender = sndr;
		for (User usr: userInMsg){
			this.receivers.put(usr, false);
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

	public Map<User, Boolean> getReceivers() {
		return receivers;
	}

	public void setReceivers(Map<User, Boolean> receivers) {
		this.receivers = receivers;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + "]";
	}	
	
}
