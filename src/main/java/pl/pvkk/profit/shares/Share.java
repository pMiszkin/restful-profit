package pl.pvkk.profit.shares;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

@Entity
public class Share {

	private String name;
	@Id
	private String isin;
	@OneToOne(fetch = FetchType.EAGER)
	private CurrentQuotation currentQuotation;
	@OneToMany(fetch = FetchType.LAZY)
	@OrderBy("date ASC")
	private List<ArchiveQuotation> archiveQuotations;
	@ElementCollection
	private List<String> indices;

	public Share() {
		archiveQuotations = new ArrayList<ArchiveQuotation>();
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

	public List<ArchiveQuotation> getArchiveQuotations() {
		return archiveQuotations;
	}

	public void setArchiveQuotations(List<ArchiveQuotation> archiveQuotations) {
		this.archiveQuotations = archiveQuotations;
	}

	public List<String> getIndices() {
		return indices;
	}

	public void setIndices(List<String> indices) {
		this.indices = indices;
	}

}
