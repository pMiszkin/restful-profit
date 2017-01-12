package pl.pvkk.profit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * VIEWS *
 */
@Controller
public class ViewsController {

	@GetMapping(value = { "/", "/home" })
	public String hello() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/shares/company/{shareShortcut}")
	public String share() {
		return "share";
	}
	
	@GetMapping("/user/profile/{username}")
	public String user() {
		return "user";
	}
}
