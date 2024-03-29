package org.adorsys.adpharma.client.jpa.salesorder;

import java.math.BigDecimal;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("VAT_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrderVat implements Association<SalesOrder, VAT>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty name;
   private SimpleBooleanProperty active;
   private SimpleObjectProperty<BigDecimal> rate;

   public SalesOrderVat()
   {
   }

   public SalesOrderVat(VAT entity)
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

   //	@Override
   //	public boolean equals(Object obj) {
   //		if (this == obj)
   //			return true;
   //		if (obj == null)
   //			return false;
   //		if (getClass() != obj.getClass())
   //			return false;
   //		SalesOrderVat other = (SalesOrderVat) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "name");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      SalesOrderVat a = new SalesOrderVat();
      a.id = id;
      a.version = version;

      a.name = name;
      a.active = active;
      a.rate = rate;
      return a;
   }

}
