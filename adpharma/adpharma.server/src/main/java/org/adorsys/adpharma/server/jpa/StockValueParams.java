package org.adorsys.adpharma.server.jpa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class StockValueParams {
	
	private Boolean groupByArticle;
	
	private Boolean stockValueRepport;
	
	private Boolean breakArticleRepport;
	
	private Boolean beloThresholdArticleRepport;

	public Boolean getGroupByArticle() {
		return groupByArticle;
	}

	public void setGroupByArticle(Boolean groupByArticle) {
		this.groupByArticle = groupByArticle;
	}

	public Boolean getStockValueRepport() {
		return stockValueRepport;
	}

	public void setStockValueRepport(Boolean stockValueRepport) {
		this.stockValueRepport = stockValueRepport;
	}

	public Boolean getBreakArticleRepport() {
		return breakArticleRepport;
	}

	public void setBreakArticleRepport(Boolean breakArticleRepport) {
		this.breakArticleRepport = breakArticleRepport;
	}

	public Boolean getBeloThresholdArticleRepport() {
		return beloThresholdArticleRepport;
	}

	public void setBeloThresholdArticleRepport(Boolean beloThresholdArticleRepport) {
		this.beloThresholdArticleRepport = beloThresholdArticleRepport;
	}
	
	

}
