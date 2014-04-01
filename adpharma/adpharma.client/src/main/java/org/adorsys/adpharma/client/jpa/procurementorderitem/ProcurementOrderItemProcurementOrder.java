package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderCreatingUser;
import org.adorsys.adpharma.client.jpa.procmtordertriggermode.ProcmtOrderTriggerMode;
import org.adorsys.adpharma.client.jpa.procurementordertype.ProcurementOrderType;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderSupplier;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderAgency;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrderVat;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("ProcurementOrder_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcurementOrderItemProcurementOrder implements Association<ProcurementOrderItem, ProcurementOrder>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty procurementOrderNumber;
   private SimpleObjectProperty<BigDecimal> amountBeforeTax;
   private SimpleObjectProperty<BigDecimal> amountAfterTax;
   private SimpleObjectProperty<BigDecimal> amountDiscount;
   private SimpleObjectProperty<BigDecimal> taxAmount;
   private SimpleObjectProperty<BigDecimal> netAmountToPay;

   public ProcurementOrderItemProcurementOrder()
   {
   }

   public ProcurementOrderItemProcurementOrder(ProcurementOrder entity)
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

   public SimpleStringProperty procurementOrderNumberProperty()
   {
      if (procurementOrderNumber == null)
      {
         procurementOrderNumber = new SimpleStringProperty();
      }
      return procurementOrderNumber;
   }

   public String getProcurementOrderNumber()
   {
      return procurementOrderNumberProperty().get();
   }

   public final void setProcurementOrderNumber(String procurementOrderNumber)
   {
      this.procurementOrderNumberProperty().set(procurementOrderNumber);
   }

   public SimpleObjectProperty<BigDecimal> amountBeforeTaxProperty()
   {
      if (amountBeforeTax == null)
      {
         amountBeforeTax = new SimpleObjectProperty<BigDecimal>();
      }
      return amountBeforeTax;
   }

   public BigDecimal getAmountBeforeTax()
   {
      return amountBeforeTaxProperty().get();
   }

   public final void setAmountBeforeTax(BigDecimal amountBeforeTax)
   {
      this.amountBeforeTaxProperty().set(amountBeforeTax);
   }

   public SimpleObjectProperty<BigDecimal> amountAfterTaxProperty()
   {
      if (amountAfterTax == null)
      {
         amountAfterTax = new SimpleObjectProperty<BigDecimal>();
      }
      return amountAfterTax;
   }

   public BigDecimal getAmountAfterTax()
   {
      return amountAfterTaxProperty().get();
   }

   public final void setAmountAfterTax(BigDecimal amountAfterTax)
   {
      this.amountAfterTaxProperty().set(amountAfterTax);
   }

   public SimpleObjectProperty<BigDecimal> amountDiscountProperty()
   {
      if (amountDiscount == null)
      {
         amountDiscount = new SimpleObjectProperty<BigDecimal>();
      }
      return amountDiscount;
   }

   public BigDecimal getAmountDiscount()
   {
      return amountDiscountProperty().get();
   }

   public final void setAmountDiscount(BigDecimal amountDiscount)
   {
      this.amountDiscountProperty().set(amountDiscount);
   }

   public SimpleObjectProperty<BigDecimal> taxAmountProperty()
   {
      if (taxAmount == null)
      {
         taxAmount = new SimpleObjectProperty<BigDecimal>();
      }
      return taxAmount;
   }

   public BigDecimal getTaxAmount()
   {
      return taxAmountProperty().get();
   }

   public final void setTaxAmount(BigDecimal taxAmount)
   {
      this.taxAmountProperty().set(taxAmount);
   }

   public SimpleObjectProperty<BigDecimal> netAmountToPayProperty()
   {
      if (netAmountToPay == null)
      {
         netAmountToPay = new SimpleObjectProperty<BigDecimal>();
      }
      return netAmountToPay;
   }

   public BigDecimal getNetAmountToPay()
   {
      return netAmountToPayProperty().get();
   }

   public final void setNetAmountToPay(BigDecimal netAmountToPay)
   {
      this.netAmountToPayProperty().set(netAmountToPay);
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
   //		ProcurementOrderItemProcurementOrder other = (ProcurementOrderItemProcurementOrder) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "procurementOrderNumber");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      ProcurementOrderItemProcurementOrder a = new ProcurementOrderItemProcurementOrder();
      a.id = id;
      a.version = version;

      a.procurementOrderNumber = procurementOrderNumber;
      a.amountBeforeTax = amountBeforeTax;
      a.amountAfterTax = amountAfterTax;
      a.amountDiscount = amountDiscount;
      a.taxAmount = taxAmount;
      a.netAmountToPay = netAmountToPay;
      return a;
   }

}
