package pl.pvkk.profit.gpw;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Share {

	private String name;
	@Id
	private String shortcut;
	@OneToMany
	private List<Quotation> quotations;
	
	public Share() {
		quotations = new LinkedList<Quotation>();
	}
	
	@Override
	public String toString() {
		return "Share [name=" + name + ", shortcut=" + shortcut + ", quotations=" + quotations + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortcut() {
		return shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	public List<Quotation> getQuotations() {
		return quotations;
	}

	public void setQuotations(List<Quotation> quotations) {
		this.quotations = quotations;
	}
	
	
	
}
