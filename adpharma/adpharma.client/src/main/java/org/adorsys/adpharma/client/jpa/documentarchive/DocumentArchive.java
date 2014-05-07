package org.adorsys.adpharma.client.jpa.documentarchive;

import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.documenttype.DocumentType;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("DocumentArchive_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField({ "documentNumber", "documentLocation" })
@ListField({ "documentNumber", "documentType", "documentLocation" })
public class DocumentArchive implements Cloneable
{

   private Long id;
   private int version;

   @Description("DocumentArchive_documentNumber_description")
   private SimpleStringProperty documentNumber;
   @Description("DocumentArchive_documentLocation_description")
   private SimpleStringProperty documentLocation;
   @Description("DocumentArchive_documentType_description")
   private SimpleObjectProperty<DocumentType> documentType;
   @Description("DocumentArchive_modifiedDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> modifiedDate;
   @Description("DocumentArchive_priodFrom_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> priodFrom;
   @Description("DocumentArchive_periodTo_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> periodTo;
   @Description("DocumentArchive_recordingUser_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<DocumentArchiveRecordingUser> recordingUser;

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

   public SimpleStringProperty documentNumberProperty()
   {
      if (documentNumber == null)
      {
         documentNumber = new SimpleStringProperty();
      }
      return documentNumber;
   }

   public String getDocumentNumber()
   {
      return documentNumberProperty().get();
   }

   public final void setDocumentNumber(String documentNumber)
   {
      this.documentNumberProperty().set(documentNumber);
   }

   public SimpleStringProperty documentLocationProperty()
   {
      if (documentLocation == null)
      {
         documentLocation = new SimpleStringProperty();
      }
      return documentLocation;
   }

   public String getDocumentLocation()
   {
      return documentLocationProperty().get();
   }

   public final void setDocumentLocation(String documentLocation)
   {
      this.documentLocationProperty().set(documentLocation);
   }

   public SimpleObjectProperty<DocumentType> documentTypeProperty()
   {
      if (documentType == null)
      {
         documentType = new SimpleObjectProperty<DocumentType>();
      }
      return documentType;
   }

   public DocumentType getDocumentType()
   {
      return documentTypeProperty().get();
   }

   public final void setDocumentType(DocumentType documentType)
   {
      this.documentTypeProperty().set(documentType);
   }

   public SimpleObjectProperty<Calendar> modifiedDateProperty()
   {
      if (modifiedDate == null)
      {
         modifiedDate = new SimpleObjectProperty<Calendar>();
      }
      return modifiedDate;
   }

   public Calendar getModifiedDate()
   {
      return modifiedDateProperty().get();
   }

   public final void setModifiedDate(Calendar modifiedDate)
   {
      this.modifiedDateProperty().set(modifiedDate);
   }

   public SimpleObjectProperty<Calendar> priodFromProperty()
   {
      if (priodFrom == null)
      {
         priodFrom = new SimpleObjectProperty<Calendar>();
      }
      return priodFrom;
   }

   public Calendar getPriodFrom()
   {
      return priodFromProperty().get();
   }

   public final void setPriodFrom(Calendar priodFrom)
   {
      this.priodFromProperty().set(priodFrom);
   }

   public SimpleObjectProperty<Calendar> periodToProperty()
   {
      if (periodTo == null)
      {
         periodTo = new SimpleObjectProperty<Calendar>();
      }
      return periodTo;
   }

   public Calendar getPeriodTo()
   {
      return periodToProperty().get();
   }

   public final void setPeriodTo(Calendar periodTo)
   {
      this.periodToProperty().set(periodTo);
   }

   public SimpleObjectProperty<DocumentArchiveRecordingUser> recordingUserProperty()
   {
      if (recordingUser == null)
      {
         recordingUser = new SimpleObjectProperty<DocumentArchiveRecordingUser>(new DocumentArchiveRecordingUser());
      }
      return recordingUser;
   }

   public DocumentArchiveRecordingUser getRecordingUser()
   {
      return recordingUserProperty().get();
   }

   public final void setRecordingUser(DocumentArchiveRecordingUser recordingUser)
   {
      if (recordingUser == null)
      {
         recordingUser = new DocumentArchiveRecordingUser();
      }
      PropertyReader.copy(recordingUser, getRecordingUser());
      recordingUserProperty().setValue(ObjectUtils.clone(getRecordingUser()));
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
      DocumentArchive other = (DocumentArchive) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "documentNumber", "documentLocation");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      DocumentArchive e = new DocumentArchive();
      e.id = id;
      e.version = version;

      e.documentNumber = documentNumber;
      e.documentLocation = documentLocation;
      e.documentType = documentType;
      e.modifiedDate = modifiedDate;
      e.priodFrom = priodFrom;
      e.periodTo = periodTo;
      e.recordingUser = recordingUser;
      return e;
   }
}