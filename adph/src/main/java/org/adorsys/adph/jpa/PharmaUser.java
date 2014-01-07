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
import org.adorsys.adph.jpa.Gender;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.adorsys.adph.jpa.RoleName;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.adph.jpa.Site;
import java.math.BigDecimal;

@Entity
@Description("org.adorsys.adph.jpa.PharmaUser.description")
public class PharmaUser implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.userNumber.description")
   private String userNumber;

   @ManyToOne
   @Description("org.adorsys.adph.jpa.PharmaUser.gender.description")
   private Gender gender;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.userName.description")
   @NotNull
   private String userName;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.firstName.description")
   private String firstName;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.lastName.description")
   @NotNull
   private String lastName;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.fullName.description")
   @NotNull
   private String fullName;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.password.description")
   private String password;

   @ManyToMany
   @Description("org.adorsys.adph.jpa.PharmaUser.roleNames.description")
   private Set<RoleName> roleNames = new HashSet<RoleName>();

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.phoneNumber.description")
   private String phoneNumber;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.disableLogin.description")
   private boolean disableLogin;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.accountLocked.description")
   private boolean accountLocked;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.jpa.PharmaUser.credentialExpiration.description")
   private Date credentialExpiration;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.jpa.PharmaUser.accountExpiration.description")
   private Date accountExpiration;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.passwordSalt.description")
   private String passwordSalt;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.address.description")
   private String address;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.email.description")
   private String email;

   @ManyToOne
   @Description("org.adorsys.adph.jpa.PharmaUser.office.description")
   private Site office;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.discountRate.description")
   private BigDecimal discountRate;

   @Column
   @Description("org.adorsys.adph.jpa.PharmaUser.saleKey.description")
   private String saleKey;

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
         return id.equals(((PharmaUser) that).id);
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

   public String getUserNumber()
   {
      return this.userNumber;
   }

   public void setUserNumber(final String userNumber)
   {
      this.userNumber = userNumber;
   }

   public Gender getGender()
   {
      return this.gender;
   }

   public void setGender(final Gender gender)
   {
      this.gender = gender;
   }

   public String getUserName()
   {
      return this.userName;
   }

   public void setUserName(final String userName)
   {
      this.userName = userName;
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

   public String getPassword()
   {
      return this.password;
   }

   public void setPassword(final String password)
   {
      this.password = password;
   }

   public Set<RoleName> getRoleNames()
   {
      return this.roleNames;
   }

   public void setRoleNames(final Set<RoleName> roleNames)
   {
      this.roleNames = roleNames;
   }

   public String getPhoneNumber()
   {
      return this.phoneNumber;
   }

   public void setPhoneNumber(final String phoneNumber)
   {
      this.phoneNumber = phoneNumber;
   }

   public boolean getDisableLogin()
   {
      return this.disableLogin;
   }

   public void setDisableLogin(final boolean disableLogin)
   {
      this.disableLogin = disableLogin;
   }

   public boolean getAccountLocked()
   {
      return this.accountLocked;
   }

   public void setAccountLocked(final boolean accountLocked)
   {
      this.accountLocked = accountLocked;
   }

   public Date getCredentialExpiration()
   {
      return this.credentialExpiration;
   }

   public void setCredentialExpiration(final Date credentialExpiration)
   {
      this.credentialExpiration = credentialExpiration;
   }

   public Date getAccountExpiration()
   {
      return this.accountExpiration;
   }

   public void setAccountExpiration(final Date accountExpiration)
   {
      this.accountExpiration = accountExpiration;
   }

   public String getPasswordSalt()
   {
      return this.passwordSalt;
   }

   public void setPasswordSalt(final String passwordSalt)
   {
      this.passwordSalt = passwordSalt;
   }

   public String getAddress()
   {
      return this.address;
   }

   public void setAddress(final String address)
   {
      this.address = address;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public Site getOffice()
   {
      return this.office;
   }

   public void setOffice(final Site office)
   {
      this.office = office;
   }

   public BigDecimal getDiscountRate()
   {
      return this.discountRate;
   }

   public void setDiscountRate(final BigDecimal discountRate)
   {
      this.discountRate = discountRate;
   }

   public String getSaleKey()
   {
      return this.saleKey;
   }

   public void setSaleKey(final String saleKey)
   {
      this.saleKey = saleKey;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (userNumber != null && !userNumber.trim().isEmpty())
         result += "userNumber: " + userNumber;
      if (userName != null && !userName.trim().isEmpty())
         result += ", userName: " + userName;
      if (firstName != null && !firstName.trim().isEmpty())
         result += ", firstName: " + firstName;
      if (lastName != null && !lastName.trim().isEmpty())
         result += ", lastName: " + lastName;
      if (fullName != null && !fullName.trim().isEmpty())
         result += ", fullName: " + fullName;
      if (password != null && !password.trim().isEmpty())
         result += ", password: " + password;
      if (phoneNumber != null && !phoneNumber.trim().isEmpty())
         result += ", phoneNumber: " + phoneNumber;
      result += ", disableLogin: " + disableLogin;
      result += ", accountLocked: " + accountLocked;
      if (passwordSalt != null && !passwordSalt.trim().isEmpty())
         result += ", passwordSalt: " + passwordSalt;
      if (address != null && !address.trim().isEmpty())
         result += ", address: " + address;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (saleKey != null && !saleKey.trim().isEmpty())
         result += ", saleKey: " + saleKey;
      return result;
   }
}