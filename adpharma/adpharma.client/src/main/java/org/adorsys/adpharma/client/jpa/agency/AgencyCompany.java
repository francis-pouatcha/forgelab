package org.adorsys.adpharma.client.jpa.agency;

import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.adorsys.adpharma.client.jpa.company.Company;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Company_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgencyCompany implements Association<Agency, Company>
{

   private Long id;
   private int version;

   private SimpleStringProperty displayName;
   private SimpleStringProperty phone;
   private SimpleStringProperty fax;
   private SimpleStringProperty siteManager;
   private SimpleStringProperty email;

   public AgencyCompany()
   {
   }

   public AgencyCompany(Company entity)
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

   public SimpleStringProperty displayNameProperty()
   {
      if (displayName == null)
      {
         displayName = new SimpleStringProperty();
      }
      return displayName;
   }

   public String getDisplayName()
   {
      return displayNameProperty().get();
   }

   public final void setDisplayName(String displayName)
   {
      this.displayNameProperty().set(displayName);
   }

   public SimpleStringProperty phoneProperty()
   {
      if (phone == null)
      {
         phone = new SimpleStringProperty();
      }
      return phone;
   }

   public String getPhone()
   {
      return phoneProperty().get();
   }

   public final void setPhone(String phone)
   {
      this.phoneProperty().set(phone);
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

   public SimpleStringProperty siteManagerProperty()
   {
      if (siteManager == null)
      {
         siteManager = new SimpleStringProperty();
      }
      return siteManager;
   }

   public String getSiteManager()
   {
      return siteManagerProperty().get();
   }

   public final void setSiteManager(String siteManager)
   {
      this.siteManagerProperty().set(siteManager);
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
      AgencyCompany other = (AgencyCompany) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "displayName");
   }
}