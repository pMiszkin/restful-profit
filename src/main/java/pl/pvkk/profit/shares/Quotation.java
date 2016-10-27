package pl.pvkk.profit.shares;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Quotation {

	@Id
	@GeneratedValue
	private long id;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;
	private String currency;
	private String lastTransactionTime;
	private double referencePrice;
	private double theoreticalOpenPrice;
	private double open;
	private double low;
	private double high;
	private double lastClosing;
	private double change;
	private double cumulatedVolume;
	private double cumulatedValue;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public double getTheoreticalOpenPrice() {
		return theoreticalOpenPrice;
	}

	public void setTheoreticalOpenPrice(double theoreticalOpenPrice) {
		this.theoreticalOpenPrice = theoreticalOpenPrice;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLastClosing() {
		return lastClosing;
	}

	public void setLastClosing(double lastClosing) {
		this.lastClosing = lastClosing;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public double getCumulatedVolume() {
		return cumulatedVolume;
	}

	public void setCumulatedVolume(double cumulatedVolume) {
		this.cumulatedVolume = cumulatedVolume;
	}

	public double getCumulatedValue() {
		return cumulatedValue;
	}

	public void setCumulatedValue(double cumulatedValue) {
		this.cumulatedValue = cumulatedValue;
	}

}
