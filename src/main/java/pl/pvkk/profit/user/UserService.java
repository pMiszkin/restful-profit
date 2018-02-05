package pl.pvkk.profit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.pvkk.profit.user.verification.OnRegistrationCompleteEvent;
import pl.pvkk.profit.user.verification.VerificationToken;
import pl.pvkk.profit.user.verification.VerificationTokenRepository;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private VerificationTokenRepository tokenRepository;
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	

	public HttpEntity<User> tryToPrintUser(User user) {
		return user == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
				: new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@Transactional
	public ResponseEntity<String> tryToSaveUser(User user) {
		if (userDao.isEmailTaken(user.getEmail()))
			return new ResponseEntity<String>("[\"Any account is registered on this email address.\"]", HttpStatus.BAD_REQUEST);
		else if(userDao.isLoginTaken(user.getLogin())) 
			return new ResponseEntity<String>("[\"Your login is taken.\"]", HttpStatus.BAD_REQUEST);
		
		setUserDataBeforeSave(user);
		userDao.saveUser(user);
		
		//try to send verification email to user
		try {
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
		}
		catch(Exception e){
			return new ResponseEntity<String>("[\"Email sendind error. Uuups..\"]", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	public void createVerificationToken(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}
	
	public boolean isUserEnabled(String username) {
		return userDao.getUserByName(username).isEnabled();
	}
	
	private void setUserDataBeforeSave(User user) {
		user.setEnabled(false);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}
}
