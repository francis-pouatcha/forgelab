package org.adorsys.adpharma.client.jpa.company;

import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.apache.commons.lang3.ObjectUtils;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Company_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("displayName")
@ListField({ "displayName", "phone", "fax", "siteManager", "email" })
public class Company implements Cloneable
{

   private Long id;
   private int version;

   @Description("Company_displayName_description")
   private SimpleStringProperty displayName;
   @Description("Company_phone_description")
   private SimpleStringProperty phone;
   @Description("Company_fax_description")
   private SimpleStringProperty fax;
   @Description("Company_siteManager_description")
   private SimpleStringProperty siteManager;
   @Description("Company_email_description")
   private SimpleStringProperty email;
   @Description("Company_street_description")
   private SimpleStringProperty street;
   @Description("Company_zipCode_description")
   private SimpleStringProperty zipCode;
   @Description("Company_city_description")
   private SimpleStringProperty city;
   @Description("Company_country_description")
   private SimpleStringProperty country;
   @Description("Company_siteInternet_description")
   private SimpleStringProperty siteInternet;
   @Description("Company_mobile_description")
   private SimpleStringProperty mobile;
   @Description("Company_registerNumber_description")
   private SimpleStringProperty registerNumber;
   @Description("Company_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordingDate;

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

   public SimpleStringProperty displayNameProperty()
   {
      if (displayName == null)
      {
         displayName = new SimpleStringProperty();
      }
      return displayName;
   }

   @NotNull(message = "Company_displayName_NotNull_validation")
   public String getDisplayName()
   {
      return displayNameProperty().get();
   }

   public final void setDisplayName(String displayName)
   {
      this.displayNameProperty().set(displayName);
   }

   public SimpleStringProperty phoneProperty()
   {
      if (phone == null)
      {
         phone = new SimpleStringProperty();
      }
      return phone;
   }

   public String getPhone()
   {
      return phoneProperty().get();
   }

   public final void setPhone(String phone)
   {
      this.phoneProperty().set(phone);
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

   public SimpleStringProperty siteManagerProperty()
   {
      if (siteManager == null)
      {
         siteManager = new SimpleStringProperty();
      }
      return siteManager;
   }

   public String getSiteManager()
   {
      return siteManagerProperty().get();
   }

   public final void setSiteManager(String siteManager)
   {
      this.siteManagerProperty().set(siteManager);
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

   public SimpleStringProperty streetProperty()
   {
      if (street == null)
      {
         street = new SimpleStringProperty();
      }
      return street;
   }

   public String getStreet()
   {
      return streetProperty().get();
   }

   public final void setStreet(String street)
   {
      this.streetProperty().set(street);
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

   public SimpleStringProperty cityProperty()
   {
      if (city == null)
      {
         city = new SimpleStringProperty();
      }
      return city;
   }

   public String getCity()
   {
      return cityProperty().get();
   }

   public final void setCity(String city)
   {
      this.cityProperty().set(city);
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

   public SimpleStringProperty siteInternetProperty()
   {
      if (siteInternet == null)
      {
         siteInternet = new SimpleStringProperty();
      }
      return siteInternet;
   }

   public String getSiteInternet()
   {
      return siteInternetProperty().get();
   }

   public final void setSiteInternet(String siteInternet)
   {
      this.siteInternetProperty().set(siteInternet);
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

   public SimpleStringProperty registerNumberProperty()
   {
      if (registerNumber == null)
      {
         registerNumber = new SimpleStringProperty();
      }
      return registerNumber;
   }

   public String getRegisterNumber()
   {
      return registerNumberProperty().get();
   }

   public final void setRegisterNumber(String registerNumber)
   {
      this.registerNumberProperty().set(registerNumber);
   }

   public SimpleObjectProperty<Calendar> recordingDateProperty()
   {
      if (recordingDate == null)
      {
         recordingDate = new SimpleObjectProperty<Calendar>();
      }
      return recordingDate;
   }

   public Calendar getRecordingDate()
   {
      return recordingDateProperty().get();
   }

   public final void setRecordingDate(Calendar recordingDate)
   {
      this.recordingDateProperty().set(recordingDate);
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
      Company other = (Company) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "displayName");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      Company e = new Company();
      e.id = id;
      e.version = version;

      e.displayName = displayName;
      e.phone = phone;
      e.fax = fax;
      e.siteManager = siteManager;
      e.email = email;
      e.street = street;
      e.zipCode = zipCode;
      e.city = city;
      e.country = country;
      e.siteInternet = siteInternet;
      e.mobile = mobile;
      e.registerNumber = registerNumber;
      e.recordingDate = recordingDate;
      return e;
   }
}