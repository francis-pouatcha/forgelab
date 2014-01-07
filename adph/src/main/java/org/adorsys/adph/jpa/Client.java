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
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.adph.jpa.Gender;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import org.adorsys.adph.jpa.ClientType;

@Entity
@Description("org.adorsys.adph.jpa.Client.description")
public class Client implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.jpa.Client.clientNumber.description")
   private String clientNumber;

   @Column
   @Description("org.adorsys.adph.jpa.Client.name.description")
   private String name;

   @Column
   @Description("org.adorsys.adph.jpa.Client.firstName.description")
   private String firstName;

   @Column
   @Description("org.adorsys.adph.jpa.Client.fullName.description")
   private String fullName;

   @Column
   @Description("org.adorsys.adph.jpa.Client.landLinePhone.description")
   private String landLinePhone;

   @Column
   @Description("org.adorsys.adph.jpa.Client.mobile.description")
   private String mobile;

   @Column
   @Description("org.adorsys.adph.jpa.Client.fax.description")
   private String fax;

   @Column
   @Description("org.adorsys.adph.jpa.Client.email.description")
   private String email;

   @Column
   @Description("org.adorsys.adph.jpa.Client.creditAuthorized.description")
   private boolean creditAuthorized;

   @Column
   @Description("org.adorsys.adph.jpa.Client.discountAuthorized.description")
   private boolean discountAuthorized;

   @Column
   @Description("org.adorsys.adph.jpa.Client.totalCreditLine.description")
   private BigDecimal totalCreditLine;

   @Column
   @Description("org.adorsys.adph.jpa.Client.employer.description")
   private String employer;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.jpa.Client.birthDate.description")
   private Date birthDate;

   @ManyToOne
   @Description("org.adorsys.adph.jpa.Client.gender.description")
   private Gender gender;

   @Column
   @Description("org.adorsys.adph.jpa.Client.coverageRate.description")
   private BigDecimal coverageRate;

   @Column
   @Description("org.adorsys.adph.jpa.Client.note.description")
   @Size(max = 256)
   private String note;

   @ManyToOne
   @Description("org.adorsys.adph.jpa.Client.payingClient.description")
   private Client payingClient;

   @ManyToOne
   @Description("org.adorsys.adph.jpa.Client.clientCategory.description")
   private Client clientCategory;

   @Column
   @Description("org.adorsys.adph.jpa.Client.totalDebt.description")
   private BigDecimal totalDebt;

   @ManyToOne
   @Description("org.adorsys.adph.jpa.Client.clientType.description")
   private ClientType clientType;

   @Column
   @Description("org.adorsys.adph.jpa.Client.serialNumber.description")
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
         return id.equals(((Client) that).id);
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

   public String getClientNumber()
   {
      return this.clientNumber;
   }

   public void setClientNumber(final String clientNumber)
   {
      this.clientNumber = clientNumber;
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getFirstName()
   {
      return this.firstName;
   }

   public void setFirstName(final String firstName)
   {
      this.firstName = firstName;
   }

   public String getFullName()
   {
      return this.fullName;
   }

   public void setFullName(final String fullName)
   {
      this.fullName = fullName;
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

   public boolean getCreditAuthorized()
   {
      return this.creditAuthorized;
   }

   public void setCreditAuthorized(final boolean creditAuthorized)
   {
      this.creditAuthorized = creditAuthorized;
   }

   public boolean getDiscountAuthorized()
   {
      return this.discountAuthorized;
   }

   public void setDiscountAuthorized(final boolean discountAuthorized)
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

   public String getEmployer()
   {
      return this.employer;
   }

   public void setEmployer(final String employer)
   {
      this.employer = employer;
   }

   public Date getBirthDate()
   {
      return this.birthDate;
   }

   public void setBirthDate(final Date birthDate)
   {
      this.birthDate = birthDate;
   }

   public Gender getGender()
   {
      return this.gender;
   }

   public void setGender(final Gender gender)
   {
      this.gender = gender;
   }

   public BigDecimal getCoverageRate()
   {
      return this.coverageRate;
   }

   public void setCoverageRate(final BigDecimal coverageRate)
   {
      this.coverageRate = coverageRate;
   }

   public String getNote()
   {
      return this.note;
   }

   public void setNote(final String note)
   {
      this.note = note;
   }

   public Client getPayingClient()
   {
      return this.payingClient;
   }

   public void setPayingClient(final Client payingClient)
   {
      this.payingClient = payingClient;
   }

   public Client getClientCategory()
   {
      return this.clientCategory;
   }

   public void setClientCategory(final Client clientCategory)
   {
      this.clientCategory = clientCategory;
   }

   public BigDecimal getTotalDebt()
   {
      return this.totalDebt;
   }

   public void setTotalDebt(final BigDecimal totalDebt)
   {
      this.totalDebt = totalDebt;
   }

   public ClientType getClientType()
   {
      return this.clientType;
   }

   public void setClientType(final ClientType clientType)
   {
      this.clientType = clientType;
   }

   public String getSerialNumber()
   {
      return this.serialNumber;
   }

   public void setSerialNumber(final String serialNumber)
   {
      this.serialNumber = serialNumber;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (clientNumber != null && !clientNumber.trim().isEmpty())
         result += "clientNumber: " + clientNumber;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      if (firstName != null && !firstName.trim().isEmpty())
         result += ", firstName: " + firstName;
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
      result += ", creditAuthorized: " + creditAuthorized;
      result += ", discountAuthorized: " + discountAuthorized;
      if (employer != null && !employer.trim().isEmpty())
         result += ", employer: " + employer;
      if (note != null && !note.trim().isEmpty())
         result += ", note: " + note;
      if (serialNumber != null && !serialNumber.trim().isEmpty())
         result += ", serialNumber: " + serialNumber;
      return result;
   }
}