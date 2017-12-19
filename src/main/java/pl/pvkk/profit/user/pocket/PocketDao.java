package pl.pvkk.profit.user.pocket;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.pvkk.profit.user.trades.Transaction;

@Repository
@Transactional
public class PocketDao {

	@PersistenceContext
	private EntityManager em;
	
	public Pocket getPocketById(String username){	
		Pocket pocket = em.find(Pocket.class, username);
		return pocket;
	}
	
	public void updateSharesAndMoneyInPocket(Pocket pocket, List<Transaction> transactions){
		transactions.forEach(t -> {
			em.persist(t);
			em.merge(pocket);
		});
	}
}