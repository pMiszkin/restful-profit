package pl.pvkk.profit.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.Length;

@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;
	@Length(min = 5, max = 30)
	private String login;
	@Length(min = 5)
	private String password;
	@OneToOne
	private UserProfile profile;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserProfile getProfile() {
		return profile;
	}

	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}

}
