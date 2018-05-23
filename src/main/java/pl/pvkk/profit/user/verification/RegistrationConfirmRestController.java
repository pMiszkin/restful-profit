package pl.pvkk.profit.user.verification;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.pvkk.profit.user.User;
import pl.pvkk.profit.user.UserDao;
import pl.pvkk.profit.user.VerificationToken;

@RestController
public class RegistrationConfirmRestController {

	@Autowired
	private VerificationTokenRepository tokenRepository;
	@Autowired
	private UserDao userDao;

	@GetMapping("registrationConfirm")
	public String confirmUser(@RequestParam("token") String token) {
		VerificationToken verificationToken = tokenRepository.findByToken(token);
		if (verificationToken == null) {
			return "Ouuuhh this token aint exists.";
		}
		User user = verificationToken.getUser();
		Calendar cal = Calendar.getInstance();

		if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return "Token is expired, you lazy...";
		}
		
		user.setEnabled(true); 
	    userDao.setEnabledUser(user); 
		return "It was good confirmation bruv u r cool";
	}
}
