package pl.pvkk.profit.gpw;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SharesDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public void updateQuotationsInShare(Share share, Quotation quotation) {
		em.persist(quotation);
		em.merge(share);
	}
	
	public List<Share> findSharesTable(StockIndexUrl stockIndexUrl) {
		List<Share> shares = em.createQuery("SELECT s FROM Share s").getResultList();
		return shares;
	}
	
	public void createShare(Share share) {
		em.persist(share);
	}
	
	public Share findShareByShortcut(String shortcut) {
		return em.find(Share.class, shortcut);
	}
	
	public boolean isShareExists(String shortcut) {
		Query query = em.createQuery("SELECT COUNT(*) FROM Share WHERE shortcut = :shortcut");
		query.setParameter("shortcut", shortcut);
		return (long) query.getSingleResult() > 0;
	}

}
