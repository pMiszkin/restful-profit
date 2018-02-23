package pl.pvkk.profit.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.pvkk.profit.domain.Transaction;
import pl.pvkk.profit.domain.shares.Share;
import pl.pvkk.profit.domain.user.Pocket;
import pl.pvkk.profit.shares.SharesService;
import pl.pvkk.profit.user.pocket.PocketService;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Scope("prototype")
public class TradesService {

	@Autowired
	private SharesService sharesService;
	@Autowired
	private PocketService pocketService;
	private Pocket pocket;
	private Share share;

    @Transactional
    public String tryToMakeTransaction(String username, String shareIsin, int shareNumber) {
		setUp(username, shareIsin, shareNumber);
		BigDecimal cost = getTransactionCost(shareNumber);
		pocketService.setMoneyIfPossible(pocket, cost);
		pocket.addShares(shareIsin, shareNumber);
		Transaction transaction = prepareTransaction(shareNumber);
		pocketService.setTransactions(pocket, transaction);

		return returnResponse(shareNumber, cost);
	}

	private void setUp(String username, String shareIsin, int shareNumber) {
        pocket = pocketService.getPocketIfPossible(username, shareIsin, shareNumber);
        share = sharesService.findShareByIsin(shareIsin);
    }

    private BigDecimal getTransactionCost(int shareNumber) {
	    return new BigDecimal(sharesService.getSumOfCosts(share, shareNumber));
    }

    private String returnResponse(int shareNumber, BigDecimal cost) {
        String prefix;
        if(shareNumber < 0)
            prefix = "You've sold ";
        else
            prefix = "You've bought ";
        return prefix + shareNumber + " shares from " + share.getName() + " company, for " + cost;
    }

	private Transaction prepareTransaction(int shareNumber) {
		Transaction transaction = new Transaction();
		transaction.setBuyer(pocket);
		transaction.setShare(share);
		transaction.setShare_number(shareNumber);
		double sharePrice = share.getQurrentQuotationPrice();
		transaction.setShare_price(sharePrice);
		transaction.setDate(new Date());
		return transaction;
	}
}
