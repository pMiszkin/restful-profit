package pl.pvkk.profit.gwd;

import java.io.IOException;
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
@RequestMapping("share")
public class GwdSharesRestController {

	@Autowired
	private SharesService sharesService;
	/**
	 * FIND STOCK INDEX TABLE
	 * @param stockIndex
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/{stockIndex}")
	public HttpEntity<List<Share>> findSharesTable(@PathVariable String stockIndex) throws IOException {
		String stockName = stockIndex.toUpperCase();

		//return stock exchange table
		try{
			return ResponseEntity.ok(sharesService.findShares(StockIndexUrl.valueOf(stockName).toString()));
		} catch(Exception e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
