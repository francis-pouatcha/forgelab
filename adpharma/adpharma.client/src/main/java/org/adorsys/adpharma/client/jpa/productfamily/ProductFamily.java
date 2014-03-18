package org.adorsys.adpharma.client.jpa.productfamily;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("ProductFamily_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("name")
@ListField("name")
public class ProductFamily
{

   private Long id;
   private int version;

   @Description("ProductFamily_name_description")
   private SimpleStringProperty name;
   @Description("ProductFamily_description_description")
   private SimpleStringProperty description;
   @Description("ProductFamily_parentFamilly_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = ProductFamily.class)
   private SimpleObjectProperty<ProductFamilyParentFamilly> parentFamilly;

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

   public SimpleStringProperty descriptionProperty()
   {
      if (description == null)
      {
         description = new SimpleStringProperty();
      }
      return description;
   }

   @Size(max = 256, message = "ProductFamily_description_Size_validation")
   public String getDescription()
   {
      return descriptionProperty().get();
   }

   public final void setDescription(String description)
   {
      this.descriptionProperty().set(description);
   }

   public SimpleObjectProperty<ProductFamilyParentFamilly> parentFamillyProperty()
   {
      if (parentFamilly == null)
      {
         parentFamilly = new SimpleObjectProperty<ProductFamilyParentFamilly>(new ProductFamilyParentFamilly());
      }
      return parentFamilly;
   }

   public ProductFamilyParentFamilly getParentFamilly()
   {
      return parentFamillyProperty().get();
   }

   public final void setParentFamilly(ProductFamilyParentFamilly parentFamilly)
   {
      if (parentFamilly == null)
      {
         parentFamilly = new ProductFamilyParentFamilly();
      }
      PropertyReader.copy(parentFamilly, getParentFamilly());
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
}