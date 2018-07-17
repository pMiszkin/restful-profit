package pl.pvkk.profit.gpw.connection;

import java.io.IOException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.pvkk.profit.shares.Share;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SharesDownloaderIntegration {

	@Test
	public void downloadShares() {
		Set<Share> shares = null;
		try {
			shares = SharesDownloader.download();
		} catch(IOException e) {}
		Assert.assertEquals(1, filterByNameCount(shares, "11BIT"));
	}
	
	private long filterByNameCount(Set<Share> shares, String name) {
		return shares.stream()
				.filter(share -> share.getName().equals(name))
				.count();
	}
	
}
