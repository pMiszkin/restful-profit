package pl.pvkk.profit.gpw.connection;

import java.io.IOException;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.pvkk.profit.shares.StockIndex;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockIndicesDownloaderIntegration {
	
	@Test
	public void downloadStockIndices() {
		StockIndicesDownloader getter = new StockIndicesDownloader();
		Set<StockIndex> indices = null;
		try {
			indices = getter.downloadStockIndices();
		} catch(IOException e) {}
		Assert.assertEquals(1, filterByNameCount(indices, "WIG20"));
		Assert.assertEquals(1, filterByNameCount(indices, "WIG-MEDIA"));
	}
	
	private long filterByNameCount(Set<StockIndex> indices, String name) {
		return indices.stream()
				.filter(id -> id.getName().equals(name))
				.count();
	}
}
