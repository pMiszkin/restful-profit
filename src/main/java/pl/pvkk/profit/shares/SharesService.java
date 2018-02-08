package pl.pvkk.profit.shares;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.domain.shares.ArchivalQuotation;
import pl.pvkk.profit.domain.shares.CurrentQuotation;
import pl.pvkk.profit.domain.shares.Share;
import pl.pvkk.profit.gpw.ArchivalQuotationUnformatted;


@Service
public class SharesService {
	
	@Autowired
	private SharesDao sharesDao;
	
	public Share findShareByIsin(String isin) {
		return sharesDao.findShareById(isin.toUpperCase());
	}
	
	public double getQurrentQuotationPrice(Share share) {
		CurrentQuotation quotation = share.getCurrentQuotation();
		return quotation.getPrice();
	}
	
	public ArchivalQuotation convertToArchive(CurrentQuotation current) {
		ArchivalQuotation archive = new ArchivalQuotation();
		archive.setPrice(current.getPrice());
		archive.setDate(current.getDate());
		//archive.setOpen(qData.getO());   WUT
		archive.setMax(current.getMax());
		archive.setMin(current.getMin());
		archive.setVolume(current.getVolume());
		return archive;
	}
	
	public ArchivalQuotation convertToArchive(ArchivalQuotationUnformatted unformatted) {
		ArchivalQuotation archive = new ArchivalQuotation();
		Date date = new Date();
		date.setTime(unformatted.getT()*360L);
		archive.setDate(date);
		archive.setPrice(unformatted.getC());
		//archive.setOpen(unformatted.getO());   WUT
		archive.setMax(unformatted.getH());
		archive.setMin(unformatted.getL());
		archive.setVolume(unformatted.getV());
		return archive;
	}
}
