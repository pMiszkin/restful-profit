package pl.pvkk.profit.shares;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pl.pvkk.profit.shares.quotations.ArchivalQuotation;
import pl.pvkk.profit.shares.quotations.CurrentQuotation;
import pl.pvkk.profit.trades.Transaction;


//TODO make it immutable
@Entity
public class Share {

	private String name;
	@Id
	private String isin;
	@OneToOne(fetch = FetchType.EAGER)
	private CurrentQuotation currentQuotation;
	@OneToMany(fetch = FetchType.LAZY)
	@OrderBy("date ASC")
	private List<ArchivalQuotation> archivalQuotations ;

	public Share() {
		archivalQuotations = new ArrayList<ArchivalQuotation>();
	}
	
	public Share(String name, String isin) {
		super();
		this.name = name;
		this.isin = isin;
	}

	public double getQurrentQuotationPrice() {
		CurrentQuotation quotation = getCurrentQuotation();
		return quotation.getPrice();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public CurrentQuotation getCurrentQuotation() {
		return currentQuotation;
	}

	public void setCurrentQuotation(CurrentQuotation currentQuotation) {
		this.currentQuotation = currentQuotation;
	}

	public List<ArchivalQuotation> getArchiveQuotations() {
		return archivalQuotations;
	}

	public void setArchiveQuotations(List<ArchivalQuotation> archivalQuotations) {
		this.archivalQuotations = archivalQuotations;
	}
}
