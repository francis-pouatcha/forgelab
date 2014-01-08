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
import org.adorsys.adph.server.jpa.Article;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import org.adorsys.adph.server.jpa.PharmaUser;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.description")
@Audited
@XmlRootElement
public class ProcurementOrderItem implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.indexLine.description")
   private String indexLine;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.pic.description")
   private String pic;

   @ManyToOne
   @NotNull
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.article.description")
   private Article article;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.recCreated.description")
   private Date recCreated;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.quantity.description")
   private BigDecimal quantity;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.availableQty.description")
   private BigDecimal availableQty;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.creatingUser.description")
   private PharmaUser creatingUser;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.designation.description")
   private String designation;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.valid.description")
   private boolean valid;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.minPurchasePriceSug.description")
   private BigDecimal minPurchasePriceSug;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.minPurchasePriceSupl.description")
   private BigDecimal minPurchasePriceSupl;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.minSalesPrice.description")
   private BigDecimal minSalesPrice;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProcurementOrderItem.totalPurchasePrice.description")
   private BigDecimal totalPurchasePrice;

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
         return id.equals(((ProcurementOrderItem) that).id);
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

   public String getIndexLine()
   {
      return this.indexLine;
   }

   public void setIndexLine(final String indexLine)
   {
      this.indexLine = indexLine;
   }

   public String getPic()
   {
      return this.pic;
   }

   public void setPic(final String pic)
   {
      this.pic = pic;
   }

   public Article getArticle()
   {
      return this.article;
   }

   public void setArticle(final Article article)
   {
      this.article = article;
   }

   public Date getRecCreated()
   {
      return this.recCreated;
   }

   public void setRecCreated(final Date recCreated)
   {
      this.recCreated = recCreated;
   }

   public BigDecimal getQuantity()
   {
      return this.quantity;
   }

   public void setQuantity(final BigDecimal quantity)
   {
      this.quantity = quantity;
   }

   public BigDecimal getAvailableQty()
   {
      return this.availableQty;
   }

   public void setAvailableQty(final BigDecimal availableQty)
   {
      this.availableQty = availableQty;
   }

   public PharmaUser getCreatingUser()
   {
      return this.creatingUser;
   }

   public void setCreatingUser(final PharmaUser creatingUser)
   {
      this.creatingUser = creatingUser;
   }

   public String getDesignation()
   {
      return this.designation;
   }

   public void setDesignation(final String designation)
   {
      this.designation = designation;
   }

   public boolean getValid()
   {
      return this.valid;
   }

   public void setValid(final boolean valid)
   {
      this.valid = valid;
   }

   public BigDecimal getMinPurchasePriceSug()
   {
      return this.minPurchasePriceSug;
   }

   public void setMinPurchasePriceSug(final BigDecimal minPurchasePriceSug)
   {
      this.minPurchasePriceSug = minPurchasePriceSug;
   }

   public BigDecimal getMinPurchasePriceSupl()
   {
      return this.minPurchasePriceSupl;
   }

   public void setMinPurchasePriceSupl(final BigDecimal minPurchasePriceSupl)
   {
      this.minPurchasePriceSupl = minPurchasePriceSupl;
   }

   public BigDecimal getMinSalesPrice()
   {
      return this.minSalesPrice;
   }

   public void setMinSalesPrice(final BigDecimal minSalesPrice)
   {
      this.minSalesPrice = minSalesPrice;
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
      if (indexLine != null && !indexLine.trim().isEmpty())
         result += "indexLine: " + indexLine;
      if (pic != null && !pic.trim().isEmpty())
         result += ", pic: " + pic;
      if (designation != null && !designation.trim().isEmpty())
         result += ", designation: " + designation;
      result += ", valid: " + valid;
      return result;
   }
}