package org.adorsys.adph.server.jpa;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import org.adorsys.javaext.description.Description;
import javax.validation.constraints.NotNull;
import org.adorsys.adph.server.jpa.PharmaUser;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.adorsys.adph.server.jpa.SalesOrder;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.PrescriptionBook.description")
@Audited
@XmlRootElement
public class PrescriptionBook implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.PrescriptionBook.prescriber.description")
   @NotNull
   private String prescriber;

   @Column
   @Description("org.adorsys.adph.server.jpa.PrescriptionBook.hospital.description")
   @NotNull
   private String hospital;

   @Column
   @Description("org.adorsys.adph.server.jpa.PrescriptionBook.patientName.description")
   private String patientName;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.PrescriptionBook.recordingAgent.description")
   private PharmaUser recordingAgent;

   @Column
   @Description("org.adorsys.adph.server.jpa.PrescriptionBook.prescriptionNumber.description")
   private String prescriptionNumber;

   @OneToOne
   @NotNull
   @Description("org.adorsys.adph.server.jpa.PrescriptionBook.salesOrder.description")
   private SalesOrder salesOrder;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.PrescriptionBook.prescriptionDate.description")
   private Date prescriptionDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.PrescriptionBook.recordingDate.description")
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

   public String getPrescriber()
   {
      return this.prescriber;
   }

   public void setPrescriber(final String prescriber)
   {
      this.prescriber = prescriber;
   }

   public String getHospital()
   {
      return this.hospital;
   }

   public void setHospital(final String hospital)
   {
      this.hospital = hospital;
   }

   public String getPatientName()
   {
      return this.patientName;
   }

   public void setPatientName(final String patientName)
   {
      this.patientName = patientName;
   }

   public PharmaUser getRecordingAgent()
   {
      return this.recordingAgent;
   }

   public void setRecordingAgent(final PharmaUser recordingAgent)
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
      if (prescriber != null && !prescriber.trim().isEmpty())
         result += "prescriber: " + prescriber;
      if (hospital != null && !hospital.trim().isEmpty())
         result += ", hospital: " + hospital;
      if (patientName != null && !patientName.trim().isEmpty())
         result += ", patientName: " + patientName;
      if (prescriptionNumber != null && !prescriptionNumber.trim().isEmpty())
         result += ", prescriptionNumber: " + prescriptionNumber;
      return result;
   }
}