package org.adorsys.adpharma.client.jpa.customer;

import org.adorsys.adpharma.client.jpa.gender.Gender;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleBooleanProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.employer.Employer;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.customertype.CustomerType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.apache.commons.lang3.ObjectUtils;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Customer_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("fullName")
@ListField({ "fullName", "birthDate", "landLinePhone", "mobile", "fax",
      "email", "creditAuthorized", "discountAuthorized" })
public class Customer implements Cloneable
{

   private Long id;
   private int version;

   @Description("Customer_firstName_description")
   private SimpleStringProperty firstName;
   @Description("Customer_lastName_description")
   private SimpleStringProperty lastName;
   @Description("Customer_fullName_description")
   private SimpleStringProperty fullName;
   @Description("Customer_landLinePhone_description")
   private SimpleStringProperty landLinePhone;
   @Description("Customer_mobile_description")
   private SimpleStringProperty mobile;
   @Description("Customer_fax_description")
   private SimpleStringProperty fax;
   @Description("Customer_email_description")
   private SimpleStringProperty email;
   @Description("Customer_creditAuthorized_description")
   private SimpleBooleanProperty creditAuthorized;
   @Description("Customer_discountAuthorized_description")
   private SimpleBooleanProperty discountAuthorized;
   @Description("Customer_serialNumber_description")
   private SimpleStringProperty serialNumber;
   @Description("Customer_gender_description")
   private SimpleObjectProperty<Gender> gender;
   @Description("Customer_customerType_description")
   private SimpleObjectProperty<CustomerType> customerType;
   @Description("Customer_totalCreditLine_description")
   private SimpleObjectProperty<BigDecimal> totalCreditLine;
   @Description("Customer_totalDebt_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalDebt;
   @Description("Customer_birthDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private SimpleObjectProperty<Calendar> birthDate;
   @Description("Customer_employer_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Employer.class)
   private SimpleObjectProperty<CustomerEmployer> employer;
   @Description("Customer_customerCategory_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = CustomerCategory.class)
   private SimpleObjectProperty<CustomerCustomerCategory> customerCategory;

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

   @NotNull(message = "Customer_lastName_NotNull_validation")
   public String getLastName()
   {
      return lastNameProperty().get();
   }

   public final void setLastName(String lastName)
   {
      this.lastNameProperty().set(lastName);
   }

   public SimpleStringProperty fullNameProperty()
   {
      if (fullName == null)
      {
         fullName = new SimpleStringProperty();
      }
      return fullName;
   }

   @NotNull(message = "Customer_fullName_NotNull_validation")
   public String getFullName()
   {
      return fullNameProperty().get();
   }

   public final void setFullName(String fullName)
   {
      this.fullNameProperty().set(fullName);
   }

   public SimpleStringProperty landLinePhoneProperty()
   {
      if (landLinePhone == null)
      {
         landLinePhone = new SimpleStringProperty();
      }
      return landLinePhone;
   }

   public String getLandLinePhone()
   {
      return landLinePhoneProperty().get();
   }

   public final void setLandLinePhone(String landLinePhone)
   {
      this.landLinePhoneProperty().set(landLinePhone);
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

   public SimpleBooleanProperty creditAuthorizedProperty()
   {
      if (creditAuthorized == null)
      {
         creditAuthorized = new SimpleBooleanProperty();
      }
      return creditAuthorized;
   }

   public Boolean getCreditAuthorized()
   {
      return creditAuthorizedProperty().get();
   }

   public final void setCreditAuthorized(Boolean creditAuthorized)
   {
      if (creditAuthorized == null)
         creditAuthorized = Boolean.FALSE;
      this.creditAuthorizedProperty().set(creditAuthorized);
   }

   public SimpleBooleanProperty discountAuthorizedProperty()
   {
      if (discountAuthorized == null)
      {
         discountAuthorized = new SimpleBooleanProperty();
      }
      return discountAuthorized;
   }

   public Boolean getDiscountAuthorized()
   {
      return discountAuthorizedProperty().get();
   }

   public final void setDiscountAuthorized(Boolean discountAuthorized)
   {
      if (discountAuthorized == null)
         discountAuthorized = Boolean.FALSE;
      this.discountAuthorizedProperty().set(discountAuthorized);
   }

   public SimpleStringProperty serialNumberProperty()
   {
      if (serialNumber == null)
      {
         serialNumber = new SimpleStringProperty();
      }
      return serialNumber;
   }

   public String getSerialNumber()
   {
      return serialNumberProperty().get();
   }

   public final void setSerialNumber(String serialNumber)
   {
      this.serialNumberProperty().set(serialNumber);
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

   public SimpleObjectProperty<CustomerType> customerTypeProperty()
   {
      if (customerType == null)
      {
         customerType = new SimpleObjectProperty<CustomerType>();
      }
      return customerType;
   }

   public CustomerType getCustomerType()
   {
      return customerTypeProperty().get();
   }

   public final void setCustomerType(CustomerType customerType)
   {
      this.customerTypeProperty().set(customerType);
   }

   public SimpleObjectProperty<BigDecimal> totalCreditLineProperty()
   {
      if (totalCreditLine == null)
      {
         totalCreditLine = new SimpleObjectProperty<BigDecimal>();
      }
      return totalCreditLine;
   }

   public BigDecimal getTotalCreditLine()
   {
      return totalCreditLineProperty().get();
   }

   public final void setTotalCreditLine(BigDecimal totalCreditLine)
   {
      this.totalCreditLineProperty().set(totalCreditLine);
   }

   public SimpleObjectProperty<BigDecimal> totalDebtProperty()
   {
      if (totalDebt == null)
      {
         totalDebt = new SimpleObjectProperty<BigDecimal>();
      }
      return totalDebt;
   }

   public BigDecimal getTotalDebt()
   {
      return totalDebtProperty().get();
   }

   public final void setTotalDebt(BigDecimal totalDebt)
   {
      this.totalDebtProperty().set(totalDebt);
   }

   public SimpleObjectProperty<Calendar> birthDateProperty()
   {
      if (birthDate == null)
      {
         birthDate = new SimpleObjectProperty<Calendar>();
      }
      return birthDate;
   }

   public Calendar getBirthDate()
   {
      return birthDateProperty().get();
   }

   public final void setBirthDate(Calendar birthDate)
   {
      this.birthDateProperty().set(birthDate);
   }

   public SimpleObjectProperty<CustomerEmployer> employerProperty()
   {
      if (employer == null)
      {
         employer = new SimpleObjectProperty<CustomerEmployer>(new CustomerEmployer());
      }
      return employer;
   }

   public CustomerEmployer getEmployer()
   {
      return employerProperty().get();
   }

   public final void setEmployer(CustomerEmployer employer)
   {
      if (employer == null)
      {
         employer = new CustomerEmployer();
      }
      PropertyReader.copy(employer, getEmployer());
      employerProperty().setValue(ObjectUtils.clone(getEmployer()));
   }

   public SimpleObjectProperty<CustomerCustomerCategory> customerCategoryProperty()
   {
      if (customerCategory == null)
      {
         customerCategory = new SimpleObjectProperty<CustomerCustomerCategory>(new CustomerCustomerCategory());
      }
      return customerCategory;
   }

   public CustomerCustomerCategory getCustomerCategory()
   {
      return customerCategoryProperty().get();
   }

   public final void setCustomerCategory(CustomerCustomerCategory customerCategory)
   {
      if (customerCategory == null)
      {
         customerCategory = new CustomerCustomerCategory();
      }
      PropertyReader.copy(customerCategory, getCustomerCategory());
      customerCategoryProperty().setValue(ObjectUtils.clone(getCustomerCategory()));
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
      Customer other = (Customer) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "fullName");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      Customer e = new Customer();
      e.id = id;
      e.version = version;

      e.firstName = firstName;
      e.lastName = lastName;
      e.fullName = fullName;
      e.landLinePhone = landLinePhone;
      e.mobile = mobile;
      e.fax = fax;
      e.email = email;
      e.creditAuthorized = creditAuthorized;
      e.discountAuthorized = discountAuthorized;
      e.serialNumber = serialNumber;
      e.gender = gender;
      e.customerType = customerType;
      e.totalCreditLine = totalCreditLine;
      e.totalDebt = totalDebt;
      e.birthDate = birthDate;
      e.employer = employer;
      e.customerCategory = customerCategory;
      return e;
   }
}