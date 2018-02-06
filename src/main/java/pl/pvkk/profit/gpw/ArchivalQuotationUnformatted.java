package pl.pvkk.profit.gpw;

public class ArchivalQuotationUnformatted {

	//as Date
	private int t;
	//as open
	private double o;
	//referencePrice
	private double c;
	//high
	private double h;
	//low
	private double l;
	//volume
	private double v;

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public double getO() {
		return o;
	}

	public void setO(double o) {
		this.o = o;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getL() {
		return l;
	}

	public void setL(double l) {
		this.l = l;
	}

	public double getV() {
		return v;
	}

	public void setV(double v) {
		this.v = v;
	}

	@Override
	public String toString() {
		return "Data [t=" + t + ", o=" + o + ", c=" + c + ", h=" + h + ", l=" + l + ", v=" + v + "]";
	}
}
