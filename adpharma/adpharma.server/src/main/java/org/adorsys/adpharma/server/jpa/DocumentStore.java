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
import org.adorsys.adpharma.server.jpa.DocumentType;
import javax.persistence.Enumerated;
import org.adorsys.adpharma.server.jpa.Login;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("DocumentStore_description")
@ToStringField({ "documentNumber", "documentLocation" })
@ListField({ "documentNumber", "documentType", "documentLocation" })
public class DocumentStore implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("DocumentStore_documentNumber_description")
   private String documentNumber;

   @Column
   @Description("DocumentStore_documentType_description")
   @Enumerated
   private DocumentType documentType;

   @Column
   @Description("DocumentStore_documentLocation_description")
   private String documentLocation;

   @ManyToOne
   @Description("DocumentStore_recordingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private Login recordingUser;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("DocumentStore_modifiedDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date modifiedDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("DocumentStore_priodFrom_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date priodFrom;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("DocumentStore_periodTo_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date periodTo;

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
         return id.equals(((DocumentStore) that).id);
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

   public String getDocumentNumber()
   {
      return this.documentNumber;
   }

   public void setDocumentNumber(final String documentNumber)
   {
      this.documentNumber = documentNumber;
   }

   public DocumentType getDocumentType()
   {
      return this.documentType;
   }

   public void setDocumentType(final DocumentType documentType)
   {
      this.documentType = documentType;
   }

   public String getDocumentLocation()
   {
      return this.documentLocation;
   }

   public void setDocumentLocation(final String documentLocation)
   {
      this.documentLocation = documentLocation;
   }

   public Login getRecordingUser()
   {
      return this.recordingUser;
   }

   public void setRecordingUser(final Login recordingUser)
   {
      this.recordingUser = recordingUser;
   }

   public Date getModifiedDate()
   {
      return this.modifiedDate;
   }

   public void setModifiedDate(final Date modifiedDate)
   {
      this.modifiedDate = modifiedDate;
   }

   public Date getPriodFrom()
   {
      return this.priodFrom;
   }

   public void setPriodFrom(final Date priodFrom)
   {
      this.priodFrom = priodFrom;
   }

   public Date getPeriodTo()
   {
      return this.periodTo;
   }

   public void setPeriodTo(final Date periodTo)
   {
      this.periodTo = periodTo;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (documentNumber != null && !documentNumber.trim().isEmpty())
         result += "documentNumber: " + documentNumber;
      if (documentLocation != null && !documentLocation.trim().isEmpty())
         result += ", documentLocation: " + documentLocation;
      return result;
   }
}