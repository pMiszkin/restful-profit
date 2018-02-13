package pl.pvkk.profit.domain.shares;

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

import pl.pvkk.profit.domain.Transaction;

@Entity
public class Share {

	private String name;
	@Id
	private String isin;
	@OneToOne(fetch = FetchType.EAGER)
	private CurrentQuotation currentQuotation;
	@OneToMany(fetch = FetchType.LAZY)
	@OrderBy("date ASC")
	private List<ArchivalQuotation> archivalQuotations;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnoreProperties("share")
	private List<Transaction> transactions;

	public Share() {
		archivalQuotations = new ArrayList<ArchivalQuotation>();
		transactions = new LinkedList<Transaction>();
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

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
}
