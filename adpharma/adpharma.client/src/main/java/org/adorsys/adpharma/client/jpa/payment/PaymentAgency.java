package org.adorsys.adpharma.client.jpa.payment;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.agency.AgencyCompany;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.adorsys.adpharma.client.jpa.agency.Agency;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Agency_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentAgency implements Association<Payment, Agency>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty agencyNumber;
   private SimpleStringProperty name;
   private SimpleBooleanProperty active;
   private SimpleStringProperty phone;
   private SimpleStringProperty fax;

   public PaymentAgency()
   {
   }

   public PaymentAgency(Agency entity)
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

   public SimpleStringProperty agencyNumberProperty()
   {
      if (agencyNumber == null)
      {
         agencyNumber = new SimpleStringProperty();
      }
      return agencyNumber;
   }

   public String getAgencyNumber()
   {
      return agencyNumberProperty().get();
   }

   public final void setAgencyNumber(String agencyNumber)
   {
      this.agencyNumberProperty().set(agencyNumber);
   }

   public SimpleStringProperty nameProperty()
   {
      if (name == null)
      {
         name = new SimpleStringProperty();
      }
      return name;
   }

   public String getName()
   {
      return nameProperty().get();
   }

   public final void setName(String name)
   {
      this.nameProperty().set(name);
   }

   public SimpleBooleanProperty activeProperty()
   {
      if (active == null)
      {
         active = new SimpleBooleanProperty();
      }
      return active;
   }

   public Boolean getActive()
   {
      return activeProperty().get();
   }

   public final void setActive(Boolean active)
   {
      if (active == null)
         active = Boolean.FALSE;
      this.activeProperty().set(active);
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
   //		PaymentAgency other = (PaymentAgency) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "agencyNumber", "name");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      PaymentAgency a = new PaymentAgency();
      a.id = id;
      a.version = version;

      a.agencyNumber = agencyNumber;
      a.name = name;
      a.active = active;
      a.phone = phone;
      a.fax = fax;
      return a;
   }

}
