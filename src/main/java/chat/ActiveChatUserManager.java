package main.java.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActiveChatUserManager {
	
	ArrayList<String> activeUsers;
	ArrayList<String> activeChat;
	Map<Integer, ArrayList<String>> chatUser;
	Map<ChatUsers, ArrayList<String>> chatContent;
	
	public ActiveChatUserManager(){
		this.activeUsers = new ArrayList<String>();
		this.activeChat = new ArrayList<String>();
	}
	
	public ActiveChatUserManager(ArrayList<String> au, ArrayList<String> ac){
		this.activeUsers = au;
		this.activeChat = ac;
		this.chatUser = new HashMap<Integer, ArrayList<String>>();
	}
	
	public void addUser(String uid) {
		this.activeUsers.add(uid);	
	}
	
	public Map<String, ArrayList<String>> getActiveList(){
		Map<String, ArrayList<String>> activeList = new HashMap<String, ArrayList<String>>();
		activeList.put("user_list", this.activeUsers);
		activeList.put("chat_list", this.activeChat);
		return activeList;
	}

	public boolean findUser(String uid) {
		for (String usr:this.activeUsers){
			if (usr.equals(uid)){
				return true;
			}
		}
		return false;	
	}

	public Map<String, Integer> createNewChat(String uid, String invited_user) {
		Map<String, Integer> newChatResponse = new HashMap<String, Integer>();
		ArrayList<String> usrs = new ArrayList<String>();
		String[] inv_usr = invited_user.split(",");
		for (String usr:inv_usr){
			usrs.add(usr);
		}
		int chatID = this.chatUser.size();
		usrs.add(uid);
		usrs.sort(null);
		System.out.println(usrs.toString());
//		if (this.chatUser.values()){
//			
//		}
		this.chatUser.put(chatID, usrs);
		this.activeChat.add(Integer.toString(chatID));
		newChatResponse.put("status", 1);
		newChatResponse.put("chat_id", chatID);
		System.out.println(this.chatUser.get(chatID).toString());
		
		return newChatResponse;
	}
	
}
