package pl.pvkk.profit.user.pocket;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PocketDao {

	@PersistenceContext
	private EntityManager em;
	
	public Pocket getPocketById(String username){	
		Pocket pocket = em.find(Pocket.class, username);
		return pocket;
	}
	
	public void updateSharesAndMoneyInPocket(Pocket pocket){
		em.merge(pocket);
	}
}