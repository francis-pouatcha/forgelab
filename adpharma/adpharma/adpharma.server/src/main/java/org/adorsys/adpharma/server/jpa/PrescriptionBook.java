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
import org.adorsys.adpharma.server.jpa.Prescriber;
import javax.persistence.ManyToOne;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.list.ListField;
import javax.validation.constraints.NotNull;
import org.adorsys.adpharma.server.jpa.Hospital;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("PrescriptionBook_description")
@ListField({ "prescriber.name", "hospital.name", "agency.name", "recordingAgent.fullName",
      "prescriptionNumber", "salesOrder.soNumber", "prescriptionDate",
      "recordingDate" })
@ToStringField("prescriptionNumber")
public class PrescriptionBook implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @ManyToOne
   @Description("PrescriptionBook_prescriber_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Prescriber.class)
   @NotNull(message = "PrescriptionBook_prescriber_NotNull_validation")
   private Prescriber prescriber;

   @ManyToOne
   @Description("PrescriptionBook_hospital_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Hospital.class)
   @NotNull(message = "PrescriptionBook_hospital_NotNull_validation")
   private Hospital hospital;

   @ManyToOne
   @Description("PrescriptionBook_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   @NotNull(message = "PrescriptionBook_agency_NotNull_validation")
   private Agency agency;

   @ManyToOne
   @Description("PrescriptionBook_recordingAgent_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   @NotNull(message = "PrescriptionBook_recordingAgent_NotNull_validation")
   private Login recordingAgent;

   @Column
   @Description("PrescriptionBook_prescriptionNumber_description")
   private String prescriptionNumber;

   @ManyToOne
   @Description("PrescriptionBook_salesOrder_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = SalesOrder.class)
   private SalesOrder salesOrder;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("PrescriptionBook_prescriptionDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private Date prescriptionDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("PrescriptionBook_recordingDate_description")
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
         return id.equals(((PrescriptionBook) that).id);
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

   public Prescriber getPrescriber()
   {
      return this.prescriber;
   }

   public void setPrescriber(final Prescriber prescriber)
   {
      this.prescriber = prescriber;
   }

   public Hospital getHospital()
   {
      return this.hospital;
   }

   public void setHospital(final Hospital hospital)
   {
      this.hospital = hospital;
   }

   public Agency getAgency()
   {
      return this.agency;
   }

   public void setAgency(final Agency agency)
   {
      this.agency = agency;
   }

   public Login getRecordingAgent()
   {
      return this.recordingAgent;
   }

   public void setRecordingAgent(final Login recordingAgent)
   {
      this.recordingAgent = recordingAgent;
   }

   public String getPrescriptionNumber()
   {
      return this.prescriptionNumber;
   }

   public void setPrescriptionNumber(final String prescriptionNumber)
   {
      this.prescriptionNumber = prescriptionNumber;
   }

   public SalesOrder getSalesOrder()
   {
      return this.salesOrder;
   }

   public void setSalesOrder(final SalesOrder salesOrder)
   {
      this.salesOrder = salesOrder;
   }

   public Date getPrescriptionDate()
   {
      return this.prescriptionDate;
   }

   public void setPrescriptionDate(final Date prescriptionDate)
   {
      this.prescriptionDate = prescriptionDate;
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
      if (prescriptionNumber != null && !prescriptionNumber.trim().isEmpty())
         result += "prescriptionNumber: " + prescriptionNumber;
      return result;
   }
}