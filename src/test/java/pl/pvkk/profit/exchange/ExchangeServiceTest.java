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

@RunWith(MockitoJUnitRunner.class)
public class ExchangeServiceTest {
	
	@Mock	
	private SharesService sharesService;
	
	@InjectMocks
	private ExchangeService exchangeService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

    @After  
    public void tearDown() {  
        sharesService = null;     
        exchangeService = null;
    } 
    
    @Test
    public void testBuyShares() {
    	
    }

}
