package pl.pvkk.profit.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailSendingException extends RuntimeException {

	private String message;

	public EmailSendingException() {
		this.message = "Email sendind error. Uuups..";
	}

	public String getMessage() {
		return message;
	}
}
