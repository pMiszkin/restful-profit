package pl.pvkk.profit.user.pocket;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.shares.ShareNotFoundException;
import pl.pvkk.profit.shares.SharesService;
import pl.pvkk.profit.trades.Transaction;
import pl.pvkk.profit.user.Pocket;
import pl.pvkk.profit.user.UserService;
import pl.pvkk.profit.user.exceptions.UserIsNotEnabledException;

@Service
public class PocketService {

	@Autowired
	private PocketDao pocketDao;
	@Autowired
	private SharesService sharesService;
	@Autowired
	private UserService userService;

	public Pocket getPocketIfPossible(String username, String shareIsin, int shareNumber) {
		checkForExceptions(username, shareIsin);
		Pocket pocket = pocketDao.getPocketById(username);
		if(shareNumber < 0)
			areEnoughSharesInPocket(pocket.getShares(), shareIsin, shareNumber);
		return pocket;
	}

	public void setMoneyIfPossible(Pocket pocket, BigDecimal cost) {
		BigDecimal money = pocket.getMoney();
		money = money.add(cost);
		if(money.compareTo(BigDecimal.ZERO) < 0)
			throw new IllegalArgumentException("You don't have enough money");
		pocket.setMoney(money);
	}

	public void setTransactions(Pocket pocket, Transaction transaction) {
		pocket.addPurchase(transaction);
		pocketDao.updateSharesAndMoneyInPocket(pocket, transaction);
	}

	private void checkForExceptions(String username, String shareIsin) {
		if(!userService.isUserEnabled(username))
			throw new UserIsNotEnabledException();
		else if(!sharesService.isShareExists(shareIsin))
			throw new ShareNotFoundException();
	}

	private void areEnoughSharesInPocket(Map<String, Integer> pocketShares, String shareIsin, int shareNumber) {
		if(!pocketShares.containsKey(shareIsin) || pocketShares.get(shareIsin)+shareNumber < 0 )
			throw new IllegalArgumentException("You don't have enough shares in pocket");
	}
}