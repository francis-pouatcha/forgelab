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
import javax.persistence.OneToOne;
import org.adorsys.adph.server.jpa.Article;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.ProductTransformationConfig.description")
@Audited
@XmlRootElement
public class ProductTransformationConfig implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProductTransformationConfig.ptNumber.description")
   private String ptNumber;

   @OneToOne
   @NotNull
   @Description("org.adorsys.adph.server.jpa.ProductTransformationConfig.source.description")
   private Article source;

   @OneToOne
   @NotNull
   @Description("org.adorsys.adph.server.jpa.ProductTransformationConfig.target.description")
   private Article target;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProductTransformationConfig.targetQuantity.description")
   @NotNull
   private BigDecimal targetQuantity;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProductTransformationConfig.salesPrice.description")
   @NotNull
   private BigDecimal salesPrice;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProductTransformationConfig.active.description")
   private boolean active;

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
         return id.equals(((ProductTransformationConfig) that).id);
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

   public String getPtNumber()
   {
      return this.ptNumber;
   }

   public void setPtNumber(final String ptNumber)
   {
      this.ptNumber = ptNumber;
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

   public boolean getActive()
   {
      return this.active;
   }

   public void setActive(final boolean active)
   {
      this.active = active;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (ptNumber != null && !ptNumber.trim().isEmpty())
         result += "ptNumber: " + ptNumber;
      result += ", active: " + active;
      return result;
   }
}