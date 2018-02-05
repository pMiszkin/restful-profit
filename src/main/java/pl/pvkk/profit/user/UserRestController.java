package pl.pvkk.profit.user;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
public class UserRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	
	@GetMapping()
	public Principal getUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@GetMapping("/print/{username}")
	public HttpEntity<User> printUser(@PathVariable String username) {
		User user = userDao.getUserByName(username);
		return userService.tryToPrintUser(user);
	}
	
	@PostMapping(value = "/add", consumes="application/json")
	public ResponseEntity<String> addUser(@Valid @RequestBody User user, BindingResult result) {
		String errorMessage = "[\"Registration form has errors, pls fill again.\"]";
		return result.hasErrors() ? new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST)
				: userService.tryToSaveUser(user);
	}
	
	@ExceptionHandler(EmailIsTakenException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String emailIsTaken(EmailIsTakenException e) {
		return e.getMessage();
	}
	
	@ExceptionHandler(LoginIsTakenException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String loginIsTaken(LoginIsTakenException e) {
		return e.getMessage();
	}
}
