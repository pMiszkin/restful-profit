package pl.pvkk.profit.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import pl.pvkk.profit.RestfulProfitApplication;
import pl.pvkk.profit.user.pocket.Pocket;
import pl.pvkk.profit.user.pocket.PocketService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestfulProfitApplication.class)
@Transactional
public class PocketTest {

	@Autowired
	private PocketService pocketService;
	
	@Test
	public void testGetPocketById(){
		Pocket pocket = pocketService.getPocketById(1);
		
		Assert.assertNotNull("failure - expected not null", pocket);
	}
}
