package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import javax.validation.constraints.NotNull;

@Entity
@Description("Supplier_description")
@ToStringField("name")
@ListField({ "name", "fax", "email" })
public class Supplier implements Serializable,Comparable<Supplier>
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("Supplier_name_description")
   @NotNull(message = "Supplier_name_NotNull_validation")
   private String name;

   @Column
   @Description("Supplier_mobile_description")
   private String mobile;

   @Column
   @Description("Supplier_fax_description")
   private String fax;

   @Column
   @Description("Supplier_email_description")
   private String email;

   @Column
   @Description("Supplier_zipCode_description")
   private String zipCode;

   @Column
   @Description("Supplier_country_description")
   private String country;

   @Column
   @Description("Supplier_internetSite_description")
   private String internetSite;

   @Column
   @Description("Supplier_responsable_description")
   private String responsable;

   @Column
   @Description("Supplier_revenue_description")
   private String revenue;

   @Column
   @Description("Supplier_taxIdNumber_description")
   private String taxIdNumber;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Supplier) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getMobile()
   {
      return this.mobile;
   }

   public void setMobile(final String mobile)
   {
      this.mobile = mobile;
   }

   public String getFax()
   {
      return this.fax;
   }

   public void setFax(final String fax)
   {
      this.fax = fax;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String getZipCode()
   {
      return this.zipCode;
   }

   public void setZipCode(final String zipCode)
   {
      this.zipCode = zipCode;
   }

   public String getCountry()
   {
      return this.country;
   }

   public void setCountry(final String country)
   {
      this.country = country;
   }

   public String getInternetSite()
   {
      return this.internetSite;
   }

   public void setInternetSite(final String internetSite)
   {
      this.internetSite = internetSite;
   }

   public String getResponsable()
   {
      return this.responsable;
   }

   public void setResponsable(final String responsable)
   {
      this.responsable = responsable;
   }

   public String getRevenue()
   {
      return this.revenue;
   }

   public void setRevenue(final String revenue)
   {
      this.revenue = revenue;
   }

   public String getTaxIdNumber()
   {
      return this.taxIdNumber;
   }

   public void setTaxIdNumber(final String taxIdNumber)
   {
      this.taxIdNumber = taxIdNumber;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      if (mobile != null && !mobile.trim().isEmpty())
         result += ", mobile: " + mobile;
      if (fax != null && !fax.trim().isEmpty())
         result += ", fax: " + fax;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (zipCode != null && !zipCode.trim().isEmpty())
         result += ", zipCode: " + zipCode;
      if (country != null && !country.trim().isEmpty())
         result += ", country: " + country;
      if (internetSite != null && !internetSite.trim().isEmpty())
         result += ", internetSite: " + internetSite;
      if (responsable != null && !responsable.trim().isEmpty())
         result += ", responsable: " + responsable;
      if (revenue != null && !revenue.trim().isEmpty())
         result += ", revenue: " + revenue;
      if (taxIdNumber != null && !taxIdNumber.trim().isEmpty())
         result += ", taxIdNumber: " + taxIdNumber;
      return result;
   }

@Override
public int compareTo(Supplier o) {
	return this.getName().compareToIgnoreCase(o.getName()) ;
}
}