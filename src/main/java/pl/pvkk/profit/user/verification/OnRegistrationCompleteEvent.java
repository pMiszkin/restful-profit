package pl.pvkk.profit.user.verification;

import org.springframework.context.ApplicationEvent;

import pl.pvkk.profit.user.User;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private static final long serialVersionUID = 9113943751727092785L;
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
