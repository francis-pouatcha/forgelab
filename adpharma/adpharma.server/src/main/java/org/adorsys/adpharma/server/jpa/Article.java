package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.PostPersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import java.lang.Override;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

import javax.validation.constraints.NotNull;

import org.adorsys.adpharma.server.jpa.Section;

import javax.persistence.ManyToOne;

import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.adpharma.server.jpa.ProductFamily;

import java.math.BigDecimal;

import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.adpharma.server.jpa.SalesMargin;
import org.adorsys.adpharma.server.jpa.PackagingMode;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.ClearanceConfig;
import org.adorsys.adpharma.server.utils.SequenceGenerator;

@Entity
@Description("Article_description")
@ToStringField({ "articleName", "pic" })
@ListField({ "articleName", "pic", "manufacturer", "active", "qtyInStock", "sppu",
	"authorizedSale", "agency.name" })
public class Article implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	@Description("Article_articleName_description")
	@NotNull(message = "Article_articleName_NotNull_validation")
	private String articleName;

	@Column
	@Description("Article_pic_description")
	private String pic;

	@Column
	@Description("Article_manufacturer_description")
	private String manufacturer;

	@ManyToOne
	@Description("Article_section_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Section.class)
	@NotNull(message = "Article_section_NotNull_validation")
	private Section section;

	@Column
	@Description("Article_active_description")
	private Boolean active;

	@ManyToOne
	@Description("Article_family_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = ProductFamily.class)
	private ProductFamily family;

	@Column
	@Description("Article_qtyInStock_description")
	private BigDecimal qtyInStock;

	@Column
	@Description("Article_salableQty_description")
	private BigDecimal salableQty;

	@Column
	@Description("Article_qtyInStore_description")
	private BigDecimal qtyInStore;

	@Column
	@Description("Article_pppu_description")
	private BigDecimal pppu;

	@Column
	@Description("Article_sppu_description")
	private BigDecimal sppu;

	@Column
	@Description("Article_maxQtyPerPO_description")
	private Long maxQtyPerPO;

	@Column
	@Description("Article_maxDiscountRate_description")
	@NumberFormatType(NumberType.PERCENTAGE)
	private BigDecimal maxDiscountRate;

	@Column
	@Description("Article_totalStockPrice_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalStockPrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Article_lastStockEntry_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy")
	private Date lastStockEntry;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Article_lastOutOfStock_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy")
	private Date lastOutOfStock;

	@ManyToOne
	@Description("Article_defaultSalesMargin_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = SalesMargin.class)
	private SalesMargin defaultSalesMargin;

	@ManyToOne
	@Description("Article_packagingMode_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = PackagingMode.class)
	private PackagingMode packagingMode;

	@Column
	@Description("Article_authorizedSale_description")
	private Boolean authorizedSale;

	@Column
	@Description("Article_approvedOrder_description")
	private Boolean approvedOrder;

	@Column
	@Description("Article_maxStockQty_description")
	private Long maxStockQty;

	@ManyToOne
	@Description("Article_agency_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
	@NotNull(message = "Article_agency_NotNull_validation")
	private Agency agency;

	@ManyToOne
	@Description("Article_clearanceConfig_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = ClearanceConfig.class)
	private ClearanceConfig clearanceConfig;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Article_recordingDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date recordingDate;


	@PostPersist
	public void onPostPersist(){
		recordingDate = new Date();
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
			return id.equals(((Article) that).id);
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

	public String getArticleName()
	{
		return this.articleName;
	}

	public void setArticleName(final String articleName)
	{
		this.articleName = articleName;
	}

	public String getPic()
	{
		return this.pic;
	}

	public void setPic(final String pic)
	{
		this.pic = pic;
	}

	public String getManufacturer()
	{
		return this.manufacturer;
	}

	public void setManufacturer(final String manufacturer)
	{
		this.manufacturer = manufacturer;
	}

	public Section getSection()
	{
		return this.section;
	}

	public void setSection(final Section section)
	{
		this.section = section;
	}

	public Boolean getActive()
	{
		return this.active;
	}

	public void setActive(final Boolean active)
	{
		this.active = active;
	}

	public ProductFamily getFamily()
	{
		return this.family;
	}

	public void setFamily(final ProductFamily family)
	{
		this.family = family;
	}

	public BigDecimal getQtyInStock()
	{
		return this.qtyInStock;
	}

	public void setQtyInStock(final BigDecimal qtyInStock)
	{
		this.qtyInStock = qtyInStock;
	}

	public BigDecimal getSalableQty()
	{
		return this.salableQty;
	}

	public void setSalableQty(final BigDecimal salableQty)
	{
		this.salableQty = salableQty;
	}

	public BigDecimal getQtyInStore()
	{
		return this.qtyInStore;
	}

	public void setQtyInStore(final BigDecimal qtyInStore)
	{
		this.qtyInStore = qtyInStore;
	}

	public BigDecimal getPppu()
	{
		return this.pppu;
	}

	public void setPppu(final BigDecimal pppu)
	{
		this.pppu = pppu;
	}

	public BigDecimal getSppu()
	{
		return this.sppu;
	}

	public void setSppu(final BigDecimal sppu)
	{
		this.sppu = sppu;
	}

	public Long getMaxQtyPerPO()
	{
		return this.maxQtyPerPO;
	}

	public void setMaxQtyPerPO(final Long maxQtyPerPO)
	{
		this.maxQtyPerPO = maxQtyPerPO;
	}

	public BigDecimal getMaxDiscountRate()
	{
		return this.maxDiscountRate;
	}

	public void setMaxDiscountRate(final BigDecimal maxDiscountRate)
	{
		this.maxDiscountRate = maxDiscountRate;
	}

	public BigDecimal getTotalStockPrice()
	{
		return this.totalStockPrice;
	}

	public void setTotalStockPrice(final BigDecimal totalStockPrice)
	{
		this.totalStockPrice = totalStockPrice;
	}

	public Date getLastStockEntry()
	{
		return this.lastStockEntry;
	}

	public void setLastStockEntry(final Date lastStockEntry)
	{
		this.lastStockEntry = lastStockEntry;
	}

	public Date getLastOutOfStock()
	{
		return this.lastOutOfStock;
	}

	public void setLastOutOfStock(final Date lastOutOfStock)
	{
		this.lastOutOfStock = lastOutOfStock;
	}

	public SalesMargin getDefaultSalesMargin()
	{
		return this.defaultSalesMargin;
	}

	public void setDefaultSalesMargin(final SalesMargin defaultSalesMargin)
	{
		this.defaultSalesMargin = defaultSalesMargin;
	}

	public PackagingMode getPackagingMode()
	{
		return this.packagingMode;
	}

	public void setPackagingMode(final PackagingMode packagingMode)
	{
		this.packagingMode = packagingMode;
	}

	public Boolean getAuthorizedSale()
	{
		return this.authorizedSale;
	}

	public void setAuthorizedSale(final Boolean authorizedSale)
	{
		this.authorizedSale = authorizedSale;
	}

	public Boolean getApprovedOrder()
	{
		return this.approvedOrder;
	}

	public void setApprovedOrder(final Boolean approvedOrder)
	{
		this.approvedOrder = approvedOrder;
	}

	public Long getMaxStockQty()
	{
		return this.maxStockQty;
	}

	public void setMaxStockQty(final Long maxStockQty)
	{
		this.maxStockQty = maxStockQty;
	}

	public Agency getAgency()
	{
		return this.agency;
	}

	public void setAgency(final Agency agency)
	{
		this.agency = agency;
	}

	public ClearanceConfig getClearanceConfig()
	{
		return this.clearanceConfig;
	}

	public void setClearanceConfig(final ClearanceConfig clearanceConfig)
	{
		this.clearanceConfig = clearanceConfig;
	}

	public Date getRecordingDate()
	{
		return this.recordingDate;
	}

	public void setRecordingDate(final Date recordingDate)
	{
		this.recordingDate = recordingDate;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (articleName != null && !articleName.trim().isEmpty())
			result += "articleName: " + articleName;
		if (pic != null && !pic.trim().isEmpty())
			result += ", pic: " + pic;
		if (manufacturer != null && !manufacturer.trim().isEmpty())
			result += ", manufacturer: " + manufacturer;
		if (active != null)
			result += ", active: " + active;
		if (maxQtyPerPO != null)
			result += ", maxQtyPerPO: " + maxQtyPerPO;
		if (authorizedSale != null)
			result += ", authorizedSale: " + authorizedSale;
		if (approvedOrder != null)
			result += ", approvedOrder: " + approvedOrder;
		if (maxStockQty != null)
			result += ", maxStockQty: " + maxStockQty;
		return result;
	}
}