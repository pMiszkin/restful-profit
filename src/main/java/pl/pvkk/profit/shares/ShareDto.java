package pl.pvkk.profit.shares;

import java.util.List;

import pl.pvkk.profit.shares.quotations.ArchivalQuotation;
import pl.pvkk.profit.shares.quotations.CurrentQuotation;
import pl.pvkk.profit.trades.Transaction;

public class ShareDto {

	private String name;
	private String isin;
	private CurrentQuotation currentQuotation;
	private List<ArchivalQuotation> archivalQuotations;
	private List<Transaction> transactions;

	private ShareDto(Builder builder) {
		this.name = builder.name;
		this.isin = builder.isin;
		this.currentQuotation = builder.currentQuotation;
		this.archivalQuotations = builder.archivalQuotations;
		this.transactions = builder.transactions;
	}

	public String getName() {
		return name;
	}

	public String getIsin() {
		return isin;
	}

	public CurrentQuotation getCurrentQuotation() {
		return currentQuotation;
	}

	public List<ArchivalQuotation> getArchivalQuotations() {
		return archivalQuotations;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	protected static class Builder {
		private String name;
		private String isin;
		private CurrentQuotation currentQuotation;
		private List<ArchivalQuotation> archivalQuotations;
		private List<Transaction> transactions;

		ShareDto build(Builder builder) {
			return new ShareDto(builder);
		}

		Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		Builder setIsin(String isin) {
			this.isin = isin;
			return this;
		}
		
		Builder setCurrentQuotation(CurrentQuotation currentQuotation) {
			this.currentQuotation = currentQuotation;
			return this;
		}
		
		Builder setArchivalQuotations(List<ArchivalQuotation> archivalQuotations) {
			this.archivalQuotations = archivalQuotations;
			return this;
		}
		
		Builder setTransactions(List<Transaction> transactions) {
			this.transactions = transactions;
			return this;
		}
	}

}
