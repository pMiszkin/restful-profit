package pl.pvkk.profit.gpw.connection;

import static pl.pvkk.profit.shares.SampleShares.getProperIsin;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import pl.pvkk.profit.shares.quotations.CurrentQuotation;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrentQuotationGetterIntegration {

	@Test
	public void tryToGetSampleQuotation() {
		CurrentQuotationGetter getter = new CurrentQuotationGetter();
		CurrentQuotation quotation;
		try {
			quotation = getter.getCurrentQuotation(getProperIsin(), new Date());	
		} catch(IOException e) {
			System.out.println(e);
		}
	}
}
