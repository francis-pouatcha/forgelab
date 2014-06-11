package org.adorsys.adpharma.client.jpa.prescriptionbook;

import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Prescriber_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PrescriptionBookPrescriber implements Association<PrescriptionBook, Prescriber>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty name;
   private SimpleStringProperty phone;
   private SimpleStringProperty street;
   private SimpleStringProperty city;

   public PrescriptionBookPrescriber()
   {
   }

   public PrescriptionBookPrescriber(Prescriber entity)
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

   public SimpleStringProperty streetProperty()
   {
      if (street == null)
      {
         street = new SimpleStringProperty();
      }
      return street;
   }

   public String getStreet()
   {
      return streetProperty().get();
   }

   public final void setStreet(String street)
   {
      this.streetProperty().set(street);
   }

   public SimpleStringProperty cityProperty()
   {
      if (city == null)
      {
         city = new SimpleStringProperty();
      }
      return city;
   }

   public String getCity()
   {
      return cityProperty().get();
   }

   public final void setCity(String city)
   {
      this.cityProperty().set(city);
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
   //		PrescriptionBookPrescriber other = (PrescriptionBookPrescriber) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "name");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      PrescriptionBookPrescriber a = new PrescriptionBookPrescriber();
      a.id = id;
      a.version = version;

      a.name = name;
      a.phone = phone;
      a.street = street;
      a.city = city;
      return a;
   }

}