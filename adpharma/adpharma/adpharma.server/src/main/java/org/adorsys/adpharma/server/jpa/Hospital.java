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
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@Entity
@Description("Hospital_description")
@ToStringField("name")
@ListField({ "name", "phone", "street", "city" })
public class Hospital implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("Hospital_name_description")
   @NotNull(message = "Hospital_name_NotNull_validation")
   private String name;

   @Column
   @Description("Hospital_phone_description")
   private String phone;

   @Column
   @Description("Hospital_street_description")
   private String street;

   @Column
   @Description("Hospital_zipCode_description")
   private String zipCode;

   @Column
   @Description("Hospital_city_description")
   private String city;

   @Column
   @Description("Hospital_country_description")
   private String country;

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
         return id.equals(((Hospital) that).id);
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

   public String getPhone()
   {
      return this.phone;
   }

   public void setPhone(final String phone)
   {
      this.phone = phone;
   }

   public String getStreet()
   {
      return this.street;
   }

   public void setStreet(final String street)
   {
      this.street = street;
   }

   public String getZipCode()
   {
      return this.zipCode;
   }

   public void setZipCode(final String zipCode)
   {
      this.zipCode = zipCode;
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

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (name != null && !name.trim().isEmpty())
         result += "name: " + name;
      if (phone != null && !phone.trim().isEmpty())
         result += ", phone: " + phone;
      if (street != null && !street.trim().isEmpty())
         result += ", street: " + street;
      if (zipCode != null && !zipCode.trim().isEmpty())
         result += ", zipCode: " + zipCode;
      if (city != null && !city.trim().isEmpty())
         result += ", city: " + city;
      if (country != null && !country.trim().isEmpty())
         result += ", country: " + country;
      return result;
   }
}