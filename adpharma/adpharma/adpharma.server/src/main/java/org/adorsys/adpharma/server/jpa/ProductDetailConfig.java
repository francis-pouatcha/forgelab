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
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;
import javax.persistence.OneToOne;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;

@Entity
@Description("ProductDetailConfig_description")
@ToStringField({ "source.articleName", "target.articleName" })
@ListField({ "source.articleName", "target.articleName", "targetQuantity", "salesPrice",
      "active" })
public class ProductDetailConfig implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("ProductDetailConfig_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date recordingDate;

   @OneToOne
   @Description("ProductDetailConfig_source_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   @NotNull(message = "ProductDetailConfig_source_NotNull_validation")
   private Article source;

   @OneToOne
   @Description("ProductDetailConfig_target_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Article.class)
   @NotNull(message = "ProductDetailConfig_target_NotNull_validation")
   private Article target;

   @Column
   @Description("ProductDetailConfig_targetQuantity_description")
   @NotNull(message = "ProductDetailConfig_targetQuantity_NotNull_validation")
   private BigDecimal targetQuantity;

   @Column
   @Description("ProductDetailConfig_salesPrice_description")
   @NotNull(message = "ProductDetailConfig_salesPrice_NotNull_validation")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal salesPrice;

   @Column
   @Description("ProductDetailConfig_active_description")
   private Boolean active;

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
         return id.equals(((ProductDetailConfig) that).id);
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

   public Date getRecordingDate()
   {
      return this.recordingDate;
   }

   public void setRecordingDate(final Date recordingDate)
   {
      this.recordingDate = recordingDate;
   }

   public Article getSource()
   {
      return this.source;
   }

   public void setSource(final Article source)
   {
      this.source = source;
   }

   public Article getTarget()
   {
      return this.target;
   }

   public void setTarget(final Article target)
   {
      this.target = target;
   }

   public BigDecimal getTargetQuantity()
   {
      return this.targetQuantity;
   }

   public void setTargetQuantity(final BigDecimal targetQuantity)
   {
      this.targetQuantity = targetQuantity;
   }

   public BigDecimal getSalesPrice()
   {
      return this.salesPrice;
   }

   public void setSalesPrice(final BigDecimal salesPrice)
   {
      this.salesPrice = salesPrice;
   }

   public Boolean getActive()
   {
      return this.active;
   }

   public void setActive(final Boolean active)
   {
      this.active = active;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (active != null)
         result += "active: " + active;
      return result;
   }
}