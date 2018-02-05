package pl.pvkk.profit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.pvkk.profit.user.exceptions.EmailIsTakenException;
import pl.pvkk.profit.user.exceptions.EmailSendingException;
import pl.pvkk.profit.user.exceptions.LoginIsTakenException;
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
		checkIsUserDataTaken(user);
		setUserDataBeforeSave(user);
		userDao.saveUser(user);
		sendVerificationEmail(user);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	public boolean isUserEnabled(String username) {
		return userDao.getUserByName(username).isEnabled();
	}
	
	public void createVerificationToken(User user, String token) {
		VerificationToken myToken = new VerificationToken(token, user);
		tokenRepository.save(myToken);
	}
	
	private void checkIsUserDataTaken(User user) {
		if (userDao.isEmailTaken(user.getEmail()))
			throw new EmailIsTakenException();
		if(userDao.isLoginTaken(user.getLogin())) 
			throw new LoginIsTakenException();
	}
	
	private void setUserDataBeforeSave(User user) {
		user.setEnabled(false);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
	}
	
	private void sendVerificationEmail(User user) {
		try {
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user));
		}
		catch(Exception e){
			throw new EmailSendingException();
		}
	}
}
