package pl.pvkk.profit.user.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.gpw.Share;
import pl.pvkk.profit.gpw.SharesService;
import pl.pvkk.profit.user.pocket.Pocket;
import pl.pvkk.profit.user.pocket.PocketService;

@Service
public class TradesService {

	@Autowired
	private SharesService sharesService;
	
	@Autowired
	private PocketService pocketService;
	

	public ResponseEntity<String> buyShares(String shareShortcut, int shareNumber) {
		String response;
		//Ill do this
		Pocket pocket = pocketService.getPocketById(1);

		if(!sharesService.isShareExists(shareShortcut)) {
			response = "This share doesn't exists!";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		
		Share share = sharesService.findShareByShortcut(shareShortcut);
		double sharePrice = share.getReferencePrice();
		
		if(shareNumber <= 0){
			response = "You're trying to buy 0 or less shares";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		else if(shareNumber*sharePrice > pocket.getMoney()) {
			response = "You have no money for that";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);	
		}
		
		pocketService.setShares(pocket, shareShortcut, shareNumber, sharePrice);
		
		response = "You've bought "+shareNumber+" shares from "+share.getName()+" company, for "+shareNumber*sharePrice;
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	
	public ResponseEntity<String> sellShares(String shareShortcut, int shareNumber) {
		String response;
		//Ill do this
		Pocket pocket = pocketService.getPocketById(1);
		
		if(!sharesService.isShareExists(shareShortcut)) {
			response = "This share doesn't exists!";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		if(shareNumber <= 0){
			response = "You're trying to sell 0 or less shares";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		if(!pocketService.isShareExistsInPocket(pocket, shareShortcut, shareNumber)) {
			response = "You don't have so many shares";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		
		Share share = sharesService.findShareByShortcut(shareShortcut);
		double sharePrice = share.getReferencePrice();
		
		pocketService.setShares(pocket, shareShortcut, -shareNumber, sharePrice);
		
		response = "You've sold "+shareNumber+" from "+share.getName()+" company, for "+shareNumber*sharePrice;
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}