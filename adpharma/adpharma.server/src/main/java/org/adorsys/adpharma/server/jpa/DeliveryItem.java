package org.adorsys.adpharma.server.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adpharma.server.utils.CurencyUtil;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;

@Entity
@Description("DeliveryItem_description")
@ListField({ "internalPic", "mainPic", "secondaryPic", "articleName",
	"article.articleName", "expirationDate", "qtyOrdered", "freeQuantity",
	"stockQuantity", "salesPricePU", "purchasePricePU",
"totalPurchasePrice" })
@ToStringField({ "articleName", "article.articleName", "qtyOrdered" })
public class DeliveryItem implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("DeliveryItem_creationDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date creationDate;

	@Column
	@Description("DeliveryItem_internalPic_description")
	@Size(min = 1, message = "DeliveryItem_internalPic_Size_validation")
	@NotNull(message = "DeliveryItem_internalPic_NotNull_validation")
	private String internalPic;

	@Column
	@Description("DeliveryItem_mainPic_description")
	@Size(min = 1, message = "DeliveryItem_mainPic_Size_validation")
	private String mainPic;

	@Column
	@Description("DeliveryItem_secondaryPic_description")
	@Size(min = 1, message = "DeliveryItem_secondaryPic_Size_validation")
	private String secondaryPic;

	@Column
	@Description("DeliveryItem_articleName_description")
	@NotNull(message = "DeliveryItem_articleName_NotNull_validation")
	private String articleName;

	@ManyToOne
	@Description("DeliveryItem_article_description")
	@Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
	@NotNull(message = "DeliveryItem_article_NotNull_validation")
	private Article article;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("DeliveryItem_expirationDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy")
	private Date expirationDate;

	@Column
	@Description("DeliveryItem_qtyOrdered_description")
	private BigDecimal qtyOrdered = BigDecimal.ZERO;

	@Column
	@Description("DeliveryItem_availableQty_description")
	private BigDecimal availableQty = BigDecimal.ZERO;

	@Column
	@Description("DeliveryItem_freeQuantity_description")
	private BigDecimal freeQuantity = BigDecimal.ZERO;

	@ManyToOne
	@Description("DeliveryItem_creatingUser_description")
	@NotNull(message = "DeliveryItem_creatingUser_NotNull_validation")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
	private Login creatingUser;

	@Column
	@Description("DeliveryItem_stockQuantity_description")
	private BigDecimal stockQuantity = BigDecimal.ZERO;

	@Column
	@Description("DeliveryItem_salesPricePU_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal salesPricePU = BigDecimal.ZERO;

	@Column
	@Description("DeliveryItem_purchasePricePU_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal purchasePricePU = BigDecimal.ZERO;

	@Column
	@Description("DeliveryItem_totalPurchasePrice_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalPurchasePrice = BigDecimal.ZERO;

	@ManyToOne
	@Description("DeliveryItem_delivery_description")
	@Association(associationType = AssociationType.COMPOSITION, targetEntity = Delivery.class)
	private Delivery delivery;

	public void calculateAmount(){
		salesPricePU = CurencyUtil.convertToCfa(delivery.getCurrency(), salesPricePU!=null?salesPricePU:BigDecimal.ZERO);
		purchasePricePU = CurencyUtil.convertToCfa(delivery.getCurrency(), purchasePricePU!=null?purchasePricePU:BigDecimal.ZERO);
		stockQuantity = stockQuantity!=null?stockQuantity:BigDecimal.ZERO; 
		freeQuantity = freeQuantity!=null?freeQuantity:BigDecimal.ZERO; 
		totalPurchasePrice= purchasePricePU.multiply(stockQuantity.subtract(freeQuantity));
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
			return id.equals(((DeliveryItem) that).id);
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

	public Date getCreationDate()
	{
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate)
	{
		this.creationDate = creationDate;
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

	public BigDecimal getQtyOrdered()
	{
		return this.qtyOrdered;
	}

	public void setQtyOrdered(final BigDecimal qtyOrdered)
	{
		this.qtyOrdered = qtyOrdered;
	}

	public BigDecimal getAvailableQty()
	{
		return this.availableQty;
	}

	public void setAvailableQty(final BigDecimal availableQty)
	{
		this.availableQty = availableQty;
	}

	public BigDecimal getFreeQuantity()
	{
		return this.freeQuantity;
	}

	public void setFreeQuantity(final BigDecimal freeQuantity)
	{
		this.freeQuantity = freeQuantity;
	}

	public Login getCreatingUser()
	{
		return this.creatingUser;
	}

	public void setCreatingUser(final Login creatingUser)
	{
		this.creatingUser = creatingUser;
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

	public Delivery getDelivery()
	{
		return this.delivery;
	}

	public void setDelivery(final Delivery delivery)
	{
		this.delivery = delivery;
	}
}