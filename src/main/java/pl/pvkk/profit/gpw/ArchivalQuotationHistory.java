package pl.pvkk.profit.gpw;

import java.util.List;

public class ArchivalQuotationHistory {

	private String mode;
	private String isin;
	private int from;
	private int to;
	private List<ArchivalQuotationUnformatted> qUnformatted;

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

	public List<ArchivalQuotationUnformatted> getArchivalQuotationUnformatted() {
		return qUnformatted;
	}

	public void setArchivalQuotationUnformatted(List<ArchivalQuotationUnformatted> qUnformatted) {
		this.qUnformatted = qUnformatted;
	}

	@Override
	public String toString() {
		return "QuotationsHistory [mode=" + mode + ", isin=" + isin + ", from=" + from + ", to=" + to + ", data=" + qUnformatted
				+ "]";
	}
}
