package pl.pvkk.profit.user.pocket;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import pl.pvkk.profit.trades.Transaction;
import pl.pvkk.profit.user.Pocket;

public class SettingMoneyAndTransactionUnit extends PocketServiceInitializerForUnit {

    @Test
    public void buySomeShares() {
        Pocket pocket = new Pocket();
        pocketService.setMoneyIfPossible(pocket, new BigDecimal(-3000));
        Assert.assertEquals(new BigDecimal(2000), pocket.getMoney());
    }

    @Test
    public void buyForAllWhatYouHave() {
        Pocket pocket = new Pocket();
        pocketService.setMoneyIfPossible(pocket, new BigDecimal(-5000));
        Assert.assertEquals(BigDecimal.ZERO, pocket.getMoney());
    }

    @Test(expected = IllegalArgumentException.class)
    public void buyWithoutEnoughMoney() {
        pocketService.setMoneyIfPossible(new Pocket(), new BigDecimal(-7000));
    }

    @Test
    public void setTransaction() {
        Pocket pocket = new Pocket();
        Transaction transaction = new Transaction();
        pocketService.setTransactions(pocket, transaction);
        Assert.assertEquals(transaction, pocket.getPurchases().get(0));
    }
}
