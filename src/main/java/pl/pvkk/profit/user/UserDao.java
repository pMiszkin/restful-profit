package pl.pvkk.profit.user;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	@Autowired
	private EntityManagerFactory emf;
	
	public String getUser(){
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		User user = em.find(User.class, 1);
		em.getTransaction().commit();		
		return user.toString();
	}
	
	public void saveUser(){
		User user = new User();
		user.setLogin("ernest");
		user.setPassword("pass");

		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}
}
