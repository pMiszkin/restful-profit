package pl.pvkk.profit.user.verification;

import org.springframework.context.ApplicationEvent;

import pl.pvkk.profit.user.User;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private final User user;

	public OnRegistrationCompleteEvent(User user) {
		super(user);
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
