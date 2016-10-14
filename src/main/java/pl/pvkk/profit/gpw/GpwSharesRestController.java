package pl.pvkk.profit.gpw;

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
public class GpwSharesRestController {

	@Autowired
	private SharesService sharesService;
	
	/*@GetMapping("/update")
	public String updateTables() {
		sharesService.addShares();
		
		return "OK!";
	}*/
	
	
	@GetMapping("/stock/{stockIndex}")
	public HttpEntity<List<Share>> findSharesTable(@PathVariable String stockIndex) {
		String stockName = stockIndex.toUpperCase();

		//return stock exchange table
		try{
			return ResponseEntity.ok(sharesService.findShares(StockIndexUrl.valueOf(stockName)));
		} catch(Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{shareShortcut}")
	public HttpEntity<Share> findShare(@PathVariable String shareShortcut) {
		Share share = sharesService.findShareByShortcut(shareShortcut);
		return share == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
			new ResponseEntity<Share>(share, HttpStatus.OK);
	}

}
