package pl.pvkk.profit.shares;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class ShareNumberLessThanOneException extends RuntimeException {

	private String message;

	public ShareNumberLessThanOneException() {
		this.message = "Share number is less than one";
	}

	public String getMessage() {
		return message;
	}
}
