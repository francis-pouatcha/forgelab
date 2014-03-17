package org.adorsys.adpharma.client.jpa.supplier;

import javafx.beans.property.SimpleStringProperty;
import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("SalesMargin_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierDefaultSalesMargin implements Association<Supplier, SalesMargin>
{

   private Long id;
   private int version;

   private SimpleStringProperty code;
   private SimpleBooleanProperty active;
   private SimpleObjectProperty<BigDecimal> rate;

   public SupplierDefaultSalesMargin()
   {
   }

   public SupplierDefaultSalesMargin(SalesMargin entity)
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

   public SimpleStringProperty codeProperty()
   {
      if (code == null)
      {
         code = new SimpleStringProperty();
      }
      return code;
   }

   public String getCode()
   {
      return codeProperty().get();
   }

   public final void setCode(String code)
   {
      this.codeProperty().set(code);
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
      SupplierDefaultSalesMargin other = (SupplierDefaultSalesMargin) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "code");
   }
}