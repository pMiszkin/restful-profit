package pl.pvkk.profit.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
public class UserIsNotEnabledException extends RuntimeException {

	private String message;

	public UserIsNotEnabledException() {
		this.message = "An e-mail was sended at your address, please confirm your account";
	}
	
	public String getMessage() {
		return message;
	}
}
