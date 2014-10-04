package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SalesOrderDiscountSearchResult {
	
	/*
	  * The number of entities matching this search.
	 */
	private Long count;
	
	/*
	    * The result list.
	    */
	private List<SalesOrderDiscount> resultList;

	public SalesOrderDiscountSearchResult() {
		super();
	}

	public SalesOrderDiscountSearchResult(Long count,
			List<SalesOrderDiscount> resultList) {
		super();
		this.count = count;
		this.resultList = resultList;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<SalesOrderDiscount> getResultList() {
		return resultList;
	}

	public void setResultList(List<SalesOrderDiscount> resultList) {
		this.resultList = resultList;
	}
	
	
	
	

}
