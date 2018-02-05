package pl.pvkk.profit;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.gpw.GpwSharesDownloader;
import pl.pvkk.profit.user.User;
import pl.pvkk.profit.user.UserService;

@Service
@Profile("prod")
public class InitDatabase {

	@Autowired
	private GpwSharesDownloader downloader;
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	public void addShares() throws SQLException {
		downloader.addShares();
		userService.tryToSaveUser(makeFirstUser());
	}	
	
	private User makeFirstUser() {
		User user = new User();
		user.setEmail("asdasd@asd.asd");
		user.setLogin("login");
		user.setPassword("password");
		user.setEnabled(true);
		return user;
	}
}
