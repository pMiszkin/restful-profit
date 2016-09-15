package pl.pvkk.profit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user")
public class LoggingController {

	@Autowired
	private UserDao userDao;
	
	@GetMapping("/print")
	public String printUser(){
		return userDao.getUser();
	}
	
	@PostMapping("/add")
	public void addUser(){
		userDao.saveUser();
	}
}
