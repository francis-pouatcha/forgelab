package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.EnumType;
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

import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.format.DateFormatPattern;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.DecimalMax;

import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;

import javax.persistence.Enumerated;

@Entity
@Description("ClearanceConfig_description")
@ToStringField({ "startDate", "endDate", "discountRate", "clearanceState" })
@ListField({ "startDate", "endDate", "discountRate", "clearanceState", "active" })
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
   @Description("ClearanceConfig_startDate_description")
   @NotNull(message = "ClearanceConfig_startDate_NotNull_validation")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private Date startDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("ClearanceConfig_endDate_description")
   @NotNull(message = "ClearanceConfig_endDate_NotNull_validation")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private Date endDate;

   @Column
   @Description("ClearanceConfig_discountRate_description")
   @NotNull(message = "ClearanceConfig_discountRate_NotNull_validation")
   @DecimalMin(value = "1.0", message = "ClearanceConfig_discountRate_DecimalMin_validation")
   @DecimalMax(value = "100.0", message = "ClearanceConfig_discountRate_DecimalMax_validation")
   @NumberFormatType(NumberType.PERCENTAGE)
   private BigDecimal discountRate;

   @Column
   @Description("ClearanceConfig_clearanceState_description")
   @Enumerated(EnumType.STRING)
   private DocumentProcessingState clearanceState;

   @Column
   @Description("ClearanceConfig_active_description")
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