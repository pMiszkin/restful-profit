package pl.pvkk.profit.shares;

import pl.pvkk.profit.shares.quotations.CurrentQuotation;

public class SimpleShares {

    public static Share getSimpleShare() {
        Share share = new Share();
        share.setIsin("randomShareIsin");
        share.setName("randomShareName");
        CurrentQuotation quotation = new CurrentQuotation();
        quotation.setPrice(50);
        share.setCurrentQuotation(quotation);

        return share;
    }
}
