package pl.pvkk.profit.user.exceptions;

public class EmailIsTakenException extends RuntimeException {

	private String message;

	public EmailIsTakenException() {
		this.message = "[\"Any account is registered on this email address.\"]";
	}

	public String getMessage() {
		return message;
	}
}
