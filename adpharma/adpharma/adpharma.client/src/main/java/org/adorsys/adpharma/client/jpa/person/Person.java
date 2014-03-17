package org.adorsys.adpharma.client.jpa.person;

import org.adorsys.adpharma.client.jpa.gender.Gender;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import java.math.BigDecimal;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.login.Login;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Person_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "gender", "phoneNumber", "userName", "agency.name",
      "login.loginName" })
@ToStringField({ "gender", "login.loginName" })
public class Person
{

   private Long id;
   private int version;

   @Description("Person_firstName_description")
   private SimpleStringProperty firstName;
   @Description("Person_lastName_description")
   private SimpleStringProperty lastName;
   @Description("Person_phoneNumber_description")
   private SimpleStringProperty phoneNumber;
   @Description("Person_street_description")
   private SimpleStringProperty street;
   @Description("Person_zipCode_description")
   private SimpleStringProperty zipCode;
   @Description("Person_city_description")
   private SimpleStringProperty city;
   @Description("Person_country_description")
   private SimpleStringProperty country;
   @Description("Person_email_description")
   private SimpleStringProperty email;
   @Description("Person_gender_description")
   private SimpleObjectProperty<Gender> gender;
   @Description("Person_discountRate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private SimpleObjectProperty<BigDecimal> discountRate;
   @Description("Person_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordingDate;
   @Description("Person_login_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.COMPOSITION, targetEntity = Login.class)
   private SimpleObjectProperty<PersonLogin> login;
   @Description("Person_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<PersonAgency> agency;

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

   public SimpleStringProperty firstNameProperty()
   {
      if (firstName == null)
      {
         firstName = new SimpleStringProperty();
      }
      return firstName;
   }

   public String getFirstName()
   {
      return firstNameProperty().get();
   }

   public final void setFirstName(String firstName)
   {
      this.firstNameProperty().set(firstName);
   }

   public SimpleStringProperty lastNameProperty()
   {
      if (lastName == null)
      {
         lastName = new SimpleStringProperty();
      }
      return lastName;
   }

   @NotNull(message = "Person_lastName_NotNull_validation")
   public String getLastName()
   {
      return lastNameProperty().get();
   }

   public final void setLastName(String lastName)
   {
      this.lastNameProperty().set(lastName);
   }

   public SimpleStringProperty phoneNumberProperty()
   {
      if (phoneNumber == null)
      {
         phoneNumber = new SimpleStringProperty();
      }
      return phoneNumber;
   }

   public String getPhoneNumber()
   {
      return phoneNumberProperty().get();
   }

   public final void setPhoneNumber(String phoneNumber)
   {
      this.phoneNumberProperty().set(phoneNumber);
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

   public SimpleObjectProperty<Gender> genderProperty()
   {
      if (gender == null)
      {
         gender = new SimpleObjectProperty<Gender>();
      }
      return gender;
   }

   public Gender getGender()
   {
      return genderProperty().get();
   }

   public final void setGender(Gender gender)
   {
      this.genderProperty().set(gender);
   }

   public SimpleObjectProperty<BigDecimal> discountRateProperty()
   {
      if (discountRate == null)
      {
         discountRate = new SimpleObjectProperty<BigDecimal>();
      }
      return discountRate;
   }

   public BigDecimal getDiscountRate()
   {
      return discountRateProperty().get();
   }

   public final void setDiscountRate(BigDecimal discountRate)
   {
      this.discountRateProperty().set(discountRate);
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

   public SimpleObjectProperty<PersonLogin> loginProperty()
   {
      if (login == null)
      {
         login = new SimpleObjectProperty<PersonLogin>(new PersonLogin());
      }
      return login;
   }

   public PersonLogin getLogin()
   {
      return loginProperty().get();
   }

   public final void setLogin(PersonLogin login)
   {
      if (login == null)
      {
         login = new PersonLogin();
      }
      PropertyReader.copy(login, getLogin());
   }

   public SimpleObjectProperty<PersonAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<PersonAgency>(new PersonAgency());
      }
      return agency;
   }

   public PersonAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(PersonAgency agency)
   {
      if (agency == null)
      {
         agency = new PersonAgency();
      }
      PropertyReader.copy(agency, getAgency());
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
      Person other = (Person) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "gender", "loginName");
   }
}