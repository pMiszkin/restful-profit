package pl.pvkk.profit.gpw.connection;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.pvkk.profit.shares.quotations.CurrentQuotation;

public class CurrentQuotationGetter {

	private final static String URL_PREFIX="https://www.gpw.pl/ajaxindex.php?start=quotationsTab&format=html&action=GPWListaSp&gls_isin=";
	private Elements rows;
	private CurrentQuotation quotation = new CurrentQuotation();

	public CurrentQuotation getCurrentQuotation(String shareIsin, Date date) throws IOException {
		Document content = GpwUrlConnector.getPageContent(URL_PREFIX+shareIsin);
		setTableRows(content);
		setIndices();
		setCurrency();
		quotation.setPrice(parseRowWithNumber(2));
		setChange();
		quotation.setBid(parseRowWithNumber(4));
		quotation.setAsk(parseRowWithNumber(5));
		quotation.setMin(parseRowWithNumber(6));
		quotation.setMax(parseRowWithNumber(7));
		quotation.setVolume(parseRowWithNumber(8));
		quotation.setValue(parseRowWithNumber(9));
		quotation.setDate(date);
		return quotation;
	}

	private void setTableRows(Document content) {
		Element tbody = content.getElementsByTag("tbody").first();
		rows = tbody.children();
	}
	
	private void setIndices() {
		List<String> indices = new LinkedList<String>();
		Elements indexElements = rows.first().getElementsByTag("a");
		indexElements.forEach(indexElement -> indices.add(indexElement.text()));
		quotation.setIndices(indices);
	}
	
	private void setCurrency() {
		String rowText = rows.get(1).child(0).text();
		String currency = rowText.substring(rowText.indexOf("(")+1, rowText.indexOf(")"));
		quotation.setCurrency(currency);
	}
	
	private void setChange() {
		String rowText = rows.get(3).child(1).text();
		String changeText = rowText.substring(rowText.indexOf("(")+1, rowText.indexOf(")")-1);
		double change = Double.parseDouble(changeText);
		quotation.setChange(change);
	}
	
	private double parseRowWithNumber(int rowIndex) {
		String text = rows.get(rowIndex).child(1).text();
		String parsedText = text.replace(",",".")
				.replace("\u00A0", ""); // no-break space
		try {
			return Double.parseDouble(parsedText);
		} catch(NumberFormatException e) {
			return (double) 0;
		}
	}
	
	private String getTextBetweenBrackets(String text) {
		return text.substring(text.indexOf("(")+1, text.indexOf(")"));
	}
}
