package org.adorsys.adpharma.server.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class StockValueArticleSearchInput {
	
	/**
	    * The entity holding search inputs.
	    */
	   private Article entity;
	   
	   private Section section;
	   
	   private Boolean groupByArticle;
		
		private Boolean stockValueRepport;
		
		private Boolean breakArticleRepport;
		
		private Boolean beloThresholdArticleRepport;
	   

	   /**
	    * The start cursor
	    */
	   private int start = -1;

	   /**
	    * The max number of records to return.
	    */
	   private int max = -1;

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
		
		public Section getSection() {
			return section;
		}
		
		public void setSection(Section section) {
			this.section = section;
		}

}
