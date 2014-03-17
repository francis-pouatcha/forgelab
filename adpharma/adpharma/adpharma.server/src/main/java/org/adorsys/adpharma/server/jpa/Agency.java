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
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;
import javax.validation.constraints.Size;
import org.adorsys.adpharma.server.jpa.Company;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("Agency_description")
@ToStringField({ "agencyNumber", "name" })
@ListField({ "agencyNumber", "name", "active", "name", "name", "phone", "fax" })
public class Agency implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("Agency_agencyNumber_description")
   private String agencyNumber;

   @Column
   @Description("Agency_name_description")
   private String name;

   @Column
   @Description("Agency_description_description")
   @Size(max = 256, message = "Agency_description_Size_validation")
   private String description;

   @Column
   @Description("Agency_active_description")
   private Boolean active;

   @Column
   @Description("Agency_street_description")
   private String street;

   @Column
   @Description("Agency_zipCode_description")
   private String zipCode;

   @Column
   @Description("Agency_city_description")
   private String city;

   @Column
   @Description("Agency_country_description")
   private String country;

   @Column
   @Description("Agency_phone_description")
   private String phone;

   @Column
   @Description("Agency_fax_description")
   private String fax;

   @ManyToOne
   @Description("Agency_company_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Company.class)
   private Company company;

   @Column
   @Description("Agency_ticketMessage_description")
   @Size(max = 256, message = "Agency_ticketMessage_Size_validation")
   private String ticketMessage;

   @Column
   @Description("Agency_invoiceMessage_description")
   @Size(max = 256, message = "Agency_invoiceMessage_Size_validation")
   private String invoiceMessage;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("Agency_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date recordingDate;

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
         return id.equals(((Agency) that).id);
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

   public String getAgencyNumber()
   {
      return this.agencyNumber;
   }

   public void setAgencyNumber(final String agencyNumber)
   {
      this.agencyNumber = agencyNumber;
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(final String name)
   {
      this.name = name;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
   }

   public Boolean getActive()
   {
      return this.active;
   }

   public void setActive(final Boolean active)
   {
      this.active = active;
   }

   public String getStreet()
   {
      return this.street;
   }

   public void setStreet(final String street)
   {
      this.street = street;
   }

   public String getZipCode()
   {
      return this.zipCode;
   }

   public void setZipCode(final String zipCode)
   {
      this.zipCode = zipCode;
   }

   public String getCity()
   {
      return this.city;
   }

   public void setCity(final String city)
   {
      this.city = city;
   }

   public String getCountry()
   {
      return this.country;
   }

   public void setCountry(final String country)
   {
      this.country = country;
   }

   public String getPhone()
   {
      return this.phone;
   }

   public void setPhone(final String phone)
   {
      this.phone = phone;
   }

   public String getFax()
   {
      return this.fax;
   }

   public void setFax(final String fax)
   {
      this.fax = fax;
   }

   public Company getCompany()
   {
      return this.company;
   }

   public void setCompany(final Company company)
   {
      this.company = company;
   }

   public String getTicketMessage()
   {
      return this.ticketMessage;
   }

   public void setTicketMessage(final String ticketMessage)
   {
      this.ticketMessage = ticketMessage;
   }

   public String getInvoiceMessage()
   {
      return this.invoiceMessage;
   }

   public void setInvoiceMessage(final String invoiceMessage)
   {
      this.invoiceMessage = invoiceMessage;
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
      if (agencyNumber != null && !agencyNumber.trim().isEmpty())
         result += "agencyNumber: " + agencyNumber;
      if (name != null && !name.trim().isEmpty())
         result += ", name: " + name;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      if (active != null)
         result += ", active: " + active;
      if (street != null && !street.trim().isEmpty())
         result += ", street: " + street;
      if (zipCode != null && !zipCode.trim().isEmpty())
         result += ", zipCode: " + zipCode;
      if (city != null && !city.trim().isEmpty())
         result += ", city: " + city;
      if (country != null && !country.trim().isEmpty())
         result += ", country: " + country;
      if (phone != null && !phone.trim().isEmpty())
         result += ", phone: " + phone;
      if (fax != null && !fax.trim().isEmpty())
         result += ", fax: " + fax;
      if (ticketMessage != null && !ticketMessage.trim().isEmpty())
         result += ", ticketMessage: " + ticketMessage;
      if (invoiceMessage != null && !invoiceMessage.trim().isEmpty())
         result += ", invoiceMessage: " + invoiceMessage;
      return result;
   }
}