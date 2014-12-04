package org.adorsys.adpharma.client.jpa.article;

import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.SimpleBooleanProperty;

@XmlRootElement
public class StockValueParams {
	
	private SimpleBooleanProperty groupByArticle;
	
	private SimpleBooleanProperty stockValueRepport;
	
	private SimpleBooleanProperty breakArticleRepport;
	
	private SimpleBooleanProperty beloThresholdArticleRepport;
	
	
	public SimpleBooleanProperty groupByArticleProperty()
	{
		if (groupByArticle == null)
		{
			groupByArticle = new SimpleBooleanProperty();
		}
		return groupByArticle;
	}

	public Boolean getGroupByArticle()
	{
		return groupByArticleProperty().get();
	}

	public final void setGroupByArticle(Boolean groupByArticle)
	{
		if (groupByArticle == null) {
			groupByArticle = Boolean.FALSE;
		}
		this.groupByArticleProperty().set(groupByArticle);
	}
	
	public SimpleBooleanProperty stockValueRepportProperty()
	{
		if (stockValueRepport == null)
		{
			stockValueRepport = new SimpleBooleanProperty(); 
		}
		return stockValueRepport;
	}

	public Boolean getStockValueRepport()
	{
		return stockValueRepportProperty().get();
	}

	public final void setStockValueRepport(Boolean stockValueRepport)
	{
		if (stockValueRepport == null)
			stockValueRepport = Boolean.FALSE;
		this.stockValueRepportProperty().set(stockValueRepport);
	}
	
	
	public SimpleBooleanProperty beloThresholdArticleRepportProperty()
	{
		if (beloThresholdArticleRepport == null)
		{
			beloThresholdArticleRepport = new SimpleBooleanProperty();
		}
		return beloThresholdArticleRepport;
	}

	public Boolean getBeloThresholdArticleRepport()
	{
		return beloThresholdArticleRepportProperty().get();
	}

	public final void setBeloThresholdArticleRepport(Boolean beloThresholdArticleRepport)
	{
		if (beloThresholdArticleRepport == null)
			beloThresholdArticleRepport = Boolean.FALSE;
		this.beloThresholdArticleRepportProperty().set(beloThresholdArticleRepport);
	}
	
	
	public SimpleBooleanProperty breakArticleRepportProperty()
	{
		if (breakArticleRepport == null)
		{
			breakArticleRepport = new SimpleBooleanProperty();
		}
		return breakArticleRepport;
	}

	public Boolean getBreakArticleRepport()
	{
		return breakArticleRepportProperty().get();
	}

	public final void setBreakArticleRepport(Boolean breakArticleRepport)
	{
		if (breakArticleRepport == null)
			breakArticleRepport = Boolean.FALSE;
		this.breakArticleRepportProperty().set(breakArticleRepport);
	}
	
	

}
