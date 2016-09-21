package pl.pvkk.profit.gpw;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
			if(!element.child(1).text().equals("Name"))
				em.persist(new Share.Builder(element.child(1).text())
						.shortcut(element.child(2).text())
						.currency(element.child(3).text())
						.lastTransactionTime(element.child(4).text())
						.referencePrice(element.child(5).text())
						.theoreticalOpenPrice(element.child(6).text())
						.open(element.child(7).text())
						.low(element.child(8).text())
						.high(element.child(9).text())
						.lastClosing(element.child(10).text())
						.change(element.child(11).text())
						.cumulatedVolume(element.child(12).text())
						.cumulatedValue(element.child(13).text())
						.build());
		}	
	}
	
	public List<Share> findSharesTable(StockIndexUrl stockIndexUrl) {
		List<Share> shares = em.createQuery("SELECT s FROM Share s").getResultList();
		return shares;
	}
	
	public Share finShareById(int id) {
		return em.find(Share.class, id);
	}

}
