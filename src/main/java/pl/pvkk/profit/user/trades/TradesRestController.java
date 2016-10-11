package pl.pvkk.profit.user.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("transfer")
public class TradesRestController {

	@Autowired
	private TradesService tradesService;
	
	@PostMapping("/buy")
	public ResponseEntity<String> buyShares(
			@RequestParam("name") String shareShortcut,
			@RequestParam("number") int shareNumber) {
		
		return tradesService.buyShares(shareShortcut.toUpperCase(), shareNumber);
	}
	@PostMapping("/sell")
	public ResponseEntity<String> sellShares(
			@RequestParam("name") String shareShortcut,
			@RequestParam("number") int shareNumber) {
		
		return tradesService.sellShares(shareShortcut.toUpperCase(), shareNumber);
	}
}
