package pl.pvkk.profit.exchange;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("transfer")
public class MoneyExchangeRestController {

	@Autowired
	private ExchangeService exchangeService;
	
	@PostMapping("/buy")
	public ResponseEntity<String> buyShares(
			@RequestParam("name") String shareName,
			@RequestParam("number") int shareNumber) throws IOException{
		
		return exchangeService.buyShares(shareName.toUpperCase(), shareNumber);
	}
	@PostMapping("/sell")
	public ResponseEntity<String> sellShares(
			@RequestParam("name") String shareName,
			@RequestParam("number") int shareNumber) throws IOException{
		
		return exchangeService.sellShares(shareName.toUpperCase(), shareNumber);
	}
}
