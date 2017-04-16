package main.java.chat;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {
	
	ActiveChatUserManager activeChatUserManager = new ActiveChatUserManager();
	 
    @RequestMapping(value="/signin", method=RequestMethod.GET)
    @ResponseBody
    public int signIn(@RequestParam(value="user_id", required=true) String uid) {   	
    	return activeChatUserManager.findUser(uid)? 1:0;
    }
    
    @RequestMapping(value="/signup", method=RequestMethod.GET)
    @ResponseBody   
    public int signUp(@RequestParam(value="user_id", required=true) String uid){
    	boolean hasUser = activeChatUserManager.findUser(uid);
    	if (hasUser){
    		return 0;
    	}else{
    		activeChatUserManager.addUser(uid);
    		return 1;
    	}   	
    }
    
    @RequestMapping(value="/active_list", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, ArrayList<String>> getActiveList(){
    	return activeChatUserManager.getActiveList();
    }
    
    @RequestMapping(value="/create_chat", method=RequestMethod.GET)
    @ResponseBody   
    public Map<String, String> createNewChat(@RequestParam(value="user_id", required=true) String uid,
    		@RequestParam(value="invited", required=true) String invited_user){
    	
    	return activeChatUserManager.createNewChat(uid, invited_user);
    }
    
    @RequestMapping(value="/join_chat", method=RequestMethod.GET)
    @ResponseBody   
    public Map<String, String> joinChat(@RequestParam(value="user_id", required=true) String uid,
    		@RequestParam(value="chat_id", required=true) String cid){
    	return activeChatUserManager.joinExistingChat(uid, cid);
    }
    
    @RequestMapping(value="/new_msg", method=RequestMethod.GET)
    @ResponseBody
    public boolean sendNewMessage(@RequestParam(value="user_id", required=true) String uid,
    		@RequestParam(value="chat_id", required=true) String cid,
    		@RequestParam(value="msg", required=true) String msg){
    	return activeChatUserManager.sendNewMessage(uid, cid, msg);
    }
    
    @RequestMapping(value="/check_msg", method=RequestMethod.GET)
    @ResponseBody
    public ArrayList<Message> pullNewMessage(@RequestParam(value="chat_id", required=true) String cid,
    		@RequestParam(value="user_id", required=true) String uid){
    	return activeChatUserManager.pullNewMessage(cid, uid);
    } 
    
    @RequestMapping(value="/signout", method=RequestMethod.GET)
    @ResponseBody
    public boolean signOutUser(@RequestParam(value="user_id", required=true) String uid){
    	return true;
    }
    
    @RequestMapping(value="/leave_chat", method=RequestMethod.GET)
    @ResponseBody
    public boolean leaveChat(@RequestParam(value="user_id", required=true) String uid,
    		@RequestParam(value="chat_id", required=true) String cid){
    	return true;
    }
    
    

}
