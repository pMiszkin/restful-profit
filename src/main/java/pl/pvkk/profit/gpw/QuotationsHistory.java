package pl.pvkk.profit.gpw;

import java.util.List;

public class QuotationsHistory {

	private String mode;
	private String isin;
	private int from;
	private int to;
	private List<Data> data;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "QuotationsHistory [mode=" + mode + ", isin=" + isin + ", from=" + from + ", to=" + to + ", data=" + data
				+ "]";
	}

}
