package org.adorsys.adpharma.client.jpa.vat;

import javafx.beans.property.SimpleStringProperty;
import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("VAT_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("name")
@ListField({ "name", "rate", "active" })
public class VAT
{

   private Long id;
   private int version;

   @Description("VAT_name_description")
   private SimpleStringProperty name;
   @Description("VAT_active_description")
   private SimpleBooleanProperty active;
   @Description("VAT_rate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private SimpleObjectProperty<BigDecimal> rate;

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

   public SimpleBooleanProperty activeProperty()
   {
      if (active == null)
      {
         active = new SimpleBooleanProperty();
      }
      return active;
   }

   public Boolean getActive()
   {
      return activeProperty().get();
   }

   public final void setActive(Boolean active)
   {
      if (active == null)
         active = Boolean.FALSE;
      this.activeProperty().set(active);
   }

   public SimpleObjectProperty<BigDecimal> rateProperty()
   {
      if (rate == null)
      {
         rate = new SimpleObjectProperty<BigDecimal>();
      }
      return rate;
   }

   public BigDecimal getRate()
   {
      return rateProperty().get();
   }

   public final void setRate(BigDecimal rate)
   {
      this.rateProperty().set(rate);
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
      VAT other = (VAT) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "name","rate");
   }
}