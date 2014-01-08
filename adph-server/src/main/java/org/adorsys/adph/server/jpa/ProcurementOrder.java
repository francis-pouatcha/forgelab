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
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.ProcurementOrder.description")
@Audited
@XmlRootElement
public class ProcurementOrder implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.ProcurementOrder.procurementNumber.description")
   private String procurementNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.ProcurementOrder.submissionDate.description")
   private Date submissionDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.ProcurementOrder.processingDate.description")
   private Date processingDate;

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
         return id.equals(((ProcurementOrder) that).id);
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

   public String getProcurementNumber()
   {
      return this.procurementNumber;
   }

   public void setProcurementNumber(final String procurementNumber)
   {
      this.procurementNumber = procurementNumber;
   }

   public Date getSubmissionDate()
   {
      return this.submissionDate;
   }

   public void setSubmissionDate(final Date submissionDate)
   {
      this.submissionDate = submissionDate;
   }

   public Date getProcessingDate()
   {
      return this.processingDate;
   }

   public void setProcessingDate(final Date processingDate)
   {
      this.processingDate = processingDate;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (procurementNumber != null && !procurementNumber.trim().isEmpty())
         result += "procurementNumber: " + procurementNumber;
      return result;
   }
}