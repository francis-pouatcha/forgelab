package org.adorsys.adph.jpa;

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
import org.adorsys.adph.jpa.Client;
import javax.persistence.ManyToOne;

@Entity
@Description("org.adorsys.adph.jpa.Invoice.description")
public class Invoice implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.jpa.Invoice.invoiceNumber.description")
   private String invoiceNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.jpa.Invoice.creationDate.description")
   private Date creationDate;

   @ManyToOne
   @Description("org.adorsys.adph.jpa.Invoice.client.description")
   private Client client;

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
         return id.equals(((Invoice) that).id);
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

   public String getInvoiceNumber()
   {
      return this.invoiceNumber;
   }

   public void setInvoiceNumber(final String invoiceNumber)
   {
      this.invoiceNumber = invoiceNumber;
   }

   public Date getCreationDate()
   {
      return this.creationDate;
   }

   public void setCreationDate(final Date creationDate)
   {
      this.creationDate = creationDate;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (invoiceNumber != null && !invoiceNumber.trim().isEmpty())
         result += "invoiceNumber: " + invoiceNumber;
      return result;
   }

   public Client getClient()
   {
      return this.client;
   }

   public void setClient(final Client client)
   {
      this.client = client;
   }
}