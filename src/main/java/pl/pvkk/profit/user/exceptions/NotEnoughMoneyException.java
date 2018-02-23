package pl.pvkk.profit.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class NotEnoughMoneyException extends RuntimeException {

    private String message;

    public NotEnoughMoneyException() {
        this.message = "You don't have enough money";
    }

    public String getMessage() {
        return message;
    }
}
