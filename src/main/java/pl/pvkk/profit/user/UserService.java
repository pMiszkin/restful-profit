package pl.pvkk.profit.user;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.user.verification.VerificationToken;
import pl.pvkk.profit.user.verification.VerificationTokenRepository;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	@PostConstruct
	public void addFirstUser() {
		User user = new User();
		user.setLogin("login");
		user.setPassword("password");
		user.setEnabled(true);
		tryToSaveUser(user);
	}

	public HttpEntity<User> tryToPrintUser(String username) {
		User user = userDao.getUserByName(username);

		return user == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<User>(user, HttpStatus.OK);
	}

	public boolean isLoginTaken(String username) {
		return userDao.isLoginTaken(username);
	}

	public boolean tryToSaveUser(User user) {
		if (userDao.isEmailTaken(user.getEmail()))
			return false;

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		userDao.saveUser(user);
		return true;
	}

	public void createVerificationToken(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}
	
	public VerificationToken getVerificationToken(String token) {
		return tokenRepository.findByToken(token);
	}

	public void setEnabledUser(User user) {
		userDao.setEnabledUser(user);
	}
	
	public boolean isUserEnabled(String username) {
		return userDao.getUserByName(username).isEnabled();
	}
}
