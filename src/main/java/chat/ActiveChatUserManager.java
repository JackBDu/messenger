package main.java.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ActiveChatUserManager {
	
	ArrayList<User> activeUsers; 
	ArrayList<Chat> activeChat;
	
	Map<Integer, String> msgMapping;
	Map<String, ArrayList<User>> chatToUsers; // chat_id to ArrayList of users
	Map<Integer, ArrayList<Message>> usersToMessage; // hash(ArrayList of Users) to ArrayList of Message
	Map<Integer, Map<User, Boolean>> msgToUserStatus; 
	
	ActiveChatUserManager(){
		this.activeUsers = new ArrayList<User>();
		this.activeChat = new ArrayList<Chat>();
		
		this.msgMapping = new HashMap<Integer, String>();
		this.chatToUsers = new HashMap<String, ArrayList<User>>(); 
		this.usersToMessage = new HashMap<Integer, ArrayList<Message>>();
		this.msgToUserStatus = new HashMap<Integer, Map<User, Boolean>>();
	}
	
	void addUser(String uid) {
		this.activeUsers.add(new User(uid));	
	}
	
	boolean findUser(String uid) {
		for (User usr:this.activeUsers){
			if (usr.name.equals(uid)){
				return true;
			}
		}
		return false;	
	}
	
	public ArrayList<String> getActiveUsers() {
		ArrayList<String> ausers = new ArrayList<String>();
		for (User u: this.activeUsers){
			ausers.add(u.name);
		}
		return ausers;
	}

	public ArrayList<String> getActiveChat() {
		ArrayList<String> achat = new ArrayList<String>();
		for (Chat c: this.activeChat){
			achat.add(c.id);
		}
		return achat;
	}

	Map<String, ArrayList<String>> getActiveList(){
		Map<String, ArrayList<String>> activeList = new HashMap<String, ArrayList<String>>();
		activeList.put("user_list", this.getActiveUsers());
		activeList.put("chat_list", this.getActiveChat());
		return activeList;
	}


	Map<String, String> createNewChat(String uid, String invited_user) {
		Map<String, String> newChatResponse = new HashMap<String, String>();
		ArrayList<User> allUsers = this.getAllUsers(invited_user, uid);		
		Integer chatUsersHash = this.getChatUsersHash(allUsers);
		if (this.usersToMessage.containsKey(chatUsersHash)){
			newChatResponse.put("status", String.valueOf(0));
			newChatResponse.put("chat_id", String.valueOf(-1));
			newChatResponse.put("all_users", "");
		}else{
			String chatID = String.valueOf(this.chatToUsers.size());
			this.chatToUsers.put(chatID, allUsers);
			this.usersToMessage.put(chatUsersHash, new ArrayList<Message>());
			this.activeChat.add(new Chat(chatID, allUsers));
			
			newChatResponse.put("status", String.valueOf(1));
			newChatResponse.put("chat_id", chatID);
			newChatResponse.put("all_users", allUsers.toString());		
		}
		System.out.println(newChatResponse.toString());
		
		return newChatResponse;

	}
	
	private ArrayList<User> getAllUsers (String chatUserString, String uid){
		ArrayList<User> usrs = new ArrayList<User>();
		String[] inv_usr = chatUserString.split(",");
		for (String usr_id:inv_usr){
			User usr = new User(usr_id);
			usrs.add(usr);
		}
		User init_user = new User(uid);
		usrs.add(init_user);
		usrs.sort(null);
		return usrs;
	}
	
	private Integer getChatUsersHash(ArrayList<User> chatUsers){
		return chatUsers.toString().hashCode();
	}
	
}
