package main.java.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ActiveChatUserManager {
	
	ArrayList<User> activeUsers; 
	ArrayList<Chat> activeChat;

	Map<String, ArrayList<User>> chatIdToUsers; // chat_id to ArrayList of users in this chat
	Map<Integer, Chat> usersToChat; // hash(ArrayList of Users in a chat) to this chat
	Map<Integer, Map<User, Boolean>> msgToUserStatus; 
	
	ActiveChatUserManager(){
		this.activeUsers = new ArrayList<User>();
		this.activeChat = new ArrayList<Chat>();
		
		this.chatIdToUsers = new HashMap<String, ArrayList<User>>(); 
		this.usersToChat = new HashMap<Integer, Chat>();
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
		
		if (this.usersToChat.containsKey(chatUsersHash)){
			Chat tempChat = this.usersToChat.get(chatUsersHash);
			newChatResponse.put("status", String.valueOf(0));
			newChatResponse.put("chat_id", tempChat.getId());
			newChatResponse.put("all_users", tempChat.getUsers().toString());
		}else{
			String chatID = String.valueOf(this.chatIdToUsers.size());
			Chat newChat = new Chat(chatID, allUsers);
			this.usersToChat.put(chatUsersHash, newChat);
			this.activeChat.add(newChat);
			this.chatIdToUsers.put(chatID, allUsers);
			
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

	public Map<String, String> joinExistingChat(String uid, String cid) {
		Map<String, String> joinChatResponse = new HashMap<String, String>();
		boolean chatExist = this.chatIdToUsers.containsKey(cid);
		if (!chatExist){
			System.out.println("Chat does not exist and chat id is" + cid);
			return null;
		} else{
			User newUser = new User(uid);
			if (chatExist && this.chatIdToUsers.get(cid).contains(newUser)){
				joinChatResponse.put("status", String.valueOf(0));
				joinChatResponse.put("chat_id", cid);
				joinChatResponse.put("all_users", this.chatIdToUsers.get(cid).toString());
			} else{
				
				int oldUsersHash = this.getChatUsersHash(this.chatIdToUsers.get(cid));
						
				// update chatIdToUsers structure							
				this.chatIdToUsers.get(cid).add(newUser);				
				System.out.println("after adding a new user..." + this.chatIdToUsers.get(cid).toString());
				
				int newUsersHash = this.getChatUsersHash(this.chatIdToUsers.get(cid));		
				Chat oldChat = this.usersToChat.get(oldUsersHash);

				this.usersToChat.put(newUsersHash, oldChat);				
				this.usersToChat.remove(oldUsersHash); 
				
				joinChatResponse.put("status", String.valueOf(1));
				joinChatResponse.put("chat_id", cid);
				joinChatResponse.put("all_users", this.chatIdToUsers.get(cid).toString());
			}
		}

		return joinChatResponse;
	}

	public boolean sendNewMessage(String uid, String cid, String msg) {
		ArrayList<User> userInChat = this.chatIdToUsers.get(cid);
		int chatUsersHash = this.getChatUsersHash(userInChat);
		Chat chat = this.usersToChat.get(chatUsersHash);
		Message newMsg = new Message(msg, new User(uid), userInChat);
		if (chat.messages.add(newMsg)) return true;
		else return false;
	}

	public ArrayList<Message> pullNewMessage(String cid, String uid) {
		int chatUsersHash = this.getChatUsersHash(this.chatIdToUsers.get(cid));
		Chat chat = this.usersToChat.get(chatUsersHash);
		ArrayList<Message> unreadMessages = new ArrayList<Message>();
		for (Message msg : chat.getMessages()){
			System.out.println(msg.content);
			System.out.println(msg.receivers.toString());
			System.out.println(uid);
			System.out.println(msg.receivers.get(uid));
			boolean isRead = msg.receivers.get(uid);
			System.out.println(isRead);
			if (! isRead){
				unreadMessages.add(msg);
				msg.receivers.replace(uid, true);
			}
			System.out.print("This should be true...");
			System.out.println(msg.getReceivers().get(uid));
		}
		return unreadMessages;
	}
	
}
