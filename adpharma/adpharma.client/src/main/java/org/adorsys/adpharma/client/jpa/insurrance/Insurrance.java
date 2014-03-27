package org.adorsys.adpharma.client.jpa.insurrance;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javaext.format.DateFormatPattern;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Insurrance_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "beginDate", "endDate", "customer.fullName", "insurer.fullName",
      "coverageRate" })
@ToStringField({ "customer.fullName", "insurer.fullName", "coverageRate" })
public class Insurrance
{

   private Long id;
   private int version;

   @Description("Insurrance_coverageRate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private SimpleObjectProperty<BigDecimal> coverageRate;
   @Description("Insurrance_beginDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private SimpleObjectProperty<Calendar> beginDate;
   @Description("Insurrance_endDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private SimpleObjectProperty<Calendar> endDate;
   @Description("Insurrance_customer_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   private SimpleObjectProperty<InsurranceCustomer> customer;
   @Description("Insurrance_insurer_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   private SimpleObjectProperty<InsurranceInsurer> insurer;

   public Long getId()
   {
      return id;
   }

   public final void setId(Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return version;
   }

   public final void setVersion(int version)
   {
      this.version = version;
   }

   public SimpleObjectProperty<BigDecimal> coverageRateProperty()
   {
      if (coverageRate == null)
      {
         coverageRate = new SimpleObjectProperty<BigDecimal>();
      }
      return coverageRate;
   }

   public BigDecimal getCoverageRate()
   {
      return coverageRateProperty().get();
   }

   public final void setCoverageRate(BigDecimal coverageRate)
   {
      this.coverageRateProperty().set(coverageRate);
   }

   public SimpleObjectProperty<Calendar> beginDateProperty()
   {
      if (beginDate == null)
      {
         beginDate = new SimpleObjectProperty<Calendar>();
      }
      return beginDate;
   }

   @NotNull(message = "Insurrance_beginDate_NotNull_validation")
   public Calendar getBeginDate()
   {
      return beginDateProperty().get();
   }

   public final void setBeginDate(Calendar beginDate)
   {
      this.beginDateProperty().set(beginDate);
   }

   public SimpleObjectProperty<Calendar> endDateProperty()
   {
      if (endDate == null)
      {
         endDate = new SimpleObjectProperty<Calendar>();
      }
      return endDate;
   }

   @NotNull(message = "Insurrance_endDate_NotNull_validation")
   public Calendar getEndDate()
   {
      return endDateProperty().get();
   }

   public final void setEndDate(Calendar endDate)
   {
      this.endDateProperty().set(endDate);
   }

   public SimpleObjectProperty<InsurranceCustomer> customerProperty()
   {
      if (customer == null)
      {
         customer = new SimpleObjectProperty<InsurranceCustomer>(new InsurranceCustomer());
      }
      return customer;
   }

   @NotNull(message = "Insurrance_customer_NotNull_validation")
   public InsurranceCustomer getCustomer()
   {
      return customerProperty().get();
   }

   public final void setCustomer(InsurranceCustomer customer)
   {
      if (customer == null)
      {
         customer = new InsurranceCustomer();
      }
      PropertyReader.copy(customer, getCustomer());
   }

   public SimpleObjectProperty<InsurranceInsurer> insurerProperty()
   {
      if (insurer == null)
      {
         insurer = new SimpleObjectProperty<InsurranceInsurer>(new InsurranceInsurer());
      }
      return insurer;
   }

   @NotNull(message = "Insurrance_insurer_NotNull_validation")
   public InsurranceInsurer getInsurer()
   {
      return insurerProperty().get();
   }

   public final void setInsurer(InsurranceInsurer insurer)
   {
      if (insurer == null)
      {
         insurer = new InsurranceInsurer();
      }
      PropertyReader.copy(insurer, getInsurer());
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result
            + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Insurrance other = (Insurrance) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "insurer");
   }
}