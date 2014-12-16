package org.adorsys.adpharma.server.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Description("SalesOrderItem_description")
@ListField({ "orderedQty", "returnedQty", "deliveredQty", "salesPricePU",
	"totalSalePrice", "internalPic", "article.articleName", "vat.rate", "article.pppu"})
@ToStringField("article.articleName")
public class SalesOrderItem implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	@Description("SalesOrderItem_orderedQty_description")
	private BigDecimal orderedQty;

	@Column
	@Description("SalesOrderItem_returnedQty_description")
	private BigDecimal returnedQty;

	@Column
	@Description("SalesOrderItem_deliveredQty_description")
	private BigDecimal deliveredQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SalesOrderItem_recordDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date recordDate;

	@Column
	@Description("SalesOrderItem_salesPricePU_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal salesPricePU;


	@Column
	@Description("SalesOrderItem_purchasePricePU_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal purchasePricePU = BigDecimal.ZERO ;

	@Column
	@Description("SalesOrderItem_totalSalePrice_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalSalePrice;
	
	@Column
	@Description("SalesOrderItem_vatValue_description")
	@NumberFormatType(NumberType.CURRENCY)
	private transient BigDecimal vatValue;

	@Column
	@Description("SalesOrderItem_internalPic_description")
	@Size(min = 0, message = "SalesOrderItem_internalPic_Size_validation")
	private String internalPic;

	@ManyToOne
	@Description("SalesOrderItem_article_description")
	@Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
	@NotNull(message = "SalesOrderItem_article_NotNull_validation")
	private Article article;

	@ManyToOne
	@Description("SalesOrderItem_vat_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = VAT.class)
	private VAT vat;

	@ManyToOne
	@Description("SalesOrderItem_salesOrder_description")
	@Association(associationType = AssociationType.COMPOSITION, targetEntity = SalesOrder.class)
	private SalesOrder salesOrder;

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
			return id.equals(((SalesOrderItem) that).id);
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

	public BigDecimal getOrderedQty()
	{
		return this.orderedQty;
	}

	public void setOrderedQty(final BigDecimal orderedQty)
	{
		this.orderedQty = orderedQty;
	}

	public BigDecimal getReturnedQty()
	{
		return this.returnedQty;
	}

	public void setReturnedQty(final BigDecimal returnedQty)
	{
		this.returnedQty = returnedQty;
	}

	public BigDecimal getDeliveredQty()
	{
		return this.deliveredQty;
	}

	public void setDeliveredQty(final BigDecimal deliveredQty)
	{
		this.deliveredQty = deliveredQty;
	}

	public Date getRecordDate()
	{
		return this.recordDate;
	}

	public void setRecordDate(final Date recordDate)
	{
		this.recordDate = recordDate;
	}

	public BigDecimal getSalesPricePU()
	{
		return this.salesPricePU;
	}

	public void setSalesPricePU(final BigDecimal salesPricePU)
	{
		this.salesPricePU = salesPricePU;
	}

	public BigDecimal getTotalSalePrice()
	{
		return this.totalSalePrice;
	}

	public void setTotalSalePrice(final BigDecimal totalSalePrice)
	{
		this.totalSalePrice = totalSalePrice;
	}

	public BigDecimal getVatValue() {
		return vatValue;
	}

	public void setVatValue(BigDecimal vatValue) {
		this.vatValue = vatValue;
	}

	public String getInternalPic()
	{
		return this.internalPic;
	}

	public void setInternalPic(final String internalPic)
	{
		this.internalPic = internalPic;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (internalPic != null && !internalPic.trim().isEmpty())
			result += "internalPic: " + internalPic;
		return result;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(final Article article)
	{
		this.article = article;
	}

	public VAT getVat()
	{
		return this.vat;
	}

	public void setVat(final VAT vat)
	{
		this.vat = vat;
	}

	public SalesOrder getSalesOrder()
	{
		return this.salesOrder;
	}

	public void setSalesOrder(final SalesOrder salesOrder)
	{
		this.salesOrder = salesOrder;
	}


	public void calculateAmount(){
		if(salesPricePU==null|| orderedQty==null) throw new IllegalArgumentException("salesPricePU and ordredQty are requiered");
		totalSalePrice = totalSalePrice!=null?totalSalePrice:BigDecimal.ZERO; 
		totalSalePrice= salesPricePU.multiply(orderedQty);
	}

	public void calucateDeliveryQty(){
		if(orderedQty==null) throw new IllegalArgumentException("ordredQty are requiered");
		returnedQty = returnedQty!=null?returnedQty:BigDecimal.ZERO; 
		deliveredQty=orderedQty.subtract(returnedQty);

	}

	public boolean hasReturnArticle(){
		returnedQty = returnedQty!=null?returnedQty:BigDecimal.ZERO;
		return BigDecimal.ZERO.compareTo(returnedQty)<0 ;
	}

	public void updateTotalSalesPrice(){
		setTotalSalePrice(getOrderedQty().multiply(getSalesPricePU()));
	}

	public BigDecimal getPurchasePricePU() {
		return purchasePricePU;
	}

	public void setPurchasePricePU(BigDecimal purchasePricePU) {
		this.purchasePricePU = purchasePricePU;
	}
	
	
}