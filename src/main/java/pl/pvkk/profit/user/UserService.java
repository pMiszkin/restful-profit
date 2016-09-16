package pl.pvkk.profit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public HttpEntity<User> tryToPrintUser(int userId) {
		User user = userDao.getUserById(userId);
		
		return user == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	public ResponseEntity<String> tryToSaveUser(String login, String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User user = new User();
		user.setLogin(login);
		user.setPassword(passwordEncoder.encode(password));

		return userDao.saveUser(user) ?
				new ResponseEntity<String>("Hi "+login+"!", HttpStatus.OK)
				: new ResponseEntity<String>("Your login is taken", HttpStatus.BAD_REQUEST);

	}
}










