package pl.pvkk.profit.user.pocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.pvkk.profit.domain.user.Pocket;

@RestController
@RequestMapping("user/pocket")
public class PocketRestController {

	@Autowired
	private PocketDao pocketDao;
	
	@GetMapping("/{username}")
	public ResponseEntity<Pocket> getPocketData(@PathVariable String username){
		Pocket pocket = pocketDao.getPocketById(username);
		return pocket == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : 
			new ResponseEntity<Pocket>(pocket, HttpStatus.OK);
	}
}
