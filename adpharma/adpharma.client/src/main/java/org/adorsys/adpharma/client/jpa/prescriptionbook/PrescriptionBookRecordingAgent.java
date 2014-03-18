package org.adorsys.adpharma.client.jpa.prescriptionbook;

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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.adorsys.adpharma.client.jpa.login.Login;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Login_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrescriptionBookRecordingAgent implements Association<PrescriptionBook, Login>
{

   private Long id;
   private int version;

   private SimpleStringProperty loginName;
   private SimpleStringProperty email;
   private SimpleObjectProperty<Gender> gender;

   public PrescriptionBookRecordingAgent()
   {
   }

   public PrescriptionBookRecordingAgent(Login entity)
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

   public SimpleStringProperty loginNameProperty()
   {
      if (loginName == null)
      {
         loginName = new SimpleStringProperty();
      }
      return loginName;
   }

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
      PrescriptionBookRecordingAgent other = (PrescriptionBookRecordingAgent) obj;
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
}