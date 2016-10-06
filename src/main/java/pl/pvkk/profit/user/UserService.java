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
	
	public HttpEntity<User> tryToPrintUser(long userId) {
		User user = userDao.getUserById(userId);
		return user == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	public ResponseEntity<String> tryToSaveUser(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		if(userDao.isLoginTaken(user.getLogin()))
			return new ResponseEntity<String>("Your login is taken", HttpStatus.BAD_REQUEST);

		userDao.saveUser(user);
		return new ResponseEntity<String>("Hi "+user.getLogin()+"!", HttpStatus.OK);
	}
}










