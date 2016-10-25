package pl.pvkk.profit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	@GetMapping(value = { "/", "/home" })
	public String hello() {
		return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
