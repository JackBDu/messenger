package main.java.chat;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {
	
	ArrayList<String> activeUsers = new ArrayList<String>();
	ArrayList<String> activeChat = new ArrayList<String>();
	ActiveChatUserManager activeChatUserManager = new ActiveChatUserManager(activeUsers, activeChat);
	
    
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
    public Map<String, Integer> createNewChat(@RequestParam(value="user_id", required=true) String uid,
    		@RequestParam(value="invited", required=true) String invited_user){
    	
    	return activeChatUserManager.createNewChat(uid, invited_user);
    }
    
    

    

}
