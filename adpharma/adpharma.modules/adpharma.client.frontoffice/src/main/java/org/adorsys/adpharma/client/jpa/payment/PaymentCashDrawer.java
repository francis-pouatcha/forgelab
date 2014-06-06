package org.adorsys.adpharma.client.jpa.payment;

import java.math.BigDecimal;
import java.util.Calendar;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("CashDrawer_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentCashDrawer implements Association<Payment, CashDrawer>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty cashDrawerNumber;
   private SimpleBooleanProperty opened;
   private SimpleObjectProperty<BigDecimal> initialAmount;
   private SimpleObjectProperty<BigDecimal> totalCashIn;
   private SimpleObjectProperty<BigDecimal> totalCashOut;
   private SimpleObjectProperty<BigDecimal> totalCash;
   private SimpleObjectProperty<BigDecimal> totalCheck;
   private SimpleObjectProperty<BigDecimal> totalCreditCard;
   private SimpleObjectProperty<BigDecimal> totalCompanyVoucher;
   private SimpleObjectProperty<BigDecimal> totalClientVoucher;
   private SimpleObjectProperty<Calendar> openingDate;
   private SimpleObjectProperty<Calendar> closingDate;

   public PaymentCashDrawer()
   {
   }

   public PaymentCashDrawer(CashDrawer entity)
   {
      PropertyReader.copy(entity, this);
   }

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

   public SimpleStringProperty cashDrawerNumberProperty()
   {
      if (cashDrawerNumber == null)
      {
         cashDrawerNumber = new SimpleStringProperty();
      }
      return cashDrawerNumber;
   }

   public String getCashDrawerNumber()
   {
      return cashDrawerNumberProperty().get();
   }

   public final void setCashDrawerNumber(String cashDrawerNumber)
   {
      this.cashDrawerNumberProperty().set(cashDrawerNumber);
   }

   public SimpleBooleanProperty openedProperty()
   {
      if (opened == null)
      {
         opened = new SimpleBooleanProperty();
      }
      return opened;
   }

   public Boolean getOpened()
   {
      return openedProperty().get();
   }

   public final void setOpened(Boolean opened)
   {
      if (opened == null)
         opened = Boolean.FALSE;
      this.openedProperty().set(opened);
   }

   public SimpleObjectProperty<BigDecimal> initialAmountProperty()
   {
      if (initialAmount == null)
      {
         initialAmount = new SimpleObjectProperty<BigDecimal>();
      }
      return initialAmount;
   }

   public BigDecimal getInitialAmount()
   {
      return initialAmountProperty().get();
   }

   public final void setInitialAmount(BigDecimal initialAmount)
   {
      this.initialAmountProperty().set(initialAmount);
   }

   public SimpleObjectProperty<BigDecimal> totalCashInProperty()
   {
      if (totalCashIn == null)
      {
         totalCashIn = new SimpleObjectProperty<BigDecimal>();
      }
      return totalCashIn;
   }

   public BigDecimal getTotalCashIn()
   {
      return totalCashInProperty().get();
   }

   public final void setTotalCashIn(BigDecimal totalCashIn)
   {
      this.totalCashInProperty().set(totalCashIn);
   }

   public SimpleObjectProperty<BigDecimal> totalCashOutProperty()
   {
      if (totalCashOut == null)
      {
         totalCashOut = new SimpleObjectProperty<BigDecimal>();
      }
      return totalCashOut;
   }

   public BigDecimal getTotalCashOut()
   {
      return totalCashOutProperty().get();
   }

   public final void setTotalCashOut(BigDecimal totalCashOut)
   {
      this.totalCashOutProperty().set(totalCashOut);
   }

   public SimpleObjectProperty<BigDecimal> totalCashProperty()
   {
      if (totalCash == null)
      {
         totalCash = new SimpleObjectProperty<BigDecimal>();
      }
      return totalCash;
   }

   public BigDecimal getTotalCash()
   {
      return totalCashProperty().get();
   }

   public final void setTotalCash(BigDecimal totalCash)
   {
      this.totalCashProperty().set(totalCash);
   }

   public SimpleObjectProperty<BigDecimal> totalCheckProperty()
   {
      if (totalCheck == null)
      {
         totalCheck = new SimpleObjectProperty<BigDecimal>();
      }
      return totalCheck;
   }

   public BigDecimal getTotalCheck()
   {
      return totalCheckProperty().get();
   }

   public final void setTotalCheck(BigDecimal totalCheck)
   {
      this.totalCheckProperty().set(totalCheck);
   }

   public SimpleObjectProperty<BigDecimal> totalCreditCardProperty()
   {
      if (totalCreditCard == null)
      {
         totalCreditCard = new SimpleObjectProperty<BigDecimal>();
      }
      return totalCreditCard;
   }

   public BigDecimal getTotalCreditCard()
   {
      return totalCreditCardProperty().get();
   }

   public final void setTotalCreditCard(BigDecimal totalCreditCard)
   {
      this.totalCreditCardProperty().set(totalCreditCard);
   }

   public SimpleObjectProperty<BigDecimal> totalCompanyVoucherProperty()
   {
      if (totalCompanyVoucher == null)
      {
         totalCompanyVoucher = new SimpleObjectProperty<BigDecimal>();
      }
      return totalCompanyVoucher;
   }

   public BigDecimal getTotalCompanyVoucher()
   {
      return totalCompanyVoucherProperty().get();
   }

   public final void setTotalCompanyVoucher(BigDecimal totalCompanyVoucher)
   {
      this.totalCompanyVoucherProperty().set(totalCompanyVoucher);
   }

   public SimpleObjectProperty<BigDecimal> totalClientVoucherProperty()
   {
      if (totalClientVoucher == null)
      {
         totalClientVoucher = new SimpleObjectProperty<BigDecimal>();
      }
      return totalClientVoucher;
   }

   public BigDecimal getTotalClientVoucher()
   {
      return totalClientVoucherProperty().get();
   }

   public final void setTotalClientVoucher(BigDecimal totalClientVoucher)
   {
      this.totalClientVoucherProperty().set(totalClientVoucher);
   }

   public SimpleObjectProperty<Calendar> openingDateProperty()
   {
      if (openingDate == null)
      {
         openingDate = new SimpleObjectProperty<Calendar>();
      }
      return openingDate;
   }

   public Calendar getOpeningDate()
   {
      return openingDateProperty().get();
   }

   public final void setOpeningDate(Calendar openingDate)
   {
      this.openingDateProperty().set(openingDate);
   }

   public SimpleObjectProperty<Calendar> closingDateProperty()
   {
      if (closingDate == null)
      {
         closingDate = new SimpleObjectProperty<Calendar>();
      }
      return closingDate;
   }

   public Calendar getClosingDate()
   {
      return closingDateProperty().get();
   }

   public final void setClosingDate(Calendar closingDate)
   {
      this.closingDateProperty().set(closingDate);
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

   //	@Override
   //	public boolean equals(Object obj) {
   //		if (this == obj)
   //			return true;
   //		if (obj == null)
   //			return false;
   //		if (getClass() != obj.getClass())
   //			return false;
   //		PaymentCashDrawer other = (PaymentCashDrawer) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "cashDrawerNumber");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      PaymentCashDrawer a = new PaymentCashDrawer();
      a.id = id;
      a.version = version;

      a.cashDrawerNumber = cashDrawerNumber;
      a.opened = opened;
      a.initialAmount = initialAmount;
      a.totalCashIn = totalCashIn;
      a.totalCashOut = totalCashOut;
      a.totalCash = totalCash;
      a.totalCheck = totalCheck;
      a.totalCreditCard = totalCreditCard;
      a.totalCompanyVoucher = totalCompanyVoucher;
      a.totalClientVoucher = totalClientVoucher;
      a.openingDate = openingDate;
      a.closingDate = closingDate;
      return a;
   }

}
