package pl.pvkk.profit.shares;

import pl.pvkk.profit.domain.shares.CurrentQuotation;
import pl.pvkk.profit.domain.shares.Share;

public class StubShares {

    public static Share getProperShare() {
        Share share = new Share();
        share.setIsin("randomShareIsin");
        share.setName("randomShareName");
        CurrentQuotation quotation = new CurrentQuotation();
        quotation.setPrice(50);
        share.setCurrentQuotation(quotation);

        return share;
    }
}
