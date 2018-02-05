package pl.pvkk.profit.user;

public class LoginIsTakenException extends RuntimeException {

	private String message;

	public LoginIsTakenException() {
		this.message = "[\"Your login is taken.\"]";
	}

	public String getMessage() {
		return message;
	}
}
