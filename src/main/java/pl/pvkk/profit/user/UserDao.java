package pl.pvkk.profit.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDao {

	@PersistenceContext
	private EntityManager em;
	
	public User getUserById(int id){		
		
		return em.find(User.class, id);
	}
	public boolean checkIfLoginIsTaken(User user){
		Query query = em.createQuery("SELECT COUNT(*) FROM User WHERE login = :login");
		query.setParameter("login", user.getLogin());
		if((long) query.getSingleResult() > 0){
			//return false if login is taken
			return false;
		}
		return true;		
	}

	public boolean saveUser(User user){
			em.persist(user);
		return true;
	}

}
