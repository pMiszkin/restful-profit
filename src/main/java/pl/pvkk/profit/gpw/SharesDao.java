package pl.pvkk.profit.gpw;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class SharesDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public void updateShares(Elements allShares) {
		for(Element element : allShares) {
			//just cut a row with "shareName = Name"
			if(!element.child(1).text().equals("Name")) {
				//Share share = findShareByShortcut(element.child(2).text());
				//if(share == null) {
					Share share = new Share();
					share.setName(element.child(1).text());
					share.setShortcut(element.child(2).text());
					share.setQuotations(new LinkedList<Quotation>());
					createShare(share);
				//}
				List<Quotation> quotations = share.getQuotations();
				Quotation quotation = new Quotation();
				quotation.setCurrency(element.child(3).text());
				quotation.setLastTransactionTime(element.child(4).text());
				//its not normal space. it is no-break space!
				quotation.setReferencePrice(Double.parseDouble(element.child(5).text().replaceAll("\u00A0", "")));
				quotation.setTheoreticalOpenPrice(element.child(6).text());
				quotation.setOpen(element.child(7).text());
				quotation.setLow(element.child(8).text());
				quotation.setHigh(element.child(9).text());
				quotation.setLastClosing(element.child(10).text());
				quotation.setChange(element.child(11).text());
				quotation.setCumulatedVolume(element.child(12).text());
				quotation.setCumulatedValue(element.child(13).text());
				
				quotations.add(quotation);
				share.setQuotations(quotations);
				
				em.persist(quotation);
				//em.merge(share);
			}
		}	
	}
	
	public List<Share> findSharesTable(StockIndexUrl stockIndexUrl) {
		List<Share> shares = em.createQuery("SELECT s FROM Share s").getResultList();
		return shares;
	}
	
	private void createShare(Share share) {
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
