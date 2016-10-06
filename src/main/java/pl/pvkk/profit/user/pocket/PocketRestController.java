package pl.pvkk.profit.user.pocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pocket")
public class PocketRestController {

	@Autowired
	private PocketService pocketService;
	
	@GetMapping("/{pocketId}")
	public String getPocketData(@PathVariable long pocketId){
		Pocket pocket = pocketService.getPocketById(pocketId);
		return pocket == null ? "wrong id bruh" : pocket.toString();
	}
}
