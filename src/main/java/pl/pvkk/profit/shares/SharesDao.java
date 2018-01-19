package pl.pvkk.profit.shares;

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
	
	public void updateCurrentQuotationInShare(Share share, CurrentQuotation quotation) {
		em.persist(quotation);
		em.merge(share);
	}
	
	public void updateArchiveQuotationsInShare(Share share, List<ArchiveQuotation> quotations) {
		quotations.forEach((q) -> {
				em.persist(q);
				em.merge(share);
		});
	}
	
	@SuppressWarnings("unchecked")
	public List<Share> findAllShares() {
		List<Share> shares = em.createQuery("SELECT s FROM Share s").getResultList();
		return shares;
	}
	
	public void createShare(Share share) {
		em.persist(share);
	}
	
	public Share findShareById(String isin) {
		return em.find(Share.class, isin);
	}
	
	public boolean isShareExists(String isin) {
		Query query = em.createQuery("SELECT COUNT(*) FROM Share WHERE isin = :isin");
		query.setParameter("isin", isin);
		return (long) query.getSingleResult() > 0;
	}
	
	
	/*
	 * Stock Indices part
	 */
	public void addStockIndex(StockIndex stockIndices) {
		em.persist(stockIndices);
	}
	
	@SuppressWarnings("unchecked")
	public List<StockIndex> findAllIndices() {
		List<StockIndex> indices = em.createQuery("SELECT s FROM StockIndex s").getResultList();
		return indices;
	}
}
