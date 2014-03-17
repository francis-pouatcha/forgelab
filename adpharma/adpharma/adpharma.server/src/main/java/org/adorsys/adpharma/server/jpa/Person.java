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
import org.adorsys.adpharma.server.jpa.Gender;
import javax.persistence.Enumerated;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;
import javax.validation.constraints.NotNull;
import org.adorsys.adpharma.server.jpa.Agency;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import java.math.BigDecimal;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.adpharma.server.jpa.Login;

@Entity
@Description("Person_description")
@ListField({ "gender", "phoneNumber", "userName", "agency.name", "login.loginName" })
@ToStringField({ "gender", "login.loginName" })
public class Person implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("Person_gender_description")
   @Enumerated
   private Gender gender;

   @Column
   @Description("Person_firstName_description")
   private String firstName;

   @Column
   @Description("Person_lastName_description")
   @NotNull(message = "Person_lastName_NotNull_validation")
   private String lastName;

   @Column
   @Description("Person_phoneNumber_description")
   private String phoneNumber;

   @Column
   @Description("Person_street_description")
   private String street;

   @Column
   @Description("Person_zipCode_description")
   private String zipCode;

   @Column
   @Description("Person_city_description")
   private String city;

   @Column
   @Description("Person_country_description")
   private String country;

   @Column
   @Description("Person_email_description")
   private String email;

   @ManyToOne
   @Description("Person_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private Agency agency;

   @Column
   @Description("Person_discountRate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private BigDecimal discountRate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("Person_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date recordingDate;

   @ManyToOne
   @Description("Person_login_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.COMPOSITION, targetEntity = Login.class)
   private Login login;

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
         return id.equals(((Person) that).id);
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

   public Gender getGender()
   {
      return this.gender;
   }

   public void setGender(final Gender gender)
   {
      this.gender = gender;
   }

   public String getFirstName()
   {
      return this.firstName;
   }

   public void setFirstName(final String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return this.lastName;
   }

   public void setLastName(final String lastName)
   {
      this.lastName = lastName;
   }

   public String getPhoneNumber()
   {
      return this.phoneNumber;
   }

   public void setPhoneNumber(final String phoneNumber)
   {
      this.phoneNumber = phoneNumber;
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

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public Agency getAgency()
   {
      return this.agency;
   }

   public void setAgency(final Agency agency)
   {
      this.agency = agency;
   }

   public BigDecimal getDiscountRate()
   {
      return this.discountRate;
   }

   public void setDiscountRate(final BigDecimal discountRate)
   {
      this.discountRate = discountRate;
   }

   public Date getRecordingDate()
   {
      return this.recordingDate;
   }

   public void setRecordingDate(final Date recordingDate)
   {
      this.recordingDate = recordingDate;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (firstName != null && !firstName.trim().isEmpty())
         result += "firstName: " + firstName;
      if (lastName != null && !lastName.trim().isEmpty())
         result += ", lastName: " + lastName;
      if (phoneNumber != null && !phoneNumber.trim().isEmpty())
         result += ", phoneNumber: " + phoneNumber;
      if (street != null && !street.trim().isEmpty())
         result += ", street: " + street;
      if (zipCode != null && !zipCode.trim().isEmpty())
         result += ", zipCode: " + zipCode;
      if (city != null && !city.trim().isEmpty())
         result += ", city: " + city;
      if (country != null && !country.trim().isEmpty())
         result += ", country: " + country;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      return result;
   }

   public Login getLogin()
   {
      return this.login;
   }

   public void setLogin(final Login login)
   {
      this.login = login;
   }
}