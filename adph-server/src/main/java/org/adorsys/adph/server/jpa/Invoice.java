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
import org.adorsys.adph.server.jpa.Client;
import javax.persistence.ManyToOne;
import org.adorsys.adph.server.jpa.CashDrawer;
import org.adorsys.adph.server.jpa.PharmaUser;
import org.adorsys.adph.server.jpa.Site;
import org.adorsys.adph.server.jpa.SalesOrder;
import java.math.BigDecimal;
import org.adorsys.adph.server.jpa.InvoiceItem;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import org.adorsys.adph.server.jpa.InvoiceType;
import javax.persistence.Enumerated;
import org.adorsys.adph.server.jpa.Payment;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.Invoice.description")
@Audited
@XmlRootElement
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
   @Description("org.adorsys.adph.server.jpa.Invoice.invoiceNumber.description")
   private String invoiceNumber;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.Invoice.creationDate.description")
   private Date creationDate;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Invoice.client.description")
   private Client client;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Invoice.cashDrawer.description")
   private CashDrawer cashDrawer;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Invoice.salesStaff.description")
   private PharmaUser salesStaff;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.Invoice.site.description")
   private Site site;

   @ManyToOne
   private SalesOrder salesOrder;

   @Column
   @Description("org.adorsys.adph.server.jpa.Invoice.totalSalesPrice.description")
   private BigDecimal totalSalesPrice;

   @Column
   @Description("org.adorsys.adph.server.jpa.Invoice.discountAmount.description")
   private BigDecimal discountAmount;

   @Column
   @Description("org.adorsys.adph.server.jpa.Invoice.netToPay.description")
   private BigDecimal netToPay;

   @Column
   @Description("org.adorsys.adph.server.jpa.Invoice.settled.description")
   private boolean settled;

   @Column
   @Description("org.adorsys.adph.server.jpa.Invoice.cashed.description")
   private boolean cashed;

   @Column
   @Description("org.adorsys.adph.server.jpa.Invoice.advancePayment.description")
   private BigDecimal advancePayment;

   @Column
   @Description("org.adorsys.adph.server.jpa.Invoice.restToPay.description")
   private BigDecimal restToPay;

   @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   @Description("org.adorsys.adph.server.jpa.Invoice.invoiceItems.description")
   private Set<InvoiceItem> invoiceItems = new HashSet<InvoiceItem>();

   @Column
   @Description("org.adorsys.adph.server.jpa.Invoice.invoiceType.description")
   @Enumerated
   private InvoiceType invoiceType;

   @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
   @Description("org.adorsys.adph.server.jpa.Invoice.payments.description")
   private Set<Payment> payments = new HashSet<Payment>();

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

   public Client getClient()
   {
      return this.client;
   }

   public void setClient(final Client client)
   {
      this.client = client;
   }

   public CashDrawer getCashDrawer()
   {
      return this.cashDrawer;
   }

   public void setCashDrawer(final CashDrawer cashDrawer)
   {
      this.cashDrawer = cashDrawer;
   }

   public PharmaUser getSalesStaff()
   {
      return this.salesStaff;
   }

   public void setSalesStaff(final PharmaUser salesStaff)
   {
      this.salesStaff = salesStaff;
   }

   public Site getSite()
   {
      return this.site;
   }

   public void setSite(final Site site)
   {
      this.site = site;
   }

   public SalesOrder getSalesOrder()
   {
      return this.salesOrder;
   }

   public void setSalesOrder(final SalesOrder salesOrder)
   {
      this.salesOrder = salesOrder;
   }

   public BigDecimal getTotalSalesPrice()
   {
      return this.totalSalesPrice;
   }

   public void setTotalSalesPrice(final BigDecimal totalSalesPrice)
   {
      this.totalSalesPrice = totalSalesPrice;
   }

   public BigDecimal getDiscountAmount()
   {
      return this.discountAmount;
   }

   public void setDiscountAmount(final BigDecimal discountAmount)
   {
      this.discountAmount = discountAmount;
   }

   public BigDecimal getNetToPay()
   {
      return this.netToPay;
   }

   public void setNetToPay(final BigDecimal netToPay)
   {
      this.netToPay = netToPay;
   }

   public boolean getSettled()
   {
      return this.settled;
   }

   public void setSettled(final boolean settled)
   {
      this.settled = settled;
   }

   public boolean getCashed()
   {
      return this.cashed;
   }

   public void setCashed(final boolean cashed)
   {
      this.cashed = cashed;
   }

   public BigDecimal getAdvancePayment()
   {
      return this.advancePayment;
   }

   public void setAdvancePayment(final BigDecimal advancePayment)
   {
      this.advancePayment = advancePayment;
   }

   public BigDecimal getRestToPay()
   {
      return this.restToPay;
   }

   public void setRestToPay(final BigDecimal restToPay)
   {
      this.restToPay = restToPay;
   }

   public Set<InvoiceItem> getInvoiceItems()
   {
      return this.invoiceItems;
   }

   public void setInvoiceItems(final Set<InvoiceItem> invoiceItems)
   {
      this.invoiceItems = invoiceItems;
   }

   public InvoiceType getInvoiceType()
   {
      return this.invoiceType;
   }

   public void setInvoiceType(final InvoiceType invoiceType)
   {
      this.invoiceType = invoiceType;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (invoiceNumber != null && !invoiceNumber.trim().isEmpty())
         result += "invoiceNumber: " + invoiceNumber;
      result += ", settled: " + settled;
      result += ", cashed: " + cashed;
      return result;
   }

   public Set<Payment> getPayments()
   {
      return this.payments;
   }

   public void setPayments(final Set<Payment> payments)
   {
      this.payments = payments;
   }
}