package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PeriodicalPrescriptionBookSearchResult {
	
	/*
	    * The number of entities matching this search.
	    */
	   private Long count;

	   /*
	    * The result list.
	    */
	   private List<PrescriptionBook> resultList;
	   
	   /*
	    * The original search input object. For stateless clients.
	    */
	   private PeriodicalPrescriptionBookDataSearchInput searchInput;

	public PeriodicalPrescriptionBookSearchResult() {
		super();
	}

	public PeriodicalPrescriptionBookSearchResult(Long count,
			List<PrescriptionBook> resultList,
			PeriodicalPrescriptionBookDataSearchInput searchInput) {
		super();
		this.count = count;
		this.resultList = resultList;
		this.searchInput = searchInput;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<PrescriptionBook> getResultList() {
		return resultList;
	}

	public void setResultList(List<PrescriptionBook> resultList) {
		this.resultList = resultList;
	}

	public PeriodicalPrescriptionBookDataSearchInput getSearchInput() {
		return searchInput;
	}

	public void setSearchInput(PeriodicalPrescriptionBookDataSearchInput searchInput) {
		this.searchInput = searchInput;
	}
	
	
	   
	  

}
