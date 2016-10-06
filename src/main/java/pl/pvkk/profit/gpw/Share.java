package pl.pvkk.profit.gpw;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Share {

	// i'm really not sure about this sheet of code <face_with_tears_of_joy>
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String shortcut;
	private String currency;
	private String lastTransactionTime;
	private double referencePrice;
	private String theoreticalOpenPrice;
	private String open;
	private String low;
	private String high;
	private String lastClosing;
	private String change;
	private String cumulatedVolume;
	private String cumulatedValue;

	public Share() {}
	
	private Share(Builder builder) {
		this.name = builder.name;
		this.shortcut = builder.shortcut;
		this.currency = builder.currency;
		this.lastTransactionTime = builder.lastTransactionTime;
		//its not normal space. it is no-break space!      							  V
		this.referencePrice = Double.parseDouble(builder.referencePrice.replaceAll("\u00A0", ""));
		this.theoreticalOpenPrice = builder.theoreticalOpenPrice;
		this.open = builder.open;
		this.low = builder.low;
		this.high = builder.high;
		this.lastClosing = builder.lastClosing;
		this.change = builder.change;
		this.cumulatedVolume = builder.cumulatedVolume;
		this.cumulatedValue = builder.cumulatedValue;
	}
	
	@Override
	public String toString() {
		return "Share [name=" + name + ", shortcut=" + shortcut + ", currency=" + currency + ", lastTransactionTime="
				+ lastTransactionTime + ", referencePrice=" + referencePrice + ", theoreticalOpenPrice="
				+ theoreticalOpenPrice + ", open=" + open + ", low=" + low + ", high=" + high + ", lastClosing="
				+ lastClosing + ", change=" + change + ", cumulatedVolume=" + cumulatedVolume + ", cumulatedValue="
				+ cumulatedValue + "]";
	}
	
	public String getName() {
		return name;
	}

	public String getShortcut() {
		return shortcut;
	}

	public String getCurrency() {
		return currency;
	}

	public String getLastTransactionTime() {
		return lastTransactionTime;
	}

	public double getReferencePrice() {
		return referencePrice;
	}

	public String getTheoreticalOpenPrice() {
		return theoreticalOpenPrice;
	}

	public String getOpen() {
		return open;
	}

	public String getLow() {
		return low;
	}

	public String getHigh() {
		return high;
	}

	public String getLastClosing() {
		return lastClosing;
	}

	public String getChange() {
		return change;
	}

	public String getCumulatedVolume() {
		return cumulatedVolume;
	}

	public String getCumulatedValue() {
		return cumulatedValue;
	}

	public static class Builder {

		private final String name;
		private String shortcut;
		private String currency;
		private String lastTransactionTime;
		private String referencePrice;
		private String theoreticalOpenPrice;
		private String open;
		private String low;
		private String high;
		private String lastClosing;
		private String change;
		private String cumulatedVolume;
		private String cumulatedValue;

		public Builder(final String name) {
			this.name = name;
		}

		public Builder shortcut(final String shortcut) {
			this.shortcut = shortcut;
			return this;
		}

		public Builder currency(final String currency) {
			this.currency = currency;
			return this;
		}

		public Builder lastTransactionTime(final String lastTransactionTime) {
			this.lastTransactionTime = lastTransactionTime;
			return this;
		}

		public Builder referencePrice(final String referencePrice) {
			this.referencePrice = referencePrice;
			return this;
		}

		public Builder theoreticalOpenPrice(final String theoreticalOpenPrice) {
			this.theoreticalOpenPrice = theoreticalOpenPrice;
			return this;
		}

		public Builder open(final String open) {
			this.open = open;
			return this;
		}

		public Builder low(final String low) {
			this.low = low;
			return this;
		}

		public Builder high(final String high) {
			this.high = high;
			return this;
		}

		public Builder lastClosing(final String lastClosing) {
			this.lastClosing = lastClosing;
			return this;
		}

		public Builder change(final String change) {
			this.change = change;
			return this;
		}

		public Builder cumulatedVolume(final String cumulatedVolume) {
			this.cumulatedVolume = cumulatedVolume;
			return this;
		}

		public Builder cumulatedValue(final String cumulatedValue) {
			this.cumulatedValue = cumulatedValue;
			return this;
		}

		public Share build() {
			return new Share(this);
		}
	}
}
