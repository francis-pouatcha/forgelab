package org.adorsys.adpharma.client.jpa.customer;

import javafx.beans.property.SimpleStringProperty;
import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("CustomerCategory_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerCustomerCategory implements Association<Customer, CustomerCategory>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty name;
   private SimpleObjectProperty<BigDecimal> discountRate;

   public CustomerCustomerCategory()
   {
   }

   public CustomerCustomerCategory(CustomerCategory entity)
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

   public SimpleStringProperty nameProperty()
   {
      if (name == null)
      {
         name = new SimpleStringProperty();
      }
      return name;
   }

   public String getName()
   {
      return nameProperty().get();
   }

   public final void setName(String name)
   {
      this.nameProperty().set(name);
   }

   public SimpleObjectProperty<BigDecimal> discountRateProperty()
   {
      if (discountRate == null)
      {
         discountRate = new SimpleObjectProperty<BigDecimal>();
      }
      return discountRate;
   }

   public BigDecimal getDiscountRate()
   {
      return discountRateProperty().get();
   }

   public final void setDiscountRate(BigDecimal discountRate)
   {
      this.discountRateProperty().set(discountRate);
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
   //		CustomerCustomerCategory other = (CustomerCustomerCategory) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "name","discountRate");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      CustomerCustomerCategory a = new CustomerCustomerCategory();
      a.id = id;
      a.version = version;

      a.name = name;
      a.discountRate = discountRate;
      return a;
   }

}
