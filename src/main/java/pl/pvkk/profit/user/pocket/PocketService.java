package pl.pvkk.profit.user.pocket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.pvkk.profit.shares.CurrentQuotation;
import pl.pvkk.profit.shares.Share;
import pl.pvkk.profit.user.trades.Transaction;

@Service
public class PocketService {

	@Autowired
	private PocketDao pocketDao;


	public Pocket getPocketById(String username) {
		return pocketDao.getPocketById(username);
	}

	public boolean isShareExistsInPocket(Pocket pocket, String shareIsin, int shareNumber) {
		Map<String, Integer> shares = pocket.getShares();

		if(!shares.containsKey(shareIsin.toUpperCase()))
			return false;
		else if(shares.get(shareIsin) < shareNumber)
			return false;
		return true;
	}

	@Transactional
	public void setSharesAndTransactions(Pocket pocket, Share share, int shareNumber) {
		Map<String, Integer> shares = pocket.getShares();
		String shareIsin = share.getIsin();
		CurrentQuotation quotation = share.getCurrentQuotation();
		double sharePrice = quotation.getPrice();
		
		if (!shares.containsKey(shareIsin))
			shares.put(shareIsin, shareNumber);
		else {
			int shareFromPocketNumber = shares.get(shareIsin);

			if (shareNumber + shareFromPocketNumber == 0)
				shares.remove(shareIsin);
			else
				shares.replace(shareIsin, shareNumber + shareFromPocketNumber);
		}
		List<Transaction> transactions = new ArrayList<Transaction>(pocket.getPurchases());

		Transaction transaction = new Transaction();
		transaction.setBuyer(pocket);
		transaction.setShare(share);
		transaction.setShare_number(shareNumber);
		transaction.setShare_price(sharePrice);
		transaction.setDate(new Date());
		
		transactions.add(transaction);
		pocket.setShares(shares);
		pocket.setPurchases(transactions);
		BigDecimal cost = BigDecimal.valueOf(sharePrice*shareNumber);
		pocket.setMoney(pocket.getMoney().subtract(cost));
		pocketDao.updateSharesAndMoneyInPocket(pocket, transaction);
	}
}
