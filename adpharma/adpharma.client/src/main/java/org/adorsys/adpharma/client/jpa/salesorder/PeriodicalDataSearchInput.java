package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.Calendar;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleAgency;
import org.adorsys.adpharma.client.jpa.article.ArticleSection;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PeriodicalDataSearchInput {

	private SimpleObjectProperty<Calendar> beginDate;

	private SimpleObjectProperty<Calendar> endDate;
	
	private SimpleObjectProperty<ArticleSection> section;
	
	private SimpleObjectProperty<Article> article;

	private SimpleBooleanProperty check;
	
	private SimpleBooleanProperty printXls;

	private SimpleBooleanProperty taxableSalesOnly ;

	private SimpleBooleanProperty nonTaxableSalesOnly;

	private SimpleBooleanProperty twentyOverHeightySalesOnly;
	
	private SimpleBooleanProperty twentyOverHeightyInQty;
	
	private SimpleBooleanProperty perVendorAndDiscount;
	
	private SimpleStringProperty pic;
	
	private SimpleStringProperty articleName ; 
	
	private SimpleStringProperty internalPic ;
	
	private SimpleBooleanProperty dayllySales;
	
	public SimpleObjectProperty<Article> articleProperty()
	   {
	      if (article == null)
	      {
	    	  article = new SimpleObjectProperty<Article>(new Article());
	      }
	      return article;
	   }

	   public Article getArticle()
	   {
	      return articleProperty().get();
	   }

	   public final void setArticle(Article section)
	   {
	      if (section == null)
	      {
	         section = new Article();
	      }
	      PropertyReader.copy(section, getSection());
	      sectionProperty().setValue(ObjectUtils.clone(getSection()));
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
	 
	   
	   public SimpleStringProperty internalPicProperty()
	   {
	      if (internalPic == null)
	      {
	    	  internalPic = new SimpleStringProperty();
	      }
	      return internalPic;
	   }

	   public String getInternalPic()
	   {
	      return internalPicProperty().get();
	   }

	   public final void setInternalPic(String internalPic)
	   {
	      this.internalPicProperty().set(internalPic);
	   }
	   
	   public SimpleStringProperty articleNameProperty()
	   {
	      if (articleName == null)
	      {
	    	  articleName = new SimpleStringProperty();
	      }
	      return articleName;
	   }

	   public String getArticleName()
	   {
	      return articleNameProperty().get();
	   }

	   public final void setArticleName(String articleName)
	   {
	      this.articleNameProperty().set(articleName);
	   }

	public SimpleObjectProperty<Calendar> beginDateProperty()
	{
		if (beginDate == null)
		{
			beginDate = new SimpleObjectProperty<Calendar>();
		}
		return beginDate;
	}
	public SimpleStringProperty picProperty()
	   {
	      if (pic == null)
	      {
	    	  pic = new SimpleStringProperty();
	      }
	      return pic;
	   }

	   public String getPic()
	   {
	      return picProperty().get();
	   }

	   public final void setPic(String pic)
	   {
	      this.picProperty().set(pic);
	   }

	public Calendar getBeginDate()
	{
		return beginDateProperty().get();
	}

	public  void setBeginDate(Calendar beginDate)
	{
		this.beginDateProperty().set(beginDate);
	}

	public SimpleObjectProperty<Calendar> endDateProperty()
	{
		if (endDate == null)
		{
			endDate = new SimpleObjectProperty<Calendar>();
		}
		return endDate;
	}

	public Calendar getEndDate()
	{
		return endDateProperty().get();
	}

	public  void setEndDate(Calendar endDate)
	{
		this.endDateProperty().set(endDate);
	}

	public SimpleBooleanProperty checkProperty()
	{
		if (check == null)
		{
			check = new SimpleBooleanProperty();
		}
		return check;
	}

	public Boolean getCheck()
	{
		return checkProperty().get();
	}

	public final void setCheck(Boolean check)
	{
		if (check == null)
			check = Boolean.FALSE;
		this.checkProperty().set(check);
	}
	
	public SimpleBooleanProperty dayllySalesProperty()
	{
		if (dayllySales == null)
		{
			dayllySales = new SimpleBooleanProperty();
		}
		return dayllySales;
	}

	public Boolean getdayllySales()
	{
		return dayllySalesProperty().get();
	}

	public final void setdayllySales(Boolean dayllySales)
	{
		if (dayllySales == null)
			dayllySales = Boolean.FALSE;
		this.dayllySalesProperty().set(dayllySales);
	}
	
	public SimpleBooleanProperty printXlsProperty()
	{
		if (printXls == null)
		{
			printXls = new SimpleBooleanProperty();
		}
		return printXls;
	}

	public Boolean getPrintXls()
	{
		return printXlsProperty().get();
	}

	public final void setPrintXls(Boolean printXls)
	{
		if (printXls == null)
			printXls = Boolean.FALSE;
		this.printXlsProperty().set(printXls);
	}

	public SimpleBooleanProperty twentyOverHeightySalesOnlyProperty()
	{
		if (twentyOverHeightySalesOnly == null)
		{
			twentyOverHeightySalesOnly = new SimpleBooleanProperty();
		}
		return twentyOverHeightySalesOnly;
	}

	public Boolean getTwentyOverHeightySalesOnly()
	{
		return twentyOverHeightySalesOnlyProperty().get();
	}

	public final void setTwentyOverHeightySalesOnly(Boolean twentyOverHeightySalesOnly)
	{
		if (twentyOverHeightySalesOnly == null)
			twentyOverHeightySalesOnly = Boolean.FALSE;
		this.twentyOverHeightySalesOnlyProperty().set(twentyOverHeightySalesOnly);
	}
	
	public SimpleBooleanProperty twentyOverHeightyInQtyProperty()
	{
		if (twentyOverHeightyInQty == null)
		{
			twentyOverHeightyInQty = new SimpleBooleanProperty();
		}
		return twentyOverHeightyInQty;
	}

	public Boolean getTwentyOverHeightyInQty()
	{
		return twentyOverHeightyInQtyProperty().get();
	}

	public final void setTwentyOverHeightyInQty(Boolean twentyOverHeightyInQty)
	{
		if (twentyOverHeightyInQty == null)
			twentyOverHeightyInQty = Boolean.FALSE;
		this.twentyOverHeightyInQtyProperty().set(twentyOverHeightyInQty);
	}

	public SimpleBooleanProperty nonTaxableSalesOnlyProperty()
	{
		if (nonTaxableSalesOnly == null)
		{
			nonTaxableSalesOnly = new SimpleBooleanProperty();
		}
		return nonTaxableSalesOnly;
	}

	public Boolean getNonTaxableSalesOnly()
	{
		return nonTaxableSalesOnlyProperty().get();
	}

	public final void setNonTaxableSalesOnly(Boolean nonTaxableSalesOnly)
	{
		if (nonTaxableSalesOnly == null)
			nonTaxableSalesOnly = Boolean.FALSE;
		this.nonTaxableSalesOnlyProperty().set(nonTaxableSalesOnly);
	}

	public SimpleBooleanProperty taxableSalesOnlyProperty()
	{
		if (taxableSalesOnly == null)
		{
			taxableSalesOnly = new SimpleBooleanProperty();
		}
		return taxableSalesOnly;
	}

	public Boolean getTaxableSalesOnly()
	{
		return taxableSalesOnlyProperty().get();
	}

	public final void setTaxableSalesOnly(Boolean taxableSalesOnly)
	{
		if (taxableSalesOnly == null)
			taxableSalesOnly = Boolean.FALSE;
		this.taxableSalesOnlyProperty().set(taxableSalesOnly);
	}
	
	public SimpleBooleanProperty perVendorAndDiscountProperty()
	{
		if (perVendorAndDiscount == null)
		{
			perVendorAndDiscount = new SimpleBooleanProperty();
		}
		return perVendorAndDiscount;
	}
	
	public Boolean getPerVendorAndDiscount()
	{
		return perVendorAndDiscountProperty().get();
	}
	
	public final void setPerVendorAndDiscount(Boolean perVendorAndDiscount)
	{
		if (perVendorAndDiscount == null)
			perVendorAndDiscount = Boolean.FALSE;
		this.perVendorAndDiscountProperty().set(perVendorAndDiscount);
	}
	

	public String toString()
	{
		return PropertyReader.buildToString(this, "beginDate","endDate");
	}

	

}
