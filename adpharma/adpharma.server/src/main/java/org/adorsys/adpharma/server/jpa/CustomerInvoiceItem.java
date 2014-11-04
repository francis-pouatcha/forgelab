package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import java.lang.Override;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import org.adorsys.adpharma.server.jpa.Article;

import javax.persistence.ManyToOne;

import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;

import java.math.BigDecimal;
import java.util.Date;

import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;

@Entity
@Description("CustomerInvoiceItem_description")
@ToStringField({ "internalPic", "article.articleName", "purchasedQty" })
@ListField({ "internalPic", "article.articleName", "purchasedQty", "salesPricePU",
"totalSalesPrice" })
public class CustomerInvoiceItem implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	@Description("CustomerInvoiceItem_internalPic_description")
	private String internalPic;

	@ManyToOne
	@Description("CustomerInvoiceItem_article_description")
	@Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
	private Article article;

	@Column
	@Description("CustomerInvoiceItem_purchasedQty_description")
	private BigDecimal purchasedQty = BigDecimal.ZERO;

	@Column
	@Description("CustomerInvoiceItem_salesPricePU_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal salesPricePU = BigDecimal.ZERO ;

	@Column
	@Description("CustomerInvoiceItem_salesPricePU_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal purchasePricePU = BigDecimal.ZERO ;

	@Column
	@Description("CustomerInvoiceItem_totalSalesPrice_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalSalesPrice =  BigDecimal.ZERO;

	@ManyToOne
	@Description("CustomerInvoiceItem_invoice_description")
	@Association(associationType = AssociationType.COMPOSITION, targetEntity = CustomerInvoice.class)
	private CustomerInvoice invoice;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("CustomerInvoiceItem_creationDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date creationDate= new Date();


	public Long getId()
	{
		return this.id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public int getVersion()
	{
		return this.version;
	}

	public void setVersion(final int version)
	{
		this.version = version;
	}

	@Override
	public boolean equals(Object that)
	{
		if (this == that)
		{
			return true;
		}
		if (that == null)
		{
			return false;
		}
		if (getClass() != that.getClass())
		{
			return false;
		}
		if (id != null)
		{
			return id.equals(((CustomerInvoiceItem) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode()
	{
		if (id != null)
		{
			return id.hashCode();
		}
		return super.hashCode();
	}

	public String getInternalPic()
	{
		return this.internalPic;
	}

	public void setInternalPic(final String internalPic)
	{
		this.internalPic = internalPic;
	}

	public Article getArticle()
	{
		return this.article;
	}

	public void setArticle(final Article article)
	{
		this.article = article;
	}

	public BigDecimal getPurchasedQty()
	{
		return this.purchasedQty;
	}

	public void setPurchasedQty(final BigDecimal purchasedQty)
	{
		this.purchasedQty = purchasedQty;
	}

	public BigDecimal getSalesPricePU()
	{
		return this.salesPricePU;
	}

	public void setSalesPricePU(final BigDecimal salesPricePU)
	{
		this.salesPricePU = salesPricePU;
	}

	public BigDecimal getTotalSalesPrice()
	{
		return this.totalSalesPrice;
	}

	public void setTotalSalesPrice(final BigDecimal totalSalesPrice)
	{
		this.totalSalesPrice = totalSalesPrice;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (internalPic != null && !internalPic.trim().isEmpty())
			result += "internalPic: " + internalPic;
		return result;
	}

	public CustomerInvoice getInvoice()
	{
		return this.invoice;
	}

	public void setInvoice(final CustomerInvoice invoice)
	{
		this.invoice = invoice;
	}

	public BigDecimal getPurchasePricePU() {
		return purchasePricePU;
	}

	public void setPurchasePricePU(BigDecimal purchasePricePU) {
		this.purchasePricePU = purchasePricePU;
	}


}