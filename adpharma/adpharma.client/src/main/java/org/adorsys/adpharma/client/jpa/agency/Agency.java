package org.adorsys.adpharma.client.jpa.agency;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.company.Company;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Agency_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "agencyNumber", "name" })
@ListField({ "agencyNumber", "name", "active", "name", "name", "phone", "fax" })
public class Agency
{

   private Long id;
   private int version;

   @Description("Agency_agencyNumber_description")
   private SimpleStringProperty agencyNumber;
   @Description("Agency_name_description")
   private SimpleStringProperty name;
   @Description("Agency_description_description")
   private SimpleStringProperty description;
   @Description("Agency_active_description")
   private SimpleBooleanProperty active;
   @Description("Agency_street_description")
   private SimpleStringProperty street;
   @Description("Agency_zipCode_description")
   private SimpleStringProperty zipCode;
   @Description("Agency_city_description")
   private SimpleStringProperty city;
   @Description("Agency_country_description")
   private SimpleStringProperty country;
   @Description("Agency_phone_description")
   private SimpleStringProperty phone;
   @Description("Agency_fax_description")
   private SimpleStringProperty fax;
   @Description("Agency_ticketMessage_description")
   private SimpleStringProperty ticketMessage;
   @Description("Agency_invoiceMessage_description")
   private SimpleStringProperty invoiceMessage;
   @Description("Agency_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordingDate;
   @Description("Agency_company_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Company.class)
   private SimpleObjectProperty<AgencyCompany> company;

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

   @NotNull(message = "Agency_name_NotNull_validation")
   public String getName()
   {
      return nameProperty().get();
   }

   public final void setName(String name)
   {
      this.nameProperty().set(name);
   }

   public SimpleStringProperty descriptionProperty()
   {
      if (description == null)
      {
         description = new SimpleStringProperty();
      }
      return description;
   }

   @Size(max = 256, message = "Agency_description_Size_validation")
   public String getDescription()
   {
      return descriptionProperty().get();
   }

   public final void setDescription(String description)
   {
      this.descriptionProperty().set(description);
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

   public SimpleStringProperty zipCodeProperty()
   {
      if (zipCode == null)
      {
         zipCode = new SimpleStringProperty();
      }
      return zipCode;
   }

   public String getZipCode()
   {
      return zipCodeProperty().get();
   }

   public final void setZipCode(String zipCode)
   {
      this.zipCodeProperty().set(zipCode);
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

   public SimpleStringProperty countryProperty()
   {
      if (country == null)
      {
         country = new SimpleStringProperty();
      }
      return country;
   }

   public String getCountry()
   {
      return countryProperty().get();
   }

   public final void setCountry(String country)
   {
      this.countryProperty().set(country);
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

   public SimpleStringProperty ticketMessageProperty()
   {
      if (ticketMessage == null)
      {
         ticketMessage = new SimpleStringProperty();
      }
      return ticketMessage;
   }

   @Size(max = 256, message = "Agency_ticketMessage_Size_validation")
   public String getTicketMessage()
   {
      return ticketMessageProperty().get();
   }

   public final void setTicketMessage(String ticketMessage)
   {
      this.ticketMessageProperty().set(ticketMessage);
   }

   public SimpleStringProperty invoiceMessageProperty()
   {
      if (invoiceMessage == null)
      {
         invoiceMessage = new SimpleStringProperty();
      }
      return invoiceMessage;
   }

   @Size(max = 256, message = "Agency_invoiceMessage_Size_validation")
   public String getInvoiceMessage()
   {
      return invoiceMessageProperty().get();
   }

   public final void setInvoiceMessage(String invoiceMessage)
   {
      this.invoiceMessageProperty().set(invoiceMessage);
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

   public SimpleObjectProperty<AgencyCompany> companyProperty()
   {
      if (company == null)
      {
         company = new SimpleObjectProperty<AgencyCompany>(new AgencyCompany());
      }
      return company;
   }

   public AgencyCompany getCompany()
   {
      return companyProperty().get();
   }

   public final void setCompany(AgencyCompany company)
   {
      if (company == null)
      {
         company = new AgencyCompany();
      }
      PropertyReader.copy(company, getCompany());
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
      Agency other = (Agency) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "agencyNumber", "name");
   }
}