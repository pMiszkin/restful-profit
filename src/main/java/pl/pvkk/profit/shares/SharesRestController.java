package pl.pvkk.profit.shares;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.pvkk.profit.domain.shares.Share;
import pl.pvkk.profit.domain.shares.StockIndex;


@RestController
@RequestMapping("shares")
public class SharesRestController {

	@Autowired
	private SharesService sharesService;	
	@Autowired
	private SharesDao sharesDao;
	
	@GetMapping("/all")
	public HttpEntity<List<Share>> findAllShares() {
		return ResponseEntity.ok(sharesDao.findAllShares());
	}
	
	@GetMapping("/company")
	public HttpEntity<Share> findShare(@RequestParam String isin) {
		Share share = sharesService.findShareByIsin(isin);
		return share == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
			new ResponseEntity<Share>(share, HttpStatus.OK);
	}
	
	@GetMapping("/indices/all")
	public HttpEntity<List<StockIndex>> findIndices() {
		return ResponseEntity.ok(sharesDao.findAllIndices());
	}
}
