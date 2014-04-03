package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;

import org.adorsys.javaext.description.Description;
import org.adorsys.adpharma.server.jpa.Agency;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;

import javax.validation.constraints.Size;

import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.adpharma.server.jpa.Article;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.javaext.format.DateFormatPattern;

import java.math.BigDecimal;

import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;

@Entity
@Description("ArticleLot_description")
@ListField({ "internalPic", "mainPic", "secondaryPic", "articleName",
	"article.articleName", "expirationDate", "stockQuantity",
	"salesPricePU", "purchasePricePU", "totalPurchasePrice",
"totalSalePrice" })
@ToStringField({ "articleName", "article.articleName" })
public class ArticleLot implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@ManyToOne
	@Description("ArticleLot_agency_description")
	@NotNull(message = "ArticleLot_agency_NotNull_validation")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
	private Agency agency;

	@Column
	@Description("ArticleLot_internalPic_description")
	@Size(min = 7, message = "ArticleLot_internalPic_Size_validation")
	@NotNull(message = "ArticleLot_internalPic_NotNull_validation")
	private String internalPic;

	@Column
	@Description("ArticleLot_mainPic_description")
	@Size(min = 7, message = "ArticleLot_mainPic_Size_validation")
	private String mainPic;

	@Column
	@Description("ArticleLot_secondaryPic_description")
	@Size(min = 7, message = "ArticleLot_secondaryPic_Size_validation")
	private String secondaryPic;

	@Column
	@Description("ArticleLot_articleName_description")
	@NotNull(message = "ArticleLot_articleName_NotNull_validation")
	private String articleName;

	@ManyToOne
	@Description("ArticleLot_article_description")
	@Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
	@NotNull(message = "ArticleLot_article_NotNull_validation")
	private Article article;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("ArticleLot_expirationDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy")
	private Date expirationDate;

	@Column
	@Description("ArticleLot_stockQuantity_description")
	private BigDecimal stockQuantity =BigDecimal.ZERO;

	@Column
	@Description("ArticleLot_salesPricePU_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal salesPricePU;

	@Column
	@Description("ArticleLot_purchasePricePU_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal purchasePricePU;

	@Column
	@Description("ArticleLot_totalPurchasePrice_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalPurchasePrice;

	@Column
	@Description("ArticleLot_totalSalePrice_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalSalePrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("ArticleLot_creationDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date creationDate;

	/**
	 * calculate tota sales and purchase price of this article lot
	 */
	public void calculateTotalAmout(){
		if(stockQuantity==null) return;
		totalPurchasePrice = totalPurchasePrice!=null?totalPurchasePrice:BigDecimal.ZERO;
		totalSalePrice = totalSalePrice!=null?totalSalePrice:BigDecimal.ZERO;
		salesPricePU = salesPricePU!=null?salesPricePU:BigDecimal.ZERO;
		purchasePricePU = purchasePricePU!=null?purchasePricePU:BigDecimal.ZERO;
		totalPurchasePrice = purchasePricePU.multiply(stockQuantity);
		totalSalePrice = salesPricePU.multiply(stockQuantity);

	}

	public static ArticleLot fromDeliveryItem(DeliveryItem deliveryItem,Login login){
		ArticleLot al = new  ArticleLot();
		al.setAgency(login.getAgency());
		al.setArticle(deliveryItem.getArticle());
		if(deliveryItem.getArticle()!=null)
			al.setArticleName(deliveryItem.getArticle().getArticleName());
		al.setCreationDate(new Date());
		al.setExpirationDate(deliveryItem.getExpirationDate());
		al.setInternalPic(deliveryItem.getInternalPic());
		al.setMainPic(deliveryItem.getMainPic());
		al.setSecondaryPic(deliveryItem.getSecondaryPic());
		al.setPurchasePricePU(deliveryItem.getPurchasePricePU());
		al.setSalesPricePU(deliveryItem.getSalesPricePU());
		al.setStockQuantity(al.getStockQuantity().add(deliveryItem.getStockQuantity()));
		al.calculateTotalAmout();
		return al;
	}
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
			return id.equals(((ArticleLot) that).id);
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

	public Agency getAgency()
	{
		return this.agency;
	}

	public void setAgency(final Agency agency)
	{
		this.agency = agency;
	}

	public String getInternalPic()
	{
		return this.internalPic;
	}

	public void setInternalPic(final String internalPic)
	{
		this.internalPic = internalPic;
	}

	public String getMainPic()
	{
		return this.mainPic;
	}

	public void setMainPic(final String mainPic)
	{
		this.mainPic = mainPic;
	}

	public String getSecondaryPic()
	{
		return this.secondaryPic;
	}

	public void setSecondaryPic(final String secondaryPic)
	{
		this.secondaryPic = secondaryPic;
	}

	public String getArticleName()
	{
		return this.articleName;
	}

	public void setArticleName(final String articleName)
	{
		this.articleName = articleName;
	}

	public Article getArticle()
	{
		return this.article;
	}

	public void setArticle(final Article article)
	{
		this.article = article;
	}

	public Date getExpirationDate()
	{
		return this.expirationDate;
	}

	public void setExpirationDate(final Date expirationDate)
	{
		this.expirationDate = expirationDate;
	}

	public BigDecimal getStockQuantity()
	{
		return this.stockQuantity;
	}

	public void setStockQuantity(final BigDecimal stockQuantity)
	{
		this.stockQuantity = stockQuantity;
	}

	public BigDecimal getSalesPricePU()
	{
		return this.salesPricePU;
	}

	public void setSalesPricePU(final BigDecimal salesPricePU)
	{
		this.salesPricePU = salesPricePU;
	}

	public BigDecimal getPurchasePricePU()
	{
		return this.purchasePricePU;
	}

	public void setPurchasePricePU(final BigDecimal purchasePricePU)
	{
		this.purchasePricePU = purchasePricePU;
	}

	public BigDecimal getTotalPurchasePrice()
	{
		return this.totalPurchasePrice;
	}

	public void setTotalPurchasePrice(final BigDecimal totalPurchasePrice)
	{
		this.totalPurchasePrice = totalPurchasePrice;
	}

	public BigDecimal getTotalSalePrice()
	{
		return this.totalSalePrice;
	}

	public void setTotalSalePrice(final BigDecimal totalSalePrice)
	{
		this.totalSalePrice = totalSalePrice;
	}

	public Date getCreationDate()
	{
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate)
	{
		this.creationDate = creationDate;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (internalPic != null && !internalPic.trim().isEmpty())
			result += "internalPic: " + internalPic;
		if (mainPic != null && !mainPic.trim().isEmpty())
			result += ", mainPic: " + mainPic;
		if (secondaryPic != null && !secondaryPic.trim().isEmpty())
			result += ", secondaryPic: " + secondaryPic;
		if (articleName != null && !articleName.trim().isEmpty())
			result += ", articleName: " + articleName;
		return result;
	}
}