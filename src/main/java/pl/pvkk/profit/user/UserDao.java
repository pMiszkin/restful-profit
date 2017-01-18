package pl.pvkk.profit.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.pvkk.profit.user.pocket.Pocket;

@Repository
@Transactional
public class UserDao {

	@PersistenceContext
	private EntityManager em;
	
	//@Transactional(readOnly=true)
	public User getUserByName(String username){
		User user = em.find(User.class, username);
		user.setPassword("not your business");
		return user;
	}
	
	public boolean isLoginTaken(String login){
		Query query = em.createQuery("SELECT COUNT(*) FROM User WHERE login = :login");
		query.setParameter("login", login);

		//return true if login is taken
		return (long) query.getSingleResult() > 0;		
	}

	public void saveUser(User user){
		UserProfile profile = new UserProfile();
		Pocket pocket = new Pocket();
		profile.setUsername(user.getLogin());
		pocket.setUsername(user.getLogin());
		profile.setPocket(pocket);
		user.setProfile(profile);
		em.persist(user);
		em.persist(profile);
		em.persist(pocket);
	}

}
