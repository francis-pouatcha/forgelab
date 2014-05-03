package org.adorsys.adpharma.client.jpa.productfamily;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("ProductFamily_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("name")
@ListField("name")
public class ProductFamily implements Cloneable
{

   private Long id;
   private int version;

   @Description("ProductFamily_name_description")
   private SimpleStringProperty name;
   @Description("ProductFamily_parentFamily_description")
   @Association(selectionMode = SelectionMode.TABLE, associationType = AssociationType.AGGREGATION, targetEntity = ProductFamily.class)
   private SimpleObjectProperty<ProductFamilyParentFamily> parentFamily;

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

   @NotNull(message = "ProductFamily_name_NotNull_validation")
   public String getName()
   {
      return nameProperty().get();
   }

   public final void setName(String name)
   {
      this.nameProperty().set(name);
   }

   public SimpleObjectProperty<ProductFamilyParentFamily> parentFamilyProperty()
   {
      if (parentFamily == null)
      {
         parentFamily = new SimpleObjectProperty<ProductFamilyParentFamily>(new ProductFamilyParentFamily());
      }
      return parentFamily;
   }

   public ProductFamilyParentFamily getParentFamily()
   {
      return parentFamilyProperty().get();
   }

   public final void setParentFamily(ProductFamilyParentFamily parentFamily)
   {
      if (parentFamily == null)
      {
         parentFamily = new ProductFamilyParentFamily();
      }
      PropertyReader.copy(parentFamily, getParentFamily());
      parentFamilyProperty().setValue(ObjectUtils.clone(getParentFamily()));
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
      ProductFamily other = (ProductFamily) obj;
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
      ProductFamily e = new ProductFamily();
      e.id = id;
      e.version = version;

      e.name = name;
      e.parentFamily = parentFamily;
      return e;
   }
}