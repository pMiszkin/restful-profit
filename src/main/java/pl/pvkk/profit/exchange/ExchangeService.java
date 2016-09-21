package pl.pvkk.profit.exchange;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.gpw.Share;
import pl.pvkk.profit.gpw.SharesService;
import pl.pvkk.profit.pocket.Pocket;

@Service
public class ExchangeService {

	@Autowired
	private SharesService sharesService;
	
	/**
	 * BUY SHARES
	 * @param shareId
	 * @param shareNumber
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<String> buyShares(int shareId, int shareNumber) {
		
		Pocket pocket = Pocket.getInstance();
		Share share = sharesService.findShareById(shareId);
		double sharePrice = share.getReferencePrice();
		String response;
		System.out.println(share);
		
		//^^^^^^THROWWWWWWWWWWWWW
		//no works V
		if(sharePrice<=0) {
			response = "This share doesn't exist!";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		else if(shareNumber <= 0){
			response = "You're trying to buy 0 or less shares";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		else if(shareNumber*sharePrice > pocket.getMoney()) {
			response = "You have no money for that";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);	
		}
		pocket.setMoney(pocket.getMoney()-sharePrice*shareNumber);
		pocket.setShares(shareId, shareNumber);
		
		response = "You've bought "+shareNumber+" from "+share.getName()+" company, for "+shareNumber*sharePrice;
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	/**
	 * SELL SHARES
	 * @param shareId
	 * @param shareNumber
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<String> sellShares(int shareId, int shareNumber) {
		
		Pocket pocket = Pocket.getInstance();
		Share share = sharesService.findShareById(shareId);
		double sharePrice = share.getReferencePrice();
		String response;
		
		if(shareNumber <= 0){
			response = "You're trying to sell 0 or less shares";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		if(!pocket.isShareExist(shareId, shareNumber)) {
			response = "You don't have so many shares";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}	
		pocket.setMoney(pocket.getMoney()+sharePrice*shareNumber);
		pocket.setShares(shareId, -shareNumber);
		
		response = "You've sold "+shareNumber+" from "+share.getName()+" company, for "+shareNumber*sharePrice;
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
