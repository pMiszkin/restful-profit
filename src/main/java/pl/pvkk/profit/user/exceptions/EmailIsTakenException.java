package pl.pvkk.profit.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class EmailIsTakenException extends RuntimeException {

	private String message;

	public EmailIsTakenException() {
		this.message = "Any account is registered on this email address";
	}

	public String getMessage() {
		return message;
	}
}
