package org.adorsys.adpharma.client.jpa.currency;

import javafx.beans.property.SimpleStringProperty;
import java.math.BigDecimal;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.apache.commons.lang3.ObjectUtils;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Currency_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "name", "cfaEquivalent" })
@ListField({ "name", "cfaEquivalent" })
public class Currency implements Cloneable
{

   private Long id;
   private int version;

   @Description("Currency_name_description")
   private SimpleStringProperty name;
   @Description("Currency_cfaEquivalent_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> cfaEquivalent;

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

   @NotNull(message = "Currency_name_NotNull_validation")
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

   @NotNull(message = "Currency_cfaEquivalent_NotNull_validation")
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

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      Currency other = (Currency) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "name", "cfaEquivalent");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      Currency e = new Currency();
      e.id = id;
      e.version = version;

      e.name = name;
      e.cfaEquivalent = cfaEquivalent;
      return e;
   }
}