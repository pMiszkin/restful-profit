package pl.pvkk.profit.exchange;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.gwd.SharesService;
import pl.pvkk.profit.pocket.Pocket;

@Service
public class ExchangeService {

	@Autowired
	private SharesService sharesService;
	/**
	 * SELL SHARES
	 * @param shareName
	 * @param shareNumber
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<String> sellShares(String shareName, int shareNumber) throws IOException {
		
		Pocket pocket = Pocket.getInstance();
		double sharePrice = sharesService.findSharePriceByName(shareName);
		String response;
		
		if(shareNumber <= 0){
			response = "You're trying to sell 0 or less shares";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		if(pocket.getShares().get(shareName) < shareNumber) {
			response = "You don't have so many shares";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}	
		pocket.setMoney(pocket.getMoney()-sharePrice*shareNumber);
		pocket.setShares(shareName, shareNumber);
		
		response = "You've sold "+shareNumber+" from "+shareName+" company, for "+shareNumber*sharePrice;
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	/**
	 * BUY SHARES
	 * @param shareName
	 * @param shareNumber
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<String> buyShares(String shareName, int shareNumber) throws IOException{
		
		Pocket pocket = Pocket.getInstance();
		double sharePrice = sharesService.findSharePriceByName(shareName);
		String response;
		
		if(shareNumber <= 0){
			response = "You're trying to buy 0 or less shares";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		else if(shareNumber*sharePrice > pocket.getMoney()) {
			response = "You have no money for that";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);	
		}
		pocket.setMoney(pocket.getMoney()-sharePrice*shareNumber);
		pocket.setShares(shareName, shareNumber);
		
		response = "You've bought "+shareNumber+" from "+shareName+" company, for "+shareNumber*sharePrice;
		return new ResponseEntity<String>(response, HttpStatus.OK);

	}
}
