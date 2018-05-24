package pl.pvkk.profit.shares;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StockIndex {

	@Id
	private String name;
	private String url;
	
	protected StockIndex(){}
	
	private StockIndex(Builder builder) {
		this.name = builder.name;
		this.url = builder.url;
	}
	
	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}
	
	public static class Builder {
		private String name;
		private String url;
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}
		
		public StockIndex build() {
			return new StockIndex(this);
		}
	}
}
