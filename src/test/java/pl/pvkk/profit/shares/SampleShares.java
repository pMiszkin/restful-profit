package pl.pvkk.profit.shares;

import pl.pvkk.profit.shares.quotations.CurrentQuotation;

public class SampleShares {
	
    public static Share getSampleShare() {
        Share share = new Share();
        share.setIsin("randomShareIsin");
        share.setName("randomShareName");
        CurrentQuotation quotation = new CurrentQuotation();
        quotation.setPrice(50);
        share.setCurrentQuotation(quotation);

        return share;
    }
    
    public static String getProperIsin() {
    	return "PL11BTS00015";
    }
}
