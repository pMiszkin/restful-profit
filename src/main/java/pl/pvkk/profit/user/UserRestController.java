package pl.pvkk.profit.user;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import pl.pvkk.profit.user.verification.OnRegistrationCompleteEvent;


@RestController
@RequestMapping("user")
public class UserRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@GetMapping()
	public Principal getUser(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@GetMapping("/print/{username}")
	public HttpEntity<User> printUser(@PathVariable String username){
		return userService.tryToPrintUser(username);
	}
	
	@PostMapping(value = "/add", consumes="application/json")
	public ResponseEntity<String> addUser(@Valid @RequestBody User user,
			BindingResult result, WebRequest request){

		if(result.hasErrors()) {
			return new ResponseEntity<>("[\"Registration form has errors, pls fill again.\"]", HttpStatus.BAD_REQUEST);
		}
		else if(userService.isLoginTaken(user.getLogin())) {
			return new ResponseEntity<String>("[\"Your login is taken.\"]", HttpStatus.BAD_REQUEST);
		}
		else if(!userService.tryToSaveUser(user)) {
			return new ResponseEntity<String>("[\"Any account is registered on this email address.\"]", HttpStatus.BAD_REQUEST);
		}
		
		user.setEnabled(false);
		
		//try to send verification email to user
		try {
			String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, appUrl));
		}
		catch(Exception e){
			System.out.println(e);
			return new ResponseEntity<String>("[\"Email sendind error. Uuups..\"]", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);		
	}
}
