package pl.pvkk.profit.user;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	public String getUser(){
		SessionFactory sessionFactory = new Configuration().configure()
				.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
 
		User user = (User) session.get(User.class, 1);
		System.out.println(user.toString());
		session.getTransaction().commit();
		session.close();	
		
		return user.toString();
	}
	public void saveUser(){
		User user = new User();
		user.setLogin("ernest");
		user.setPassword("pass");

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(user);

		session.getTransaction().commit();
		session.close();
	}
}
