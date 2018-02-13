package pl.pvkk.profit.gpw;

import java.util.Date;

import org.springframework.stereotype.Service;

import pl.pvkk.profit.domain.shares.ArchivalQuotation;
import pl.pvkk.profit.domain.shares.CurrentQuotation;

@Service
public class ArchivalQuotationService {

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
