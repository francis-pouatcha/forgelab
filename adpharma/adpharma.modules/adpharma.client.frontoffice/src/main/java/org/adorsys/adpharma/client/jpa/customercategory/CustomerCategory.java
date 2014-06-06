package org.adorsys.adpharma.client.jpa.customercategory;

import java.math.BigDecimal;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("CustomerCategory_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("name")
@ListField({ "name", "discountRate" })
public class CustomerCategory implements Cloneable
{

   private Long id;
   private int version;

   @Description("CustomerCategory_name_description")
   private SimpleStringProperty name;
   @Description("CustomerCategory_description_description")
   private SimpleStringProperty description;
   @Description("CustomerCategory_discountRate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private SimpleObjectProperty<BigDecimal> discountRate;

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

   @NotNull(message = "CustomerCategory_name_NotNull_validation")
   public String getName()
   {
      return nameProperty().get();
   }

   public final void setName(String name)
   {
      this.nameProperty().set(name);
   }

   public SimpleStringProperty descriptionProperty()
   {
      if (description == null)
      {
         description = new SimpleStringProperty();
      }
      return description;
   }

   @Size(max = 256, message = "CustomerCategory_description_Size_validation")
   public String getDescription()
   {
      return descriptionProperty().get();
   }

   public final void setDescription(String description)
   {
      this.descriptionProperty().set(description);
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

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      CustomerCategory other = (CustomerCategory) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "name");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      CustomerCategory e = new CustomerCategory();
      e.id = id;
      e.version = version;

      e.name = name;
      e.description = description;
      e.discountRate = discountRate;
      return e;
   }
}