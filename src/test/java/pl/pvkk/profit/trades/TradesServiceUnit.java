package pl.pvkk.profit.trades;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import pl.pvkk.profit.domain.shares.Share;
import pl.pvkk.profit.domain.user.Pocket;
import pl.pvkk.profit.shares.SharesService;
import pl.pvkk.profit.shares.StubShares;
import pl.pvkk.profit.user.pocket.PocketService;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TradesServiceUnit {

    @Mock
    private SharesService sharesService;
    @Mock
    private PocketService pocketService;
    @InjectMocks
    private TradesService tradesService;
    private String username;
    private String shareIsin;
    private int shareNumber;
    private Share share;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        username = "randomUsername";
        shareIsin = StubShares.getProperShare().getIsin();
        shareNumber = 5;
        share = StubShares.getProperShare();
    }

    @Test
    public void compareResponseForPurchase() {
        String response = makeProperTransaction();
        BigDecimal cost = new BigDecimal(share.getQurrentQuotationPrice() * shareNumber);
        String expectedResponse = "You've bought "+ shareNumber + " shares from " + share.getName() + " company, for " + cost;
        Assert.assertEquals(expectedResponse, response);
    }

    @Test
    public void compareResponseForSale() {
        shareNumber = -8;
        String response = makeProperTransaction();
        BigDecimal cost = new BigDecimal(share.getQurrentQuotationPrice() * shareNumber);
        String expectedResponse = "You've sold "+ shareNumber + " shares from " + share.getName() + " company, for " + cost;
        Assert.assertEquals(expectedResponse, response);
    }

    private String makeProperTransaction() {
        when(pocketService.getPocketIfPossible(username, shareIsin, shareNumber)).thenReturn(new Pocket());
        when(sharesService.findShareByIsin(shareIsin)).thenReturn(share);
        double sharePrice = share.getQurrentQuotationPrice();
        when(sharesService.getSumOfCosts(share, shareNumber)).thenReturn(sharePrice * shareNumber);

        return tradesService.tryToMakeTransaction(username, shareIsin, shareNumber);
    }

}
