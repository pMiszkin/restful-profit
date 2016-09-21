package pl.pvkk.profit.exchange;

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
			@RequestParam("id") int shareId,
			@RequestParam("number") int shareNumber) {
		
		return exchangeService.buyShares(shareId, shareNumber);
	}
	@PostMapping("/sell")
	public ResponseEntity<String> sellShares(
			@RequestParam("id") int shareId,
			@RequestParam("number") int shareNumber) {
		
		return exchangeService.sellShares(shareId, shareNumber);
	}
}
