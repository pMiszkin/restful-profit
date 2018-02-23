package pl.pvkk.profit.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class NotEnoughSharesInPocketException extends RuntimeException {

    private String message;

    public NotEnoughSharesInPocketException() {
        this.message = "You don't have enough shares in pocket";
    }

    public String getMessage() {
        return message;
    }
}
