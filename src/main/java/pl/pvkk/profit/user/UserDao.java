package pl.pvkk.profit.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional()
public class UserDao {

	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@Transactional(readOnly=true)
	public User getUserById(int id){		
		return em.find(User.class, id);
	}
	
	public boolean isLoginTaken(String login){
		Query query = em.createQuery("SELECT COUNT(*) FROM User WHERE login = :login");
		query.setParameter("login", login);

		//return true if login is taken
		return (long) query.getSingleResult() > 0;		
	}

	public boolean saveUser(User user){
		em.persist(user);
	
		return true;
	}
}
