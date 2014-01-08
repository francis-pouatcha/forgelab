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
import org.adorsys.adph.server.jpa.PharmaUser;
import javax.persistence.ManyToOne;
import org.adorsys.adph.server.jpa.Site;
import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import org.hibernate.envers.Audited;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Description("org.adorsys.adph.server.jpa.CashDrawer.description")
@Audited
@XmlRootElement
public class CashDrawer implements Serializable
{

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.cashDrawerNumber.description")
   private String cashDrawerNumber;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.CashDrawer.cashier.description")
   private PharmaUser cashier;

   @ManyToOne
   @Description("org.adorsys.adph.server.jpa.CashDrawer.closedBy.description")
   private PharmaUser closedBy;

   @ManyToOne
   @NotNull
   @Description("org.adorsys.adph.server.jpa.CashDrawer.site.description")
   private Site site;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.CashDrawer.openingDate.description")
   private Date openingDate;

   @Temporal(TemporalType.TIMESTAMP)
   @Description("org.adorsys.adph.server.jpa.CashDrawer.closingDate.description")
   private Date closingDate;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.initialAmount.description")
   private BigDecimal initialAmount;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.opened.description")
   private boolean opened;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.totalCashIn.description")
   private BigDecimal totalCashIn;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.totalCashOut.description")
   private BigDecimal totalCashOut;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.totalCash.description")
   private BigDecimal totalCash;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.totalCreditSales.description")
   private BigDecimal totalCreditSales;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.totalCheck.description")
   private BigDecimal totalCheck;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.totalCreditCard.description")
   private BigDecimal totalCreditCard;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.totalCompanyVoucher.description")
   private BigDecimal totalCompanyVoucher;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.totalClientVoucher.description")
   private BigDecimal totalClientVoucher;

   @Column
   @Description("org.adorsys.adph.server.jpa.CashDrawer.totalCashDebt.description")
   private BigDecimal totalCashDebt;

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
         return id.equals(((CashDrawer) that).id);
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

   public String getCashDrawerNumber()
   {
      return this.cashDrawerNumber;
   }

   public void setCashDrawerNumber(final String cashDrawerNumber)
   {
      this.cashDrawerNumber = cashDrawerNumber;
   }

   public PharmaUser getCashier()
   {
      return this.cashier;
   }

   public void setCashier(final PharmaUser cashier)
   {
      this.cashier = cashier;
   }

   public PharmaUser getClosedBy()
   {
      return this.closedBy;
   }

   public void setClosedBy(final PharmaUser closedBy)
   {
      this.closedBy = closedBy;
   }

   public Site getSite()
   {
      return this.site;
   }

   public void setSite(final Site site)
   {
      this.site = site;
   }

   public Date getOpeningDate()
   {
      return this.openingDate;
   }

   public void setOpeningDate(final Date openingDate)
   {
      this.openingDate = openingDate;
   }

   public Date getClosingDate()
   {
      return this.closingDate;
   }

   public void setClosingDate(final Date closingDate)
   {
      this.closingDate = closingDate;
   }

   public BigDecimal getInitialAmount()
   {
      return this.initialAmount;
   }

   public void setInitialAmount(final BigDecimal initialAmount)
   {
      this.initialAmount = initialAmount;
   }

   public boolean getOpened()
   {
      return this.opened;
   }

   public void setOpened(final boolean opened)
   {
      this.opened = opened;
   }

   public BigDecimal getTotalCashIn()
   {
      return this.totalCashIn;
   }

   public void setTotalCashIn(final BigDecimal totalCashIn)
   {
      this.totalCashIn = totalCashIn;
   }

   public BigDecimal getTotalCashOut()
   {
      return this.totalCashOut;
   }

   public void setTotalCashOut(final BigDecimal totalCashOut)
   {
      this.totalCashOut = totalCashOut;
   }

   public BigDecimal getTotalCash()
   {
      return this.totalCash;
   }

   public void setTotalCash(final BigDecimal totalCash)
   {
      this.totalCash = totalCash;
   }

   public BigDecimal getTotalCreditSales()
   {
      return this.totalCreditSales;
   }

   public void setTotalCreditSales(final BigDecimal totalCreditSales)
   {
      this.totalCreditSales = totalCreditSales;
   }

   public BigDecimal getTotalCheck()
   {
      return this.totalCheck;
   }

   public void setTotalCheck(final BigDecimal totalCheck)
   {
      this.totalCheck = totalCheck;
   }

   public BigDecimal getTotalCreditCard()
   {
      return this.totalCreditCard;
   }

   public void setTotalCreditCard(final BigDecimal totalCreditCard)
   {
      this.totalCreditCard = totalCreditCard;
   }

   public BigDecimal getTotalCompanyVoucher()
   {
      return this.totalCompanyVoucher;
   }

   public void setTotalCompanyVoucher(final BigDecimal totalCompanyVoucher)
   {
      this.totalCompanyVoucher = totalCompanyVoucher;
   }

   public BigDecimal getTotalClientVoucher()
   {
      return this.totalClientVoucher;
   }

   public void setTotalClientVoucher(final BigDecimal totalClientVoucher)
   {
      this.totalClientVoucher = totalClientVoucher;
   }

   public BigDecimal getTotalCashDebt()
   {
      return this.totalCashDebt;
   }

   public void setTotalCashDebt(final BigDecimal totalCashDebt)
   {
      this.totalCashDebt = totalCashDebt;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (cashDrawerNumber != null && !cashDrawerNumber.trim().isEmpty())
         result += "cashDrawerNumber: " + cashDrawerNumber;
      result += ", opened: " + opened;
      return result;
   }
}