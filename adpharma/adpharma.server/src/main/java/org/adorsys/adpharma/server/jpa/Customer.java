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
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;
import java.math.BigDecimal;
import org.adorsys.adpharma.server.jpa.Employer;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.adpharma.server.jpa.CustomerCategory;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.CustomerType;

@Entity
@Description("Customer_description")
@ToStringField("fullName")
@ListField({ "fullName", "birthDate", "landLinePhone", "mobile", "fax", "email",
      "creditAuthorized", "discountAuthorized" })
public class Customer implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("Customer_gender_description")
   @Enumerated
   private Gender gender;

   @Column
   @Description("Customer_firstName_description")
   private String firstName;

   @Column
   @Description("Customer_lastName_description")
   @NotNull(message = "Customer_lastName_NotNull_validation")
   private String lastName;

   @Column
   @Description("Customer_fullName_description")
   @NotNull(message = "Customer_fullName_NotNull_validation")
   private String fullName;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("Customer_birthDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy")
   private Date birthDate;

   @Column
   @Description("Customer_landLinePhone_description")
   private String landLinePhone;

   @Column
   @Description("Customer_mobile_description")
   private String mobile;

   @Column
   @Description("Customer_fax_description")
   private String fax;

   @Column
   @Description("Customer_email_description")
   private String email;
   
   @Column
   @Description("Customer_societe_description")
   private String societe;

   @Column
   @Description("Customer_creditAuthorized_description")
   private Boolean creditAuthorized;

   @Column
   @Description("Customer_discountAuthorized_description")
   private Boolean discountAuthorized;

   @Column
   @Description("Customer_totalCreditLine_description")
   private BigDecimal totalCreditLine;

   @ManyToOne
   @Description("Customer_employer_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Employer.class)
   private Employer employer;

   @ManyToOne
   @Description("Customer_customerCategory_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = CustomerCategory.class)
   private CustomerCategory customerCategory;

   @Column
   @Description("Customer_totalDebt_description")
   @NumberFormatType(NumberType.CURRENCY)
   private BigDecimal totalDebt;

   @Column
   @Description("Customer_customerType_description")
   @Enumerated
   private CustomerType customerType;

   @Column
   @Description("Customer_serialNumber_description")
   private String serialNumber;

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
         return id.equals(((Customer) that).id);
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

   public String getFullName()
   {
      return this.fullName;
   }

   public void setFullName(final String fullName)
   {
      this.fullName = fullName;
   }

   public Date getBirthDate()
   {
      return this.birthDate;
   }

   public void setBirthDate(final Date birthDate)
   {
      this.birthDate = birthDate;
   }

   public String getLandLinePhone()
   {
      return this.landLinePhone;
   }

   public void setLandLinePhone(final String landLinePhone)
   {
      this.landLinePhone = landLinePhone;
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

   public Boolean getCreditAuthorized()
   {
      return this.creditAuthorized;
   }

   public void setCreditAuthorized(final Boolean creditAuthorized)
   {
      this.creditAuthorized = creditAuthorized;
   }

   public Boolean getDiscountAuthorized()
   {
      return this.discountAuthorized;
   }

   public void setDiscountAuthorized(final Boolean discountAuthorized)
   {
      this.discountAuthorized = discountAuthorized;
   }

   public BigDecimal getTotalCreditLine()
   {
      return this.totalCreditLine;
   }

   public void setTotalCreditLine(final BigDecimal totalCreditLine)
   {
      this.totalCreditLine = totalCreditLine;
   }

   public Employer getEmployer()
   {
      return this.employer;
   }

   public void setEmployer(final Employer employer)
   {
      this.employer = employer;
   }

   public CustomerCategory getCustomerCategory()
   {
      return this.customerCategory;
   }

   public void setCustomerCategory(final CustomerCategory customerCategory)
   {
      this.customerCategory = customerCategory;
   }

   public BigDecimal getTotalDebt()
   {
      return this.totalDebt;
   }

   public void setTotalDebt(final BigDecimal totalDebt)
   {
      this.totalDebt = totalDebt;
   }

   public CustomerType getCustomerType()
   {
      return this.customerType;
   }

   public void setCustomerType(final CustomerType customerType)
   {
      this.customerType = customerType;
   }

   public String getSerialNumber()
   {
      return this.serialNumber;
   }

   public void setSerialNumber(final String serialNumber)
   {
      this.serialNumber = serialNumber;
   }

   public String getSociete() {
	return societe;
}

public void setSociete(String societe) {
	this.societe = societe;
}

@Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (firstName != null && !firstName.trim().isEmpty())
         result += "firstName: " + firstName;
      if (lastName != null && !lastName.trim().isEmpty())
         result += ", lastName: " + lastName;
      if (fullName != null && !fullName.trim().isEmpty())
         result += ", fullName: " + fullName;
      if (landLinePhone != null && !landLinePhone.trim().isEmpty())
         result += ", landLinePhone: " + landLinePhone;
      if (mobile != null && !mobile.trim().isEmpty())
         result += ", mobile: " + mobile;
      if (fax != null && !fax.trim().isEmpty())
         result += ", fax: " + fax;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (creditAuthorized != null)
         result += ", creditAuthorized: " + creditAuthorized;
      if (discountAuthorized != null)
         result += ", discountAuthorized: " + discountAuthorized;
      if (serialNumber != null && !serialNumber.trim().isEmpty())
         result += ", serialNumber: " + serialNumber;
      return result;
   }
}