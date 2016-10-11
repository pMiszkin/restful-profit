package pl.pvkk.profit.user.trades;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import pl.pvkk.profit.gpw.Share;
import pl.pvkk.profit.gpw.SharesService;
import pl.pvkk.profit.user.pocket.Pocket;
import pl.pvkk.profit.user.pocket.PocketService;
import pl.pvkk.profit.user.trades.TradesService;

@RunWith(MockitoJUnitRunner.class)
public class TradesServiceTest {

	@Mock
	private SharesService sharesService;

	@Mock
	private PocketService pocketService;

	@InjectMocks
	private TradesService tradesService;

	private Pocket pocket;
	private Share share;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		pocket = getPocketStubData();
		share = getShareStubData();
		when(sharesService.isShareExists(anyObject())).thenReturn(true);
		when(pocketService.getPocketById(1)).thenReturn(pocket);
		when(sharesService.findShareByShortcut(anyObject())).thenReturn(share);
	}

	/**
	 * Unit tests for the TradesServicer using Mockito
	 * 
	 * Testing buying shares first - buyShares(String shareShortcut, int shareNumber) method.
	 */
	@Test
	public void testBuyShareWhatDoesntExist() {
		when(sharesService.isShareExists(anyObject())).thenReturn(false);

		ResponseEntity<String> response = tradesService.buyShares(anyString(), 5);
		System.out.println(response);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testBuyShare() {		
		ResponseEntity<String> response = tradesService.buyShares(anyString(), 5);
		System.out.println(response);
		System.out.println(pocket);
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testBuyShareWithoutMoney() {
		ResponseEntity<String> response = tradesService.buyShares(anyString(), 50000);
		System.out.println(response);
		
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testBuyMinusAmountOfShare() {
		ResponseEntity<String> response = tradesService.buyShares(anyString(), -5);
		System.out.println(response);
		
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Testing sales
	 * 
	 */
	
	private Pocket getPocketStubData() {
		pocket = new Pocket();
		pocket.setId(1L);

		return pocket;
	}

	private Share getShareStubData() {
		share = new Share.Builder("PVKK").shortcut("PVK").referencePrice("50.45").build();

		return share;
	}
}
