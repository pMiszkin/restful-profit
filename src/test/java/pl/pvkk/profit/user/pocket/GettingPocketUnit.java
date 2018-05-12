package pl.pvkk.profit.user.pocket;

import org.junit.Assert;
import org.junit.Test;

import pl.pvkk.profit.shares.ShareNotFoundException;
import pl.pvkk.profit.user.Pocket;
import pl.pvkk.profit.user.exceptions.NotEnoughSharesInPocketException;
import pl.pvkk.profit.user.exceptions.UserIsNotEnabledException;

import java.util.Map;

import static org.mockito.Mockito.when;

public class GettingPocketUnit extends PocketServiceInitializerForUnit {


    @Test(expected = UserIsNotEnabledException.class)
    public void buyWhenUserIsNotEnabled() {
        when(userService.isUserEnabled(username)).thenReturn(false);
        pocketService.getPocketIfPossible(username, shareIsin, shareNumber);
    }

    @Test(expected = ShareNotFoundException.class)
    public void buyShareWhatDoesnExist() {
        when(userService.isUserEnabled(username)).thenReturn(true);
        when(sharesService.isShareExists(shareIsin)).thenReturn(false);
        pocketService.getPocketIfPossible(username, shareIsin, shareNumber);
    }

    @Test
    public void makeProperPurchase() {
        Pocket expectedPocket = new Pocket();
        Pocket pocket = properTransaction(expectedPocket);
        Assert.assertEquals(expectedPocket, pocket);
    }

    @Test
    public void compareDifferentPockets() {
        Pocket pocket = properTransaction(new Pocket());
        Assert.assertNotEquals(new Pocket(), pocket);
    }

    @Test(expected = NotEnoughSharesInPocketException.class)
    public void sellSharesWhichDontContain() {
        shareNumber = -4;
        properTransaction(new Pocket());
    }
    @Test(expected = NotEnoughSharesInPocketException.class)
    public void sellMoreThanYouHave() {
        shareNumber = -4;
        Pocket pocket = new Pocket();
        Map<String, Integer> shares = pocket.getShares();
        shares.put(shareIsin, 2);
        properTransaction(new Pocket());
    }

    @Test
    public void makeProperSale() {
        shareNumber = -4;
        Pocket pocket = new Pocket();
        pocket.addShares(shareIsin, 7);
        Assert.assertEquals(pocket, properTransaction(pocket));
    }

    @Test
    public void sellAllSharesWhatYouHave() {
        shareNumber = -4;
        Pocket pocket = new Pocket();
        pocket.addShares(shareIsin, 4);
        Assert.assertEquals(pocket, properTransaction(pocket));
    }

    private Pocket properTransaction(Pocket pocket) {
        when(userService.isUserEnabled(username)).thenReturn(true);
        when(sharesService.isShareExists(shareIsin)).thenReturn(true);
        when(pocketDao.getPocketById(username)).thenReturn(pocket);
        return pocketService.getPocketIfPossible(username, shareIsin, shareNumber);
    }

}
