package org.adorsys.adpharma.client.jpa.login;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.gender.Gender;
import org.adorsys.adpharma.client.jpa.rolename.RoleName;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.apache.commons.lang3.ObjectUtils;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.relation.Relationship;
import org.adorsys.javaext.relation.RelationshipEnd;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Login_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "loginName", "gender" })
@ListField({ "loginName", "email", "gender" })
public class Login implements Cloneable
{

   private Long id;
   private int version;

   @Description("Login_loginName_description")
   private SimpleStringProperty loginName;
   @Description("Login_email_description")
   private SimpleStringProperty email;
   @Description("Login_fullName_description")
   private SimpleStringProperty fullName;
   @Description("Login_password_description")
   private SimpleStringProperty password;
   @Description("Login_disableLogin_description")
   private SimpleBooleanProperty disableLogin;
   @Description("Login_accountLocked_description")
   private SimpleBooleanProperty accountLocked;
   @Description("Login_saleKey_description")
   private SimpleStringProperty saleKey;
   @Description("Login_gender_description")
   private SimpleObjectProperty<Gender> gender;
   @Description("Login_discountRate_description")
   @NumberFormatType(NumberType.PERCENTAGE)
   private SimpleObjectProperty<BigDecimal> discountRate;
   @Description("Login_credentialExpiration_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> credentialExpiration;
   @Description("Login_accountExpiration_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> accountExpiration;
   @Description("Login_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordingDate;
   @Relationship(end = RelationshipEnd.SOURCE, sourceEntity = Login.class, targetEntity = RoleName.class, sourceQualifier = "roleNames")
   @Description("Login_roleNames_description")
   @Association(associationType = AssociationType.AGGREGATION, targetEntity = RoleName.class, selectionMode = SelectionMode.FORWARD)
   private SimpleObjectProperty<ObservableList<RoleName>> roleNames;
   @Description("Login_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<LoginAgency> agency;

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

   public SimpleStringProperty loginNameProperty()
   {
      if (loginName == null)
      {
         loginName = new SimpleStringProperty();
      }
      return loginName;
   }

   @NotNull(message = "Login_loginName_NotNull_validation")
   public String getLoginName()
   {
      return loginNameProperty().get();
   }

   public final void setLoginName(String loginName)
   {
      this.loginNameProperty().set(loginName);
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

   public SimpleStringProperty fullNameProperty()
   {
      if (fullName == null)
      {
         fullName = new SimpleStringProperty();
      }
      return fullName;
   }

   @NotNull(message = "Login_fullName_NotNull_validation")
   public String getFullName()
   {
      return fullNameProperty().get();
   }

   public final void setFullName(String fullName)
   {
      this.fullNameProperty().set(fullName);
   }

   public SimpleStringProperty passwordProperty()
   {
      if (password == null)
      {
         password = new SimpleStringProperty();
      }
      return password;
   }

   public String getPassword()
   {
      return passwordProperty().get();
   }

   public final void setPassword(String password)
   {
      this.passwordProperty().set(password);
   }

   public SimpleBooleanProperty disableLoginProperty()
   {
      if (disableLogin == null)
      {
         disableLogin = new SimpleBooleanProperty();
      }
      return disableLogin;
   }

   public Boolean getDisableLogin()
   {
      return disableLoginProperty().get();
   }

   public final void setDisableLogin(Boolean disableLogin)
   {
      if (disableLogin == null)
         disableLogin = Boolean.FALSE;
      this.disableLoginProperty().set(disableLogin);
   }

   public SimpleBooleanProperty accountLockedProperty()
   {
      if (accountLocked == null)
      {
         accountLocked = new SimpleBooleanProperty();
      }
      return accountLocked;
   }

   public Boolean getAccountLocked()
   {
      return accountLockedProperty().get();
   }

   public final void setAccountLocked(Boolean accountLocked)
   {
      if (accountLocked == null)
         accountLocked = Boolean.FALSE;
      this.accountLockedProperty().set(accountLocked);
   }

   public SimpleStringProperty saleKeyProperty()
   {
      if (saleKey == null)
      {
         saleKey = new SimpleStringProperty();
      }
      return saleKey;
   }

   public String getSaleKey()
   {
      return saleKeyProperty().get();
   }

   public final void setSaleKey(String saleKey)
   {
      this.saleKeyProperty().set(saleKey);
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

   public SimpleObjectProperty<Calendar> credentialExpirationProperty()
   {
      if (credentialExpiration == null)
      {
         credentialExpiration = new SimpleObjectProperty<Calendar>();
      }
      return credentialExpiration;
   }

   public Calendar getCredentialExpiration()
   {
      return credentialExpirationProperty().get();
   }

   public final void setCredentialExpiration(Calendar credentialExpiration)
   {
      this.credentialExpirationProperty().set(credentialExpiration);
   }

   public SimpleObjectProperty<Calendar> accountExpirationProperty()
   {
      if (accountExpiration == null)
      {
         accountExpiration = new SimpleObjectProperty<Calendar>();
      }
      return accountExpiration;
   }

   public Calendar getAccountExpiration()
   {
      return accountExpirationProperty().get();
   }

   public final void setAccountExpiration(Calendar accountExpiration)
   {
      this.accountExpirationProperty().set(accountExpiration);
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

   public SimpleObjectProperty<ObservableList<RoleName>> roleNamesProperty()
   {
      if (roleNames == null)
      {
         ObservableList<RoleName> observableArrayList = FXCollections.observableArrayList();
         roleNames = new SimpleObjectProperty<ObservableList<RoleName>>(observableArrayList);
      }
      return roleNames;
   }

   public List<RoleName> getRoleNames()
   {
      return new ArrayList<RoleName>(roleNamesProperty().get());
   }

   public final void setRoleNames(List<RoleName> roleNames)
   {
      this.roleNamesProperty().get().clear();
      if (roleNames != null)
         this.roleNamesProperty().get().addAll(roleNames);
   }

   public final void addToRoleNames(RoleName entity)
   {
      this.roleNamesProperty().get().add(entity);
   }

   public SimpleObjectProperty<LoginAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<LoginAgency>(new LoginAgency());
      }
      return agency;
   }

   public LoginAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(LoginAgency agency)
   {
      if (agency == null)
      {
         agency = new LoginAgency();
      }
      PropertyReader.copy(agency, getAgency());
      agencyProperty().setValue(ObjectUtils.clone(getAgency()));
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
      Login other = (Login) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "loginName", "gender");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      Login e = new Login();
      e.id = id;
      e.version = version;

      e.loginName = loginName;
      e.email = email;
      e.fullName = fullName;
      e.password = password;
      e.disableLogin = disableLogin;
      e.accountLocked = accountLocked;
      e.saleKey = saleKey;
      e.gender = gender;
      e.discountRate = discountRate;
      e.credentialExpiration = credentialExpiration;
      e.accountExpiration = accountExpiration;
      e.recordingDate = recordingDate;
      e.roleNames = roleNames;
      e.agency = agency;
      return e;
   }
}