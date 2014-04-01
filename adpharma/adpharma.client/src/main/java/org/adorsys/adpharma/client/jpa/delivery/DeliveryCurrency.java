package org.adorsys.adpharma.client.jpa.delivery;

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

import org.adorsys.adpharma.client.jpa.currency.Currency;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Currency_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryCurrency implements Association<Delivery, Currency>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty name;
   private SimpleObjectProperty<BigDecimal> cfaEquivalent;

   public DeliveryCurrency()
   {
   }

   public DeliveryCurrency(Currency entity)
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

   public SimpleObjectProperty<BigDecimal> cfaEquivalentProperty()
   {
      if (cfaEquivalent == null)
      {
         cfaEquivalent = new SimpleObjectProperty<BigDecimal>();
      }
      return cfaEquivalent;
   }

   public BigDecimal getCfaEquivalent()
   {
      return cfaEquivalentProperty().get();
   }

   public final void setCfaEquivalent(BigDecimal cfaEquivalent)
   {
      this.cfaEquivalentProperty().set(cfaEquivalent);
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
   //		DeliveryCurrency other = (DeliveryCurrency) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "name", "cfaEquivalent");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      DeliveryCurrency a = new DeliveryCurrency();
      a.id = id;
      a.version = version;

      a.name = name;
      a.cfaEquivalent = cfaEquivalent;
      return a;
   }

}
