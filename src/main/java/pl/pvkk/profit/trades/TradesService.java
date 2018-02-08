package pl.pvkk.profit.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.pvkk.profit.domain.shares.Share;
import pl.pvkk.profit.domain.user.Pocket;
import pl.pvkk.profit.shares.ShareNotFoundException;
import pl.pvkk.profit.shares.ShareNumberLessThanOneException;
import pl.pvkk.profit.shares.SharesDao;
import pl.pvkk.profit.shares.SharesService;
import pl.pvkk.profit.user.UserService;
import pl.pvkk.profit.user.exceptions.UserIsNotEnabledException;
import pl.pvkk.profit.user.pocket.PocketDao;
import pl.pvkk.profit.user.pocket.PocketService;

@Service
@Transactional
public class TradesService {

	@Autowired
	private SharesDao sharesDao;
	@Autowired
	private SharesService sharesService;
	@Autowired
	private PocketService pocketService;
	@Autowired
	private PocketDao pocketDao;
	@Autowired
	private UserService userService;
	
	public ResponseEntity<String> buyShares(String username, String shareShortcut, int shareNumber) {
		Pocket pocket = getPocketIfPossible(username, shareShortcut, shareNumber);
		Share share = sharesService.findShareByIsin(shareShortcut);
		double sharePrice = sharesService.getQurrentQuotationPrice(share);
		if(shareNumber*sharePrice > pocket.getMoney().doubleValue()) {
			String response = "You have no money for that";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);	
		}
		pocketService.setSharesAndTransactions(pocket, share, shareNumber);
		
		String response = "You've bought "+shareNumber+" shares from "+share.getName()+" company, for "+shareNumber*sharePrice;
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<String> sellShares(String username, String shareShortcut, int shareNumber) {
		Pocket pocket = getPocketIfPossible(username, shareShortcut, shareNumber);
		if(!pocketService.areEnoughSharesInPocket(pocket, shareShortcut, shareNumber)) {
			String response = "You don't have so many shares";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		Share share = sharesService.findShareByIsin(shareShortcut);
		double sharePrice = sharesService.getQurrentQuotationPrice(share);
		pocketService.setSharesAndTransactions(pocket, share, -shareNumber);
		
		String response = "You've sold "+shareNumber+" from "+share.getName()+" company, for "+shareNumber*sharePrice;
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	private Pocket getPocketIfPossible(String username, String shareShortcut, int shareNumber) {
		if(!userService.isUserEnabled(username)) 
			throw new UserIsNotEnabledException();
		else if(!sharesDao.isShareExists(shareShortcut))
			throw new ShareNotFoundException();
		else if(shareNumber < 1)
			throw new ShareNumberLessThanOneException();
		return pocketDao.getPocketById(username);
	}	
}
