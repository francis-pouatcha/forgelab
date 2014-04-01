package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceCustomer;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceInsurer;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Insurrance_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrderInsurance implements Association<SalesOrder, Insurrance>, Cloneable
{

   private Long id;
   private int version;

   private SimpleObjectProperty<BigDecimal> coverageRate;
   private SimpleObjectProperty<Calendar> beginDate;
   private SimpleObjectProperty<Calendar> endDate;

   public SalesOrderInsurance()
   {
   }

   public SalesOrderInsurance(Insurrance entity)
   {
      PropertyReader.copy(entity, this);
   }

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

   public Calendar getEndDate()
   {
      return endDateProperty().get();
   }

   public final void setEndDate(Calendar endDate)
   {
      this.endDateProperty().set(endDate);
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

   //	@Override
   //	public boolean equals(Object obj) {
   //		if (this == obj)
   //			return true;
   //		if (obj == null)
   //			return false;
   //		if (getClass() != obj.getClass())
   //			return false;
   //		SalesOrderInsurance other = (SalesOrderInsurance) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "fullName", "fullName", "coverageRate");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      SalesOrderInsurance a = new SalesOrderInsurance();
      a.id = id;
      a.version = version;

      a.coverageRate = coverageRate;
      a.beginDate = beginDate;
      a.endDate = endDate;
      return a;
   }

}
