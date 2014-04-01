package org.adorsys.adpharma.client.jpa.salesorderitem;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCashDrawer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCustomer;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderInsurance;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderVat;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSalesAgent;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderAgency;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import javafx.beans.property.SimpleBooleanProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderType;
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

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("SalesOrder_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrderItemSalesOrder implements Association<SalesOrderItem, SalesOrder>, Cloneable
{

   private Long id;
   private int version;

   private SimpleStringProperty soNumber;
   private SimpleBooleanProperty cashed;
   private SimpleObjectProperty<DocumentProcessingState> salesOrderStatus;
   private SimpleObjectProperty<SalesOrderType> salesOrderType;
   private SimpleObjectProperty<BigDecimal> amountBeforeTax;
   private SimpleObjectProperty<BigDecimal> amountVAT;
   private SimpleObjectProperty<BigDecimal> amountDiscount;
   private SimpleObjectProperty<BigDecimal> totalReturnAmount;
   private SimpleObjectProperty<BigDecimal> amountAfterTax;

   public SalesOrderItemSalesOrder()
   {
   }

   public SalesOrderItemSalesOrder(SalesOrder entity)
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

   public SimpleStringProperty soNumberProperty()
   {
      if (soNumber == null)
      {
         soNumber = new SimpleStringProperty();
      }
      return soNumber;
   }

   public String getSoNumber()
   {
      return soNumberProperty().get();
   }

   public final void setSoNumber(String soNumber)
   {
      this.soNumberProperty().set(soNumber);
   }

   public SimpleBooleanProperty cashedProperty()
   {
      if (cashed == null)
      {
         cashed = new SimpleBooleanProperty();
      }
      return cashed;
   }

   public Boolean getCashed()
   {
      return cashedProperty().get();
   }

   public final void setCashed(Boolean cashed)
   {
      if (cashed == null)
         cashed = Boolean.FALSE;
      this.cashedProperty().set(cashed);
   }

   public SimpleObjectProperty<DocumentProcessingState> salesOrderStatusProperty()
   {
      if (salesOrderStatus == null)
      {
         salesOrderStatus = new SimpleObjectProperty<DocumentProcessingState>();
      }
      return salesOrderStatus;
   }

   public DocumentProcessingState getSalesOrderStatus()
   {
      return salesOrderStatusProperty().get();
   }

   public final void setSalesOrderStatus(DocumentProcessingState salesOrderStatus)
   {
      this.salesOrderStatusProperty().set(salesOrderStatus);
   }

   public SimpleObjectProperty<SalesOrderType> salesOrderTypeProperty()
   {
      if (salesOrderType == null)
      {
         salesOrderType = new SimpleObjectProperty<SalesOrderType>();
      }
      return salesOrderType;
   }

   public SalesOrderType getSalesOrderType()
   {
      return salesOrderTypeProperty().get();
   }

   public final void setSalesOrderType(SalesOrderType salesOrderType)
   {
      this.salesOrderTypeProperty().set(salesOrderType);
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

   public SimpleObjectProperty<BigDecimal> amountVATProperty()
   {
      if (amountVAT == null)
      {
         amountVAT = new SimpleObjectProperty<BigDecimal>();
      }
      return amountVAT;
   }

   public BigDecimal getAmountVAT()
   {
      return amountVATProperty().get();
   }

   public final void setAmountVAT(BigDecimal amountVAT)
   {
      this.amountVATProperty().set(amountVAT);
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

   public SimpleObjectProperty<BigDecimal> totalReturnAmountProperty()
   {
      if (totalReturnAmount == null)
      {
         totalReturnAmount = new SimpleObjectProperty<BigDecimal>();
      }
      return totalReturnAmount;
   }

   public BigDecimal getTotalReturnAmount()
   {
      return totalReturnAmountProperty().get();
   }

   public final void setTotalReturnAmount(BigDecimal totalReturnAmount)
   {
      this.totalReturnAmountProperty().set(totalReturnAmount);
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
   //		SalesOrderItemSalesOrder other = (SalesOrderItemSalesOrder) obj;
   //      if(id==other.id) return true;
   //      if (id== null) return other.id==null;
   //      return id.equals(other.id);
   //	}

   public String toString()
   {
      return PropertyReader.buildToString(this, "soNumber");
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      SalesOrderItemSalesOrder a = new SalesOrderItemSalesOrder();
      a.id = id;
      a.version = version;

      a.soNumber = soNumber;
      a.cashed = cashed;
      a.salesOrderStatus = salesOrderStatus;
      a.salesOrderType = salesOrderType;
      a.amountBeforeTax = amountBeforeTax;
      a.amountVAT = amountVAT;
      a.amountDiscount = amountDiscount;
      a.totalReturnAmount = totalReturnAmount;
      a.amountAfterTax = amountAfterTax;
      return a;
   }

}
