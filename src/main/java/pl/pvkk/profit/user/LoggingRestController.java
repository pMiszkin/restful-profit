package pl.pvkk.profit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
public class LoggingRestController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/print/{userId}")
	public HttpEntity<User> printUser(@PathVariable int userId){
		return userService.tryToPrintUser(userId);
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addUser(
			@RequestParam String login,
			@RequestParam String password
			){
		
		return userService.tryToSaveUser(login, password);
	}

}








