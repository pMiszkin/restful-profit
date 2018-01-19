package pl.pvkk.profit.user.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("user/pocket/transfer")
public class TradesRestController {

	@Autowired
	private TradesService tradesService;
	
	@PostMapping(value = "/purchases", produces = "text/plain;charset=UTF-8")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<String> buyShares(
			@RequestParam("name") String shareShortcut,
			@RequestParam("number") int shareNumber) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return tradesService.buyShares(shareShortcut.toUpperCase(), shareNumber, username);
	}
	
	@PostMapping(value = "/sales", produces = "text/plain;charset=UTF-8")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<String> sellShares(
			@RequestParam("name") String shareShortcut,
			@RequestParam("number") int shareNumber) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return tradesService.sellShares(shareShortcut.toUpperCase(), shareNumber, username);
	}
}
