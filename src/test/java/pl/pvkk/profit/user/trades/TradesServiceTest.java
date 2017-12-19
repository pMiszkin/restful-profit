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

import pl.pvkk.profit.shares.CurrentQuotation;
import pl.pvkk.profit.shares.Share;
import pl.pvkk.profit.shares.SharesService;
import pl.pvkk.profit.user.UserService;
import pl.pvkk.profit.user.pocket.Pocket;
import pl.pvkk.profit.user.pocket.PocketService;
import pl.pvkk.profit.user.trades.TradesService;

@RunWith(MockitoJUnitRunner.class)
public class TradesServiceTest {

	@Mock
	private SharesService sharesService;

	@Mock
	private PocketService pocketService;
	
	@Mock
	private UserService userService;

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
		when(pocketService.getPocketById("login")).thenReturn(pocket);
		when(sharesService.findShareByIsin(anyObject())).thenReturn(share);
		when(userService.isUserEnabled("login")).thenReturn(true);
	}

	/**
	 * Unit tests for the TradesServicer using Mockito
	 * 
	 * Testing buying shares first - buyShares(String shareShortcut, int shareNumber) method.
	 */
	@Test
	public void testBuyShareWhatDoesntExist() {
		when(sharesService.isShareExists(anyObject())).thenReturn(false);

		ResponseEntity<String> response = tradesService.buyShares(anyString(), 5, "login");
		System.out.println(response);
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testBuyShare() {		
		ResponseEntity<String> response = tradesService.buyShares(anyString(), 5, "login");
		System.out.println(response);
		System.out.println(response.getBody());
		
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void testBuyShareWithoutMoney() {
		ResponseEntity<String> response = tradesService.buyShares(anyString(), 50000, "login");
		System.out.println(response);
		
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testBuyMinusAmountOfShare() {
		ResponseEntity<String> response = tradesService.buyShares(anyString(), -5, "login");
		System.out.println(response);
		
		assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Testing sales
	 * 
	 */
	
	private Pocket getPocketStubData() {
		pocket = new Pocket();
		pocket.setUsername("login");

		return pocket;
	}

	private Share getShareStubData() {
		share = new Share();
		share.setName("PVKKKK");
		share.setIsin("PVK");
		
		CurrentQuotation quotation = new CurrentQuotation();
		quotation.setPrice(50.25);
		share.setCurrentQuotation(quotation);
		
		return share;
	}
}
