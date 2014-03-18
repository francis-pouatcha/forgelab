package org.adorsys.adpharma.client.jpa.supplier;

import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Supplier_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("name")
@ListField({ "name", "fax", "email" })
public class Supplier
{

   private Long id;
   private int version;

   @Description("Supplier_name_description")
   private SimpleStringProperty name;
   @Description("Supplier_mobile_description")
   private SimpleStringProperty mobile;
   @Description("Supplier_fax_description")
   private SimpleStringProperty fax;
   @Description("Supplier_email_description")
   private SimpleStringProperty email;
   @Description("Supplier_zipCode_description")
   private SimpleStringProperty zipCode;
   @Description("Supplier_country_description")
   private SimpleStringProperty country;
   @Description("Supplier_internetSite_description")
   private SimpleStringProperty internetSite;
   @Description("Supplier_responsable_description")
   private SimpleStringProperty responsable;
   @Description("Supplier_revenue_description")
   private SimpleStringProperty revenue;
   @Description("Supplier_taxIdNumber_description")
   private SimpleStringProperty taxIdNumber;

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

   @NotNull(message = "Supplier_name_NotNull_validation")
   public String getName()
   {
      return nameProperty().get();
   }

   public final void setName(String name)
   {
      this.nameProperty().set(name);
   }

   public SimpleStringProperty mobileProperty()
   {
      if (mobile == null)
      {
         mobile = new SimpleStringProperty();
      }
      return mobile;
   }

   public String getMobile()
   {
      return mobileProperty().get();
   }

   public final void setMobile(String mobile)
   {
      this.mobileProperty().set(mobile);
   }

   public SimpleStringProperty faxProperty()
   {
      if (fax == null)
      {
         fax = new SimpleStringProperty();
      }
      return fax;
   }

   public String getFax()
   {
      return faxProperty().get();
   }

   public final void setFax(String fax)
   {
      this.faxProperty().set(fax);
   }

   public SimpleStringProperty emailProperty()
   {
      if (email == null)
      {
         email = new SimpleStringProperty();
      }
      return email;
   }

   public String getEmail()
   {
      return emailProperty().get();
   }

   public final void setEmail(String email)
   {
      this.emailProperty().set(email);
   }

   public SimpleStringProperty zipCodeProperty()
   {
      if (zipCode == null)
      {
         zipCode = new SimpleStringProperty();
      }
      return zipCode;
   }

   public String getZipCode()
   {
      return zipCodeProperty().get();
   }

   public final void setZipCode(String zipCode)
   {
      this.zipCodeProperty().set(zipCode);
   }

   public SimpleStringProperty countryProperty()
   {
      if (country == null)
      {
         country = new SimpleStringProperty();
      }
      return country;
   }

   public String getCountry()
   {
      return countryProperty().get();
   }

   public final void setCountry(String country)
   {
      this.countryProperty().set(country);
   }

   public SimpleStringProperty internetSiteProperty()
   {
      if (internetSite == null)
      {
         internetSite = new SimpleStringProperty();
      }
      return internetSite;
   }

   public String getInternetSite()
   {
      return internetSiteProperty().get();
   }

   public final void setInternetSite(String internetSite)
   {
      this.internetSiteProperty().set(internetSite);
   }

   public SimpleStringProperty responsableProperty()
   {
      if (responsable == null)
      {
         responsable = new SimpleStringProperty();
      }
      return responsable;
   }

   public String getResponsable()
   {
      return responsableProperty().get();
   }

   public final void setResponsable(String responsable)
   {
      this.responsableProperty().set(responsable);
   }

   public SimpleStringProperty revenueProperty()
   {
      if (revenue == null)
      {
         revenue = new SimpleStringProperty();
      }
      return revenue;
   }

   public String getRevenue()
   {
      return revenueProperty().get();
   }

   public final void setRevenue(String revenue)
   {
      this.revenueProperty().set(revenue);
   }

   public SimpleStringProperty taxIdNumberProperty()
   {
      if (taxIdNumber == null)
      {
         taxIdNumber = new SimpleStringProperty();
      }
      return taxIdNumber;
   }

   public String getTaxIdNumber()
   {
      return taxIdNumberProperty().get();
   }

   public final void setTaxIdNumber(String taxIdNumber)
   {
      this.taxIdNumberProperty().set(taxIdNumber);
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
      Supplier other = (Supplier) obj;
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