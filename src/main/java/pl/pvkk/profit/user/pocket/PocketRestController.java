package pl.pvkk.profit.user.pocket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pocket")
public class PocketRestController {

	@GetMapping()
	public String getPocketData(){
		
		return Pocket.getInstance().toString();
	}
}
