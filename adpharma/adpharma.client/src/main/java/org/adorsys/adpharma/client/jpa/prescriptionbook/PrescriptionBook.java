package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.hospital.Hospital;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
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
@Description("PrescriptionBook_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "prescriber.name", "hospital.name", "agency.name",
      "recordingAgent.fullName", "prescriptionNumber", "salesOrder.soNumber",
      "prescriptionDate", "recordingDate" })
@ToStringField("prescriptionNumber")
public class PrescriptionBook implements Cloneable
{

   private Long id;
   private int version;

   @Description("PrescriptionBook_prescriptionNumber_description")
   private SimpleStringProperty prescriptionNumber;
   @Description("PrescriptionBook_prescriptionDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> prescriptionDate;
   @Description("PrescriptionBook_recordingDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> recordingDate;
   @Description("PrescriptionBook_prescriber_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Prescriber.class)
   private SimpleObjectProperty<PrescriptionBookPrescriber> prescriber;
   @Description("PrescriptionBook_hospital_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Hospital.class)
   private SimpleObjectProperty<PrescriptionBookHospital> hospital;
   @Description("PrescriptionBook_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<PrescriptionBookAgency> agency;
   @Description("PrescriptionBook_recordingAgent_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<PrescriptionBookRecordingAgent> recordingAgent;
   @Description("PrescriptionBook_salesOrder_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = SalesOrder.class)
   private SimpleObjectProperty<PrescriptionBookSalesOrder> salesOrder;

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

   public SimpleStringProperty prescriptionNumberProperty()
   {
      if (prescriptionNumber == null)
      {
         prescriptionNumber = new SimpleStringProperty();
      }
      return prescriptionNumber;
   }

   public String getPrescriptionNumber()
   {
      return prescriptionNumberProperty().get();
   }

   public final void setPrescriptionNumber(String prescriptionNumber)
   {
      this.prescriptionNumberProperty().set(prescriptionNumber);
   }

   public SimpleObjectProperty<Calendar> prescriptionDateProperty()
   {
      if (prescriptionDate == null)
      {
         prescriptionDate = new SimpleObjectProperty<Calendar>();
      }
      return prescriptionDate;
   }

   public Calendar getPrescriptionDate()
   {
      return prescriptionDateProperty().get();
   }

   public final void setPrescriptionDate(Calendar prescriptionDate)
   {
      this.prescriptionDateProperty().set(prescriptionDate);
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

   public SimpleObjectProperty<PrescriptionBookPrescriber> prescriberProperty()
   {
      if (prescriber == null)
      {
         prescriber = new SimpleObjectProperty<PrescriptionBookPrescriber>(new PrescriptionBookPrescriber());
      }
      return prescriber;
   }

   @NotNull(message = "PrescriptionBook_prescriber_NotNull_validation")
   public PrescriptionBookPrescriber getPrescriber()
   {
      return prescriberProperty().get();
   }

   public final void setPrescriber(PrescriptionBookPrescriber prescriber)
   {
      if (prescriber == null)
      {
         prescriber = new PrescriptionBookPrescriber();
      }
      PropertyReader.copy(prescriber, getPrescriber());
      prescriberProperty().setValue(ObjectUtils.clone(getPrescriber()));
   }

   public SimpleObjectProperty<PrescriptionBookHospital> hospitalProperty()
   {
      if (hospital == null)
      {
         hospital = new SimpleObjectProperty<PrescriptionBookHospital>(new PrescriptionBookHospital());
      }
      return hospital;
   }

   @NotNull(message = "PrescriptionBook_hospital_NotNull_validation")
   public PrescriptionBookHospital getHospital()
   {
      return hospitalProperty().get();
   }

   public final void setHospital(PrescriptionBookHospital hospital)
   {
      if (hospital == null)
      {
         hospital = new PrescriptionBookHospital();
      }
      PropertyReader.copy(hospital, getHospital());
      hospitalProperty().setValue(ObjectUtils.clone(getHospital()));
   }

   public SimpleObjectProperty<PrescriptionBookAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<PrescriptionBookAgency>(new PrescriptionBookAgency());
      }
      return agency;
   }

   @NotNull(message = "PrescriptionBook_agency_NotNull_validation")
   public PrescriptionBookAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(PrescriptionBookAgency agency)
   {
      if (agency == null)
      {
         agency = new PrescriptionBookAgency();
      }
      PropertyReader.copy(agency, getAgency());
      agencyProperty().setValue(ObjectUtils.clone(getAgency()));
   }

   public SimpleObjectProperty<PrescriptionBookRecordingAgent> recordingAgentProperty()
   {
      if (recordingAgent == null)
      {
         recordingAgent = new SimpleObjectProperty<PrescriptionBookRecordingAgent>(new PrescriptionBookRecordingAgent());
      }
      return recordingAgent;
   }

   @NotNull(message = "PrescriptionBook_recordingAgent_NotNull_validation")
   public PrescriptionBookRecordingAgent getRecordingAgent()
   {
      return recordingAgentProperty().get();
   }

   public final void setRecordingAgent(PrescriptionBookRecordingAgent recordingAgent)
   {
      if (recordingAgent == null)
      {
         recordingAgent = new PrescriptionBookRecordingAgent();
      }
      PropertyReader.copy(recordingAgent, getRecordingAgent());
      recordingAgentProperty().setValue(ObjectUtils.clone(getRecordingAgent()));
   }

   public SimpleObjectProperty<PrescriptionBookSalesOrder> salesOrderProperty()
   {
      if (salesOrder == null)
      {
         salesOrder = new SimpleObjectProperty<PrescriptionBookSalesOrder>(new PrescriptionBookSalesOrder());
      }
      return salesOrder;
   }

   public PrescriptionBookSalesOrder getSalesOrder()
   {
      return salesOrderProperty().get();
   }

   public final void setSalesOrder(PrescriptionBookSalesOrder salesOrder)
   {
      if (salesOrder == null)
      {
         salesOrder = new PrescriptionBookSalesOrder();
      }
      PropertyReader.copy(salesOrder, getSalesOrder());
      salesOrderProperty().setValue(ObjectUtils.clone(getSalesOrder()));
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
      PrescriptionBook other = (PrescriptionBook) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "prescriptionNumber");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      PrescriptionBook e = new PrescriptionBook();
      e.id = id;
      e.version = version;

      e.prescriptionNumber = prescriptionNumber;
      e.prescriptionDate = prescriptionDate;
      e.recordingDate = recordingDate;
      e.prescriber = prescriber;
      e.hospital = hospital;
      e.agency = agency;
      e.recordingAgent = recordingAgent;
      e.salesOrder = salesOrder;
      return e;
   }
}