package org.adorsys.adpharma.client.jpa.article;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class StockValueArticleSearchInput {
	    /**
	    * The entity holding search inputs.
	    */
	   private Article entity = new Article();
	   
	   private SimpleObjectProperty<ArticleSection> section;
	   
	   private SimpleBooleanProperty groupByArticle;
		
		private SimpleBooleanProperty stockValueRepport;
		
		private SimpleBooleanProperty breakArticleRepport;
		
		private SimpleBooleanProperty beloThresholdArticleRepport;
	   

	   /**
	    * The start cursor
	    */
	   private int start = 0;

	   /**
	    * The max number of records to return.
	    */
	   private int max = 5;

	   /**
	    * The field names to be included in the search.
	    */
	   private List<String> fieldNames = new ArrayList<String>();

	   public Article getEntity()
	   {
	      return entity;
	   }

	   public void setEntity(Article entity)
	   {
	      this.entity = entity;
	   }
	   
	   

	public List<String> getFieldNames()
	   {
	      return fieldNames;
	   }

	   public void setFieldNames(List<String> fieldNames)
	   {
	      this.fieldNames = fieldNames;
	   }

	   public int getStart()
	   {
	      return start;
	   }

	   public void setStart(int start)
	   {
	      this.start = start;
	   }

	   public int getMax()
	   {
	      return max;
	   }

	   public void setMax(int max)
	   {
	      this.max = max;
	   }
	
	   
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
		
		public SimpleObjectProperty<ArticleSection> sectionProperty()
		   {
		      if (section == null)
		      {
		         section = new SimpleObjectProperty<ArticleSection>(new ArticleSection());
		      }
		      return section;
		   }

		   public ArticleSection getSection()
		   {
		      return sectionProperty().get();
		   }

		   public final void setSection(ArticleSection section)
		   {
		      if (section == null)
		      {
		         section = new ArticleSection();
		      }
		      PropertyReader.copy(section, getSection());
		      sectionProperty().setValue(ObjectUtils.clone(getSection()));
		   }


}
