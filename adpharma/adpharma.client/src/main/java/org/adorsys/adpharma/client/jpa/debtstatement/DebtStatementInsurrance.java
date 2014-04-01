package org.adorsys.adpharma.client.jpa.debtstatement;

import org.adorsys.adpharma.client.jpa.gender.Gender;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleBooleanProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.employer.Employer;
import org.adorsys.adpharma.client.jpa.customer.CustomerEmployer;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.customer.CustomerCustomerCategory;
import org.adorsys.adpharma.client.jpa.customertype.CustomerType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.adorsys.adpharma.client.jpa.customer.Customer;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Customer_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DebtStatementInsurrance implements Association<DebtStatement, Customer>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty fullName;
   private SimpleStringProperty landLinePhone;
   private SimpleStringProperty mobile;
   private SimpleStringProperty fax;
   private SimpleStringProperty email;
   private SimpleBooleanProperty creditAuthorized;
   private SimpleBooleanProperty discountAuthorized;
   private SimpleObjectProperty<Calendar> birthDate;

   public DebtStatementInsurrance()
   {
   }

   public DebtStatementInsurrance(Customer entity)
   {
      PropertyReader.copy(entity, this);
   }

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

   public SimpleStringProperty fullNameProperty()
   {
      if (fullName == null)
      {
         fullName = new SimpleStringProperty();
      }
      return fullName;
   }

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

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result
            + ((id == null) ? 0 : id.hashCode());
      return result;
   }

   //	@Override
   //	public boolean equals(Object obj) {
   //		if (this == obj)
   //			return true;
   //		if (obj == null)
   //			return false;
   //		if (getClass() != obj.getClass())
   //			return false;
   //		DebtStatementInsurrance other = (DebtStatementInsurrance) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "fullName");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      DebtStatementInsurrance a = new DebtStatementInsurrance();
      a.id = id;
      a.version = version;

      a.fullName = fullName;
      a.landLinePhone = landLinePhone;
      a.mobile = mobile;
      a.fax = fax;
      a.email = email;
      a.creditAuthorized = creditAuthorized;
      a.discountAuthorized = discountAuthorized;
      a.birthDate = birthDate;
      return a;
   }

}
