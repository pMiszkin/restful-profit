package pl.pvkk.profit.gpw;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Quotation {

	//I'm really not sure about this builder <face with tears of joy>
	@Id
	@GeneratedValue
	private long id;
	
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

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLastTransactionTime() {
		return lastTransactionTime;
	}

	public void setLastTransactionTime(String lastTransactionTime) {
		this.lastTransactionTime = lastTransactionTime;
	}

	public double getReferencePrice() {
		return referencePrice;
	}

	public void setReferencePrice(double referencePrice) {
		this.referencePrice = referencePrice;
	}

	public String getTheoreticalOpenPrice() {
		return theoreticalOpenPrice;
	}

	public void setTheoreticalOpenPrice(String theoreticalOpenPrice) {
		this.theoreticalOpenPrice = theoreticalOpenPrice;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLastClosing() {
		return lastClosing;
	}

	public void setLastClosing(String lastClosing) {
		this.lastClosing = lastClosing;
	}

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public String getCumulatedVolume() {
		return cumulatedVolume;
	}

	public void setCumulatedVolume(String cumulatedVolume) {
		this.cumulatedVolume = cumulatedVolume;
	}

	public String getCumulatedValue() {
		return cumulatedValue;
	}

	public void setCumulatedValue(String cumulatedValue) {
		this.cumulatedValue = cumulatedValue;
	}

}
