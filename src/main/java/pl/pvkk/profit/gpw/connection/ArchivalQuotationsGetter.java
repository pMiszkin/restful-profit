package pl.pvkk.profit.gpw.connection;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.pvkk.profit.domain.shares.ArchivalQuotation;
import pl.pvkk.profit.gpw.ArchivalQuotationHistory;
import pl.pvkk.profit.gpw.ArchivalQuotationService;
import pl.pvkk.profit.gpw.ArchivalQuotationUnformatted;

public class ArchivalQuotationsGetter extends GpwUrlConnector {

	private final static String URL_PREFIX = "https://www.gpw.pl/chart-json.php?req=[{%22isin%22:%22";
	private final static String URL_SUFFIX = "%22,%22mode%22:%22ARCH%22,%22from%22:null,%22to%22:null}]";
	@Autowired
	private ArchivalQuotationService archivalQuotationService;	
	
	public ArchivalQuotationsGetter(String shareIsin) throws IOException {
		super(URL_PREFIX+shareIsin+URL_SUFFIX);
	}
	
	public List<ArchivalQuotation> getArchivalQuotations() throws IOException {
		String body = content.getElementsByTag("body").text();
		ArchivalQuotationHistory quotationHistory = mapToQuotationHistory(body);
		List<ArchivalQuotationUnformatted> unformatted = quotationHistory.getArchivalQuotationUnformatted();
		
		return unformatted.stream()
				.map(archivalQuotationService::convertToArchive)
				.collect(Collectors.toList());
	}
	
	private ArchivalQuotationHistory mapToQuotationHistory(String body) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(body.substring(1, body.length()-1), ArchivalQuotationHistory.class);
	}

}
