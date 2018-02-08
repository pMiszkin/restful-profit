package pl.pvkk.profit.user.pocket;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.domain.Transaction;
import pl.pvkk.profit.domain.shares.Share;
import pl.pvkk.profit.domain.user.Pocket;
import pl.pvkk.profit.shares.SharesService;

@Service
public class PocketService {

	@Autowired
	private PocketDao pocketDao;
	@Autowired
	private SharesService sharesService;

	public boolean areEnoughSharesInPocket(Pocket pocket, String shareIsin, int shareNumber) {
		Map<String, Integer> pocketShares = pocket.getShares();
		if(!pocketShares.containsKey(shareIsin.toUpperCase()))
			return false;
		return pocketShares.get(shareIsin) >= shareNumber;
	}
	
	public void setSharesAndTransactions(Pocket pocket, Share share, int shareNumber) {
		Map<String, Integer> pocketShares = pocket.getShares();
		String shareIsin = share.getIsin();
		setShares(pocketShares, shareIsin, shareNumber);
		
		Transaction transaction = new Transaction();
		transaction.setBuyer(pocket);
		transaction.setShare(share);
		transaction.setShare_number(shareNumber);
		double sharePrice = sharesService.getQurrentQuotationPrice(share);
		transaction.setShare_price(sharePrice);
		transaction.setDate(new Date());
		
		List<Transaction> transactions = pocket.getPurchases();
		transactions.add(transaction);
		pocket.setShares(pocketShares);
		pocket.setPurchases(transactions);
		BigDecimal cost = BigDecimal.valueOf(sharePrice*shareNumber);
		pocket.setMoney(pocket.getMoney().subtract(cost));
		pocketDao.updateSharesAndMoneyInPocket(pocket, transaction);
	}
	
	private void setShares(Map<String, Integer> pocketShares, String shareIsin, int shareNumber) {
		if (!pocketShares.containsKey(shareIsin))
			pocketShares.put(shareIsin, shareNumber);
		else {
			int shareFromPocketNumber = pocketShares.get(shareIsin);
			if (shareNumber + shareFromPocketNumber == 0)
				pocketShares.remove(shareIsin);
			else
				pocketShares.replace(shareIsin, shareNumber + shareFromPocketNumber);
		}
	}
}
