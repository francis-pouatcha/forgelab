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
import org.adorsys.javaext.admin.LoginTable;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;
import java.math.BigDecimal;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.Gender;
import javax.persistence.Enumerated;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import org.adorsys.javaext.relation.Relationship;
import org.adorsys.javaext.relation.RelationshipEnd;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.adpharma.server.jpa.Agency;
import javax.persistence.ManyToOne;

@Entity
@Description("Login_description")
@LoginTable(loginNameField = "loginName", fullNameField = "fullName", passwordField = "password")
@ToStringField({ "loginName", "gender" })
@ListField({ "loginName", "email", "gender" })
public class Login implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("Login_loginName_description")
   @NotNull(message = "Login_loginName_NotNull_validation")
   private String loginName;

   @Column
   @Description("Login_email_description")
   private String email;

   @Column
   @Description("Login_fullName_description")
   @NotNull(message = "Login_fullName_NotNull_validation")
   private String fullName;

   @Column
   @Description("Login_password_description")
   private String password;

   @Column
   @Description("Login_disableLogin_description")
   private Boolean disableLogin;

   @Column
   @Description("Login_accountLocked_description")
   private Boolean accountLocked;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("Login_credentialExpiration_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date credentialExpiration;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("Login_accountExpiration_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date accountExpiration;

   @Column
   @Description("Login_saleKey_description")
   private String saleKey;

   @Column
   @Description("Login_discountRate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private BigDecimal discountRate;

   @Column
   @Description("Login_gender_description")
   @Enumerated
   private Gender gender;

   @OneToMany(mappedBy = "source", targetEntity = LoginRoleNameAssoc.class)
   @Relationship(end = RelationshipEnd.SOURCE, sourceEntity = Login.class, targetEntity = RoleName.class, sourceQualifier = "roleNames")
   @Description("Login_roleNames_description")
   @Association(associationType = AssociationType.AGGREGATION, targetEntity = RoleName.class, selectionMode = SelectionMode.FORWARD)
   private Set<LoginRoleNameAssoc> roleNames = new HashSet<LoginRoleNameAssoc>();

   @Temporal(TemporalType.TIMESTAMP)
   @Description("Login_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date recordingDate;

   @ManyToOne
   @Description("Login_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private Agency agency;

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
         return id.equals(((Login) that).id);
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

   public String getLoginName()
   {
      return this.loginName;
   }

   public void setLoginName(final String loginName)
   {
      this.loginName = loginName;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
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

   public Boolean getDisableLogin()
   {
      return this.disableLogin;
   }

   public void setDisableLogin(final Boolean disableLogin)
   {
      this.disableLogin = disableLogin;
   }

   public Boolean getAccountLocked()
   {
      return this.accountLocked;
   }

   public void setAccountLocked(final Boolean accountLocked)
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

   public String getSaleKey()
   {
      return this.saleKey;
   }

   public void setSaleKey(final String saleKey)
   {
      this.saleKey = saleKey;
   }

   public BigDecimal getDiscountRate()
   {
      return this.discountRate;
   }

   public void setDiscountRate(final BigDecimal discountRate)
   {
      this.discountRate = discountRate;
   }

   public Gender getGender()
   {
      return this.gender;
   }

   public void setGender(final Gender gender)
   {
      this.gender = gender;
   }

   public Set<LoginRoleNameAssoc> getRoleNames()
   {
      return this.roleNames;
   }

   public void setRoleNames(final Set<LoginRoleNameAssoc> roleNames)
   {
      this.roleNames = roleNames;
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
      if (loginName != null && !loginName.trim().isEmpty())
         result += "loginName: " + loginName;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (fullName != null && !fullName.trim().isEmpty())
         result += ", fullName: " + fullName;
      if (password != null && !password.trim().isEmpty())
         result += ", password: " + password;
      if (disableLogin != null)
         result += ", disableLogin: " + disableLogin;
      if (accountLocked != null)
         result += ", accountLocked: " + accountLocked;
      if (saleKey != null && !saleKey.trim().isEmpty())
         result += ", saleKey: " + saleKey;
      return result;
   }

   public Agency getAgency()
   {
      return this.agency;
   }

   public void setAgency(final Agency agency)
   {
      this.agency = agency;
   }
}