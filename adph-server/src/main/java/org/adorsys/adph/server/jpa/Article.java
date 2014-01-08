package org.adorsys.adph.server.jpa;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.adorsys.javaext.description.Description;
import org.adorsys.adph.server.jpa.Section;
import javax.persistence.ManyToOne;
import org.adorsys.adph.server.jpa.ProductFamily;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.adph.server.jpa.VAT;
import org.adorsys.adph.server.jpa.SalesMargin;
import org.adorsys.adph.server.jpa.PackagingMode;
import org.adorsys.adph.server.jpa.Agency;
import org.adorsys.adph.server.jpa.ClearanceConfig;
import javax.validation.constraints.Size;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.Article.description")
@Audited
@XmlRootElement
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
   @Description("org.adorsys.adph.server.jpa.Article.articleNumber.description")
   private String articleNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.articleName.description")
   private String articleName;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.manufacturer.description")
   private String manufacturer;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Article.section.description")
   private Section section;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.active.description")
   private boolean active;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Article.family.description")
   private ProductFamily family;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.qtyInStock.description")
   private long qtyInStock;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.pppu.description")
   private BigDecimal pppu;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.sppu.description")
   private BigDecimal sppu;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.maxQtyPerPO.description")
   private long maxQtyPerPO;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.maxDiscountRate.description")
   private BigDecimal maxDiscountRate;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.totalStockPrice.description")
   private BigDecimal totalStockPrice;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.Article.lastStockEntry.description")
   private Date lastStockEntry;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.Article.lastOutOfStock.description")
   private Date lastOutOfStock;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.dosage.description")
   private String dosage;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Article.vat.description")
   private VAT vat;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Article.salesMargin.description")
   private SalesMargin salesMargin;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.pic.description")
   private String pic;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Article.packagingMode.description")
   private PackagingMode packagingMode;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.authorizedSale.description")
   private boolean authorizedSale;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.approvedOrder.description")
   private boolean approvedOrder;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.maxStockQty.description")
   private long maxStockQty;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Article.agency.description")
   private Agency agency;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Article.clearanceConfig.description")
   private ClearanceConfig clearanceConfig;

   @Column
   @Size(max = 256)
   @Description("org.adorsys.adph.server.jpa.Article.activeIngredients.description")
   private String activeIngredients;

   @Column
   @Description("org.adorsys.adph.server.jpa.Article.mixedDrug.description")
   private boolean mixedDrug;

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

   public String getArticleNumber()
   {
      return this.articleNumber;
   }

   public void setArticleNumber(final String articleNumber)
   {
      this.articleNumber = articleNumber;
   }

   public String getArticleName()
   {
      return this.articleName;
   }

   public void setArticleName(final String articleName)
   {
      this.articleName = articleName;
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

   public boolean getActive()
   {
      return this.active;
   }

   public void setActive(final boolean active)
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

   public long getQtyInStock()
   {
      return this.qtyInStock;
   }

   public void setQtyInStock(final long qtyInStock)
   {
      this.qtyInStock = qtyInStock;
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

   public long getMaxQtyPerPO()
   {
      return this.maxQtyPerPO;
   }

   public void setMaxQtyPerPO(final long maxQtyPerPO)
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

   public String getDosage()
   {
      return this.dosage;
   }

   public void setDosage(final String dosage)
   {
      this.dosage = dosage;
   }

   public VAT getVat()
   {
      return this.vat;
   }

   public void setVat(final VAT vat)
   {
      this.vat = vat;
   }

   public SalesMargin getSalesMargin()
   {
      return this.salesMargin;
   }

   public void setSalesMargin(final SalesMargin salesMargin)
   {
      this.salesMargin = salesMargin;
   }

   public String getPic()
   {
      return this.pic;
   }

   public void setPic(final String pic)
   {
      this.pic = pic;
   }

   public PackagingMode getPackagingMode()
   {
      return this.packagingMode;
   }

   public void setPackagingMode(final PackagingMode packagingMode)
   {
      this.packagingMode = packagingMode;
   }

   public boolean getAuthorizedSale()
   {
      return this.authorizedSale;
   }

   public void setAuthorizedSale(final boolean authorizedSale)
   {
      this.authorizedSale = authorizedSale;
   }

   public boolean getApprovedOrder()
   {
      return this.approvedOrder;
   }

   public void setApprovedOrder(final boolean approvedOrder)
   {
      this.approvedOrder = approvedOrder;
   }

   public long getMaxStockQty()
   {
      return this.maxStockQty;
   }

   public void setMaxStockQty(final long maxStockQty)
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

   public String getActiveIngredients()
   {
      return this.activeIngredients;
   }

   public void setActiveIngredients(final String activeIngredients)
   {
      this.activeIngredients = activeIngredients;
   }

   public boolean getMixedDrug()
   {
      return this.mixedDrug;
   }

   public void setMixedDrug(final boolean mixedDrug)
   {
      this.mixedDrug = mixedDrug;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (articleNumber != null && !articleNumber.trim().isEmpty())
         result += "articleNumber: " + articleNumber;
      if (articleName != null && !articleName.trim().isEmpty())
         result += ", articleName: " + articleName;
      if (manufacturer != null && !manufacturer.trim().isEmpty())
         result += ", manufacturer: " + manufacturer;
      result += ", active: " + active;
      result += ", qtyInStock: " + qtyInStock;
      result += ", maxQtyPerPO: " + maxQtyPerPO;
      if (dosage != null && !dosage.trim().isEmpty())
         result += ", dosage: " + dosage;
      if (pic != null && !pic.trim().isEmpty())
         result += ", pic: " + pic;
      result += ", authorizedSale: " + authorizedSale;
      result += ", approvedOrder: " + approvedOrder;
      result += ", maxStockQty: " + maxStockQty;
      if (activeIngredients != null && !activeIngredients.trim().isEmpty())
         result += ", activeIngredients: " + activeIngredients;
      result += ", mixedDrug: " + mixedDrug;
      return result;
   }
}