package pl.pvkk.profit.user.verification;

import org.springframework.context.ApplicationEvent;

import pl.pvkk.profit.user.User;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private final String appUrl;
	private final User user;

	public OnRegistrationCompleteEvent(User user, String appUrl) {
		super(user);

		this.user = user;
		this.appUrl = appUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public User getUser() {
		return user;
	}
}
