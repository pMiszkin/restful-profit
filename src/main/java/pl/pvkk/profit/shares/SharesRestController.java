package pl.pvkk.profit.shares;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("shares")
public class SharesRestController {

	@Autowired
	private SharesService sharesService;	
	
	@GetMapping("/all")
	public HttpEntity<List<Share>> findAllShares() {
		//return stock exchange table
		try{
			return ResponseEntity.ok(sharesService.findAllShares());
		} catch(Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/company/{shareShortcut}")
	public HttpEntity<Share> findShare(@PathVariable String shareShortcut) {
		Share share = sharesService.findShareByShortcut(shareShortcut);
		return share == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
			new ResponseEntity<Share>(share, HttpStatus.OK);
	}
	
	@GetMapping("/indices/all")
	public HttpEntity<List<StockIndices>> findIndices() {
		return ResponseEntity.ok(sharesService.findAllIndices());
	}

}