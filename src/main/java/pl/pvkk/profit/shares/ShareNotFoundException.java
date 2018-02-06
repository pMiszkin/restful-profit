package pl.pvkk.profit.shares;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ShareNotFoundException extends RuntimeException{
	
	private String message;
	
	public ShareNotFoundException() {
		this.message = "This share doesn't exist!";
	}

	public String getMessage() {
		return message;
	}
}
