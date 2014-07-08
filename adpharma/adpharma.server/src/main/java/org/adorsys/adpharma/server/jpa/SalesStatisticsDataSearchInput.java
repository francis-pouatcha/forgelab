package org.adorsys.adpharma.server.jpa;


public class SalesStatisticsDataSearchInput {

	private Integer years ;
	
	private Customer customer ;
	
	private Article article ;

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	
	
	
	
	
}
