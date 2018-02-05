package pl.pvkk.profit.user;

public class UsersForTesting {

	public static User getProperUser() {
		User user = new User();
		user.setEmail("proper@pro.per");
		user.setLogin("proper");
		user.setPassword("proper");
		user.setEnabled(false);
		return user;
	}
	
	public static User getTooShortLoginUser() {
		User user = new User();
		user.setEmail("tooshort@log.in");
		user.setLogin("too");
		user.setPassword("short");
		user.setEnabled(false);
		return user;
	}
}
