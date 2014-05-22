package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import java.lang.Override;

import org.adorsys.javaext.description.Description;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.format.DateFormatPattern;

import javax.validation.constraints.NotNull;

import org.adorsys.adpharma.server.jpa.Customer;

import javax.persistence.ManyToOne;

import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.ToStringField;

import java.math.BigDecimal;

import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.apache.commons.lang3.time.DateUtils;

@Entity
@Description("Insurrance_description")
@ListField({ "beginDate", "endDate", "customer.fullName", "insurer.fullName",
      "coverageRate" })
@ToStringField({ "customer.fullName", "insurer.fullName", "coverageRate" })
public class Insurrance implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("Insurrance_beginDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   @NotNull(message = "Insurrance_beginDate_NotNull_validation")
   private Date beginDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("Insurrance_endDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   @NotNull(message = "Insurrance_endDate_NotNull_validation")
   private Date endDate;

   @ManyToOne
   @Description("Insurrance_customer_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   @NotNull(message = "Insurrance_customer_NotNull_validation")
   private Customer customer;

   @ManyToOne
   @Description("Insurrance_insurer_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   @NotNull(message = "Insurrance_insurer_NotNull_validation")
   private Customer insurer;

   @Column
   @Description("Insurrance_coverageRate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private BigDecimal coverageRate;
   
   
   @PrePersist
   public void prePersist(){
	   Date date = new Date();
	   beginDate = DateUtils.addYears(date, -2);
	   endDate =DateUtils.addYears(date, 5);
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
         return id.equals(((Insurrance) that).id);
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

   public Date getBeginDate()
   {
      return this.beginDate;
   }

   public void setBeginDate(final Date beginDate)
   {
      this.beginDate = beginDate;
   }

   public Date getEndDate()
   {
      return this.endDate;
   }

   public void setEndDate(final Date endDate)
   {
      this.endDate = endDate;
   }

   public Customer getCustomer()
   {
      return this.customer;
   }

   public void setCustomer(final Customer customer)
   {
      this.customer = customer;
   }

   public Customer getInsurer()
   {
      return this.insurer;
   }

   public void setInsurer(final Customer insurer)
   {
      this.insurer = insurer;
   }

   public BigDecimal getCoverageRate()
   {
      return this.coverageRate;
   }

   public void setCoverageRate(final BigDecimal coverageRate)
   {
      this.coverageRate = coverageRate;
   }
}