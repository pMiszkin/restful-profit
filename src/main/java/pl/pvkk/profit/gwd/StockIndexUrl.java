package pl.pvkk.profit.gwd;

public enum StockIndexUrl {
	
	WIG20("https://www.gpw.pl/ajaxindex.php?action=GPWQuotations&start=showTable&tab=wig20&lang=EN&full=0"),
	MWIG40("https://www.gpw.pl/ajaxindex.php?action=GPWQuotations&start=showTable&tab=mwig40&lang=EN&full=0"),
	SWIG80("https://www.gpw.pl/ajaxindex.php?action=GPWQuotations&start=showTable&tab=swig80&lang=EN&full=0"),
	WIG30("https://www.gpw.pl/ajaxindex.php?action=GPWQuotations&start=showTable&tab=wig30&lang=EN&full=0"),
	RESPECT("https://www.gpw.pl/ajaxindex.php?action=GPWQuotations&start=showTable&tab=respect&lang=EN&full=0"),
	PWP("https://www.gpw.pl/ajaxindex.php?action=GPWQuotations&start=showTable&tab=pwp&lang=EN&full=0"),
	ALL("https://www.gpw.pl/ajaxindex.php?action=GPWQuotations&start=showTable&tab=all&lang=EN&full=0");
	
	private final String text;

    private StockIndexUrl(final String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }

}
