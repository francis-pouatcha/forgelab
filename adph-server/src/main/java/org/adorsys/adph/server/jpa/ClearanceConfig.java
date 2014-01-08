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
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.DecimalMax;
import org.adorsys.adph.server.jpa.DocumentProcessingState;
import javax.persistence.Enumerated;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.ClearanceConfig.description")
@Audited
@XmlRootElement
public class ClearanceConfig implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.ClearanceConfig.startDate.description")
   @NotNull(message = "Veuillez entrer la date de debut du solde")
   private Date startDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.ClearanceConfig.endDate.description")
   @NotNull
   private Date endDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClearanceConfig.discountRate.description")
   @NotNull(message = "Veuillez entrer le taux de solde de ce produit")
   @DecimalMin(message = "Le taux de solde ne doit pas etre inferieur a 1", value = "1.0")
   @DecimalMax(message = "Le taux de solde ne doit pas etre superieur a 100", value = "100.0")
   private BigDecimal discountRate;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClearanceConfig.clearanceState.description")
   @Enumerated
   private DocumentProcessingState clearanceState;

   @Column
   @Description("org.adorsys.adph.server.jpa.ClearanceConfig.active.description")
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
         return id.equals(((ClearanceConfig) that).id);
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

   public Date getStartDate()
   {
      return this.startDate;
   }

   public void setStartDate(final Date startDate)
   {
      this.startDate = startDate;
   }

   public Date getEndDate()
   {
      return this.endDate;
   }

   public void setEndDate(final Date endDate)
   {
      this.endDate = endDate;
   }

   public BigDecimal getDiscountRate()
   {
      return this.discountRate;
   }

   public void setDiscountRate(final BigDecimal discountRate)
   {
      this.discountRate = discountRate;
   }

   public DocumentProcessingState getClearanceState()
   {
      return this.clearanceState;
   }

   public void setClearanceState(final DocumentProcessingState clearanceState)
   {
      this.clearanceState = clearanceState;
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
      result += "active: " + active;
      return result;
   }
}