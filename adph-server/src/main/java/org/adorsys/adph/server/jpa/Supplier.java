package org.adorsys.adph.server.jpa;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.adorsys.javaext.description.Description;
import javax.validation.constraints.Size;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.Supplier.description")
@Audited
@XmlRootElement
public class Supplier implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.supplierNumber.description")
   private String supplierNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.name.description")
   private String name;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.shortName.description")
   private String shortName;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.phone.description")
   private String phone;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.mobile.description")
   private String mobile;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.fax.description")
   private String fax;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.email.description")
   private String email;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.adresse.description")
   private String adresse;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.internetSite.description")
   private String internetSite;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.responsable.description")
   private String responsable;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.revenue.description")
   private String revenue;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.city.description")
   private String city;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.country.description")
   private String country;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.zipCode.description")
   private String zipCode;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.taxIdNumber.description")
   private String taxIdNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.commRegNumber.description")
   private String commRegNumber;

   @Column
   @Description("org.adorsys.adph.server.jpa.Supplier.note.description")
   @Size(max = 256)
   private String note;

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

   public String getSupplierNumber()
   {
      return this.supplierNumber;
   }

   public void setSupplierNumber(final String supplierNumber)
   {
      this.supplierNumber = supplierNumber;
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getShortName()
   {
      return this.shortName;
   }

   public void setShortName(final String shortName)
   {
      this.shortName = shortName;
   }

   public String getPhone()
   {
      return this.phone;
   }

   public void setPhone(final String phone)
   {
      this.phone = phone;
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

   public String getAdresse()
   {
      return this.adresse;
   }

   public void setAdresse(final String adresse)
   {
      this.adresse = adresse;
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

   public String getCity()
   {
      return this.city;
   }

   public void setCity(final String city)
   {
      this.city = city;
   }

   public String getCountry()
   {
      return this.country;
   }

   public void setCountry(final String country)
   {
      this.country = country;
   }

   public String getZipCode()
   {
      return this.zipCode;
   }

   public void setZipCode(final String zipCode)
   {
      this.zipCode = zipCode;
   }

   public String getTaxIdNumber()
   {
      return this.taxIdNumber;
   }

   public void setTaxIdNumber(final String taxIdNumber)
   {
      this.taxIdNumber = taxIdNumber;
   }

   public String getCommRegNumber()
   {
      return this.commRegNumber;
   }

   public void setCommRegNumber(final String commRegNumber)
   {
      this.commRegNumber = commRegNumber;
   }

   public String getNote()
   {
      return this.note;
   }

   public void setNote(final String note)
   {
      this.note = note;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (supplierNumber != null && !supplierNumber.trim().isEmpty())
         result += "supplierNumber: " + supplierNumber;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      if (shortName != null && !shortName.trim().isEmpty())
         result += ", shortName: " + shortName;
      if (phone != null && !phone.trim().isEmpty())
         result += ", phone: " + phone;
      if (mobile != null && !mobile.trim().isEmpty())
         result += ", mobile: " + mobile;
      if (fax != null && !fax.trim().isEmpty())
         result += ", fax: " + fax;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (adresse != null && !adresse.trim().isEmpty())
         result += ", adresse: " + adresse;
      if (internetSite != null && !internetSite.trim().isEmpty())
         result += ", internetSite: " + internetSite;
      if (responsable != null && !responsable.trim().isEmpty())
         result += ", responsable: " + responsable;
      if (revenue != null && !revenue.trim().isEmpty())
         result += ", revenue: " + revenue;
      if (city != null && !city.trim().isEmpty())
         result += ", city: " + city;
      if (country != null && !country.trim().isEmpty())
         result += ", country: " + country;
      if (zipCode != null && !zipCode.trim().isEmpty())
         result += ", zipCode: " + zipCode;
      if (taxIdNumber != null && !taxIdNumber.trim().isEmpty())
         result += ", taxIdNumber: " + taxIdNumber;
      if (commRegNumber != null && !commRegNumber.trim().isEmpty())
         result += ", commRegNumber: " + commRegNumber;
      if (note != null && !note.trim().isEmpty())
         result += ", note: " + note;
      return result;
   }
}