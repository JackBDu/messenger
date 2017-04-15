package chat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {

    @RequestMapping(value="/login", method=RequestMethod.GET)
    @ResponseBody
    public String greeting(@RequestParam(value="user_id", required=true) String uid) {
	    //  model.addAttribute("name", name);
	     return uid;
    }

}
