package pl.pvkk.profit.user;

public class EmailIsTakenException extends RuntimeException {

	private String message;

	public EmailIsTakenException() {
		this.message = "[\"Any account is registered on this email address.\"]";
	}

	public String getMessage() {
		return message;
	}
}
