package pl.pvkk.profit.trades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("user/pocket/transfer")
@Validated
public class TradesRestController {

	@Autowired
	private TradesService tradesService;
	
	@PostMapping(value = "/purchases", produces = "text/plain;charset=UTF-8")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<String> buyShares(
			@RequestParam("name") String shareIsin,
			@Min(1) @RequestParam("number") int shareNumber) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String response = tradesService.tryToMakeTransaction(username, shareIsin.toUpperCase(), shareNumber);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = "/sales", produces = "text/plain;charset=UTF-8")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<String> sellShares(
			@RequestParam("name") String shareIsin,
			@Min(1) @RequestParam("number") int shareNumber) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String response = tradesService.tryToMakeTransaction(username, shareIsin.toUpperCase(), -shareNumber);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String constraintViolationHandler(ConstraintViolationException e) {
		return "Share number " + e.getConstraintViolations().iterator().next().getMessage();
	}
}
