package pl.pvkk.profit.exchange;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import pl.pvkk.profit.gpw.SharesService;
import pl.pvkk.profit.user.trades.TradesService;

@RunWith(MockitoJUnitRunner.class)
public class TradesServiceTest {
	
	@Mock	
	private SharesService sharesService;
	
	@InjectMocks
	private TradesService tradesService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

    @After  
    public void tearDown() {  
        sharesService = null;     
        tradesService = null;
    } 
    
    @Test
    public void testBuyShares() {
    	
    }

}
