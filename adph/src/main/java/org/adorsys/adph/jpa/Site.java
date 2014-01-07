package org.adorsys.adph.jpa;

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
import javax.validation.constraints.Size;

@Entity
@Description("org.adorsys.adph.jpa.Site.description")
public class Site implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.jpa.Site.displayName.description")
   @NotNull
   private String displayName;

   @Column
   @Description("org.adorsys.adph.jpa.Site.address.description")
   @Size(max = 256)
   private String address;

   @Column
   @Description("org.adorsys.adph.jpa.Site.city.description")
   private String city;

   @Column
   @Description("org.adorsys.adph.jpa.Site.country.description")
   private String country;

   @Column
   @Description("org.adorsys.adph.jpa.Site.siteNumber.description")
   private String siteNumber;

   @Column
   @Description("org.adorsys.adph.jpa.Site.phone.description")
   private String phone;

   @Column
   @Description("org.adorsys.adph.jpa.Site.siteManager.description")
   private String siteManager;

   @Column
   @Description("org.adorsys.adph.jpa.Site.email.description")
   private String email;

   @Column
   @Description("org.adorsys.adph.jpa.Site.siteInternet.description")
   private String siteInternet;

   @Column
   @Description("org.adorsys.adph.jpa.Site.mobile.description")
   private String mobile;

   @Column
   @Description("org.adorsys.adph.jpa.Site.fax.description")
   private String fax;

   @Column
   @Description("org.adorsys.adph.jpa.Site.registerNumber.description")
   private String registerNumber;

   @Column
   @Description("org.adorsys.adph.jpa.Site.messageTicker.description")
   private String messageTicker;

   @Column
   @Description("org.adorsys.adph.jpa.Site.barCodePerLine.description")
   private long barCodePerLine;

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
         return id.equals(((Site) that).id);
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

   public String getDisplayName()
   {
      return this.displayName;
   }

   public void setDisplayName(final String displayName)
   {
      this.displayName = displayName;
   }

   public String getAddress()
   {
      return this.address;
   }

   public void setAddress(final String address)
   {
      this.address = address;
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

   public String getSiteNumber()
   {
      return this.siteNumber;
   }

   public void setSiteNumber(final String siteNumber)
   {
      this.siteNumber = siteNumber;
   }

   public String getPhone()
   {
      return this.phone;
   }

   public void setPhone(final String phone)
   {
      this.phone = phone;
   }

   public String getSiteManager()
   {
      return this.siteManager;
   }

   public void setSiteManager(final String siteManager)
   {
      this.siteManager = siteManager;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String getSiteInternet()
   {
      return this.siteInternet;
   }

   public void setSiteInternet(final String siteInternet)
   {
      this.siteInternet = siteInternet;
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

   public String getRegisterNumber()
   {
      return this.registerNumber;
   }

   public void setRegisterNumber(final String registerNumber)
   {
      this.registerNumber = registerNumber;
   }

   public String getMessageTicker()
   {
      return this.messageTicker;
   }

   public void setMessageTicker(final String messageTicker)
   {
      this.messageTicker = messageTicker;
   }

   public long getBarCodePerLine()
   {
      return this.barCodePerLine;
   }

   public void setBarCodePerLine(final long barCodePerLine)
   {
      this.barCodePerLine = barCodePerLine;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (displayName != null && !displayName.trim().isEmpty())
         result += "displayName: " + displayName;
      if (address != null && !address.trim().isEmpty())
         result += ", address: " + address;
      if (city != null && !city.trim().isEmpty())
         result += ", city: " + city;
      if (country != null && !country.trim().isEmpty())
         result += ", country: " + country;
      if (siteNumber != null && !siteNumber.trim().isEmpty())
         result += ", siteNumber: " + siteNumber;
      if (phone != null && !phone.trim().isEmpty())
         result += ", phone: " + phone;
      if (siteManager != null && !siteManager.trim().isEmpty())
         result += ", siteManager: " + siteManager;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (siteInternet != null && !siteInternet.trim().isEmpty())
         result += ", siteInternet: " + siteInternet;
      if (mobile != null && !mobile.trim().isEmpty())
         result += ", mobile: " + mobile;
      if (fax != null && !fax.trim().isEmpty())
         result += ", fax: " + fax;
      if (registerNumber != null && !registerNumber.trim().isEmpty())
         result += ", registerNumber: " + registerNumber;
      if (messageTicker != null && !messageTicker.trim().isEmpty())
         result += ", messageTicker: " + messageTicker;
      result += ", barCodePerLine: " + barCodePerLine;
      return result;
   }
}