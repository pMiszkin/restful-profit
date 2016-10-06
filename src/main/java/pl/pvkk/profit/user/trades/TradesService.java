package pl.pvkk.profit.user.trades;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.gpw.Share;
import pl.pvkk.profit.gpw.SharesService;
import pl.pvkk.profit.user.pocket.Pocket;
import pl.pvkk.profit.user.pocket.PocketDao;
import pl.pvkk.profit.user.pocket.PocketService;

@Service
public class TradesService {

	@Autowired
	private SharesService sharesService;
	
	@Autowired
	private PocketService pocketService;
	/**
	 * BUY SHARES
	 * @param shareId
	 * @param shareNumber
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<String> buyShares(long shareId, int shareNumber) {
		String response;

		if(!sharesService.isShareExists(shareId)) {
			response = "This share doesn't exists!";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		
		Share share = sharesService.findShareById(shareId);
		double sharePrice = share.getReferencePrice();
		
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
	public ResponseEntity<String> sellShares(long shareId, int shareNumber) {
		String response;

		if(!sharesService.isShareExists(shareId)) {
			response = "This share doesn't exists!";
			return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
		}
		
		Pocket pocket = pocketService.getPocketById(1);
		Share share = sharesService.findShareById(shareId);
		double sharePrice = share.getReferencePrice();
		
		response = "You've sold "+shareNumber+" from "+share.getName()+" company, for "+shareNumber*sharePrice;
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
