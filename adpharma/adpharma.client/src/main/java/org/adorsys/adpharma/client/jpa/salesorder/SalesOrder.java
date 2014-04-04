package org.adorsys.adpharma.client.jpa.salesorder;

import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.Calendar;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import javafx.beans.property.SimpleBooleanProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderType;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.apache.commons.lang3.ObjectUtils;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.DateFormatPattern;
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.display.ToStringField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("SalesOrder_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "cashDrawer.cashDrawerNumber", "soNumber", "customer.fullName",
      "insurance.customer.fullName", "insurance.insurer.fullName",
      "vat.rate", "salesAgent.fullName", "agency.name", "salesOrderStatus",
      "cashed", "amountBeforeTax", "amountVAT", "amountDiscount",
      "totalReturnAmount", "amountAfterTax", "salesOrderType" })
@ToStringField("soNumber")
public class SalesOrder implements Cloneable
{

   private Long id;
   private int version;

   @Description("SalesOrder_soNumber_description")
   private SimpleStringProperty soNumber;
   @Description("SalesOrder_cashed_description")
   private SimpleBooleanProperty cashed;
   @Description("SalesOrder_salesOrderStatus_description")
   private SimpleObjectProperty<DocumentProcessingState> salesOrderStatus;
   @Description("SalesOrder_salesOrderType_description")
   private SimpleObjectProperty<SalesOrderType> salesOrderType;
   @Description("SalesOrder_amountBeforeTax_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountBeforeTax;
   @Description("SalesOrder_amountVAT_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountVAT;
   @Description("SalesOrder_amountDiscount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountDiscount;
   @Description("SalesOrder_totalReturnAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> totalReturnAmount;
   @Description("SalesOrder_amountAfterTax_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountAfterTax;
   @Description("SalesOrder_creationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> creationDate;
   @Description("SalesOrder_cancelationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> cancelationDate;
   @Description("SalesOrder_restorationDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> restorationDate;
   @Description("SalesOrder_salesOrderItems_description")
   @Association(associationType = AssociationType.COMPOSITION, targetEntity = SalesOrderItem.class, selectionMode = SelectionMode.TABLE)
   private SimpleObjectProperty<ObservableList<SalesOrderItem>> salesOrderItems;
   
   @Description("SalesOrder_cashDrawer_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = CashDrawer.class)
   private SimpleObjectProperty<SalesOrderCashDrawer> cashDrawer;
   @Description("SalesOrder_customer_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   private SimpleObjectProperty<SalesOrderCustomer> customer;
   @Description("SalesOrder_insurance_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Insurrance.class)
   private SimpleObjectProperty<SalesOrderInsurance> insurance;
   @Description("SalesOrder_vat_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = VAT.class)
   private SimpleObjectProperty<SalesOrderVat> vat;
   @Description("SalesOrder_salesAgent_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
   private SimpleObjectProperty<SalesOrderSalesAgent> salesAgent;
   @Description("SalesOrder_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<SalesOrderAgency> agency;
   
   public void calculateAmount() {
		//  TODO calculate all the amount of this sale oder	
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

   public SimpleObjectProperty<Calendar> creationDateProperty()
   {
      if (creationDate == null)
      {
         creationDate = new SimpleObjectProperty<Calendar>();
      }
      return creationDate;
   }

   public Calendar getCreationDate()
   {
      return creationDateProperty().get();
   }

   public final void setCreationDate(Calendar creationDate)
   {
      this.creationDateProperty().set(creationDate);
   }

   public SimpleObjectProperty<Calendar> cancelationDateProperty()
   {
      if (cancelationDate == null)
      {
         cancelationDate = new SimpleObjectProperty<Calendar>();
      }
      return cancelationDate;
   }

   public Calendar getCancelationDate()
   {
      return cancelationDateProperty().get();
   }

   public final void setCancelationDate(Calendar cancelationDate)
   {
      this.cancelationDateProperty().set(cancelationDate);
   }

   public SimpleObjectProperty<Calendar> restorationDateProperty()
   {
      if (restorationDate == null)
      {
         restorationDate = new SimpleObjectProperty<Calendar>();
      }
      return restorationDate;
   }

   public Calendar getRestorationDate()
   {
      return restorationDateProperty().get();
   }

   public final void setRestorationDate(Calendar restorationDate)
   {
      this.restorationDateProperty().set(restorationDate);
   }

   public SimpleObjectProperty<ObservableList<SalesOrderItem>> salesOrderItemsProperty()
   {
      if (salesOrderItems == null)
      {
         ObservableList<SalesOrderItem> observableArrayList = FXCollections.observableArrayList();
         salesOrderItems = new SimpleObjectProperty<ObservableList<SalesOrderItem>>(observableArrayList);
      }
      return salesOrderItems;
   }

   public List<SalesOrderItem> getSalesOrderItems()
   {
      return new ArrayList<SalesOrderItem>(salesOrderItemsProperty().get());
   }

   public final void setSalesOrderItems(List<SalesOrderItem> salesOrderItems)
   {
      this.salesOrderItemsProperty().get().clear();
      if (salesOrderItems != null)
         this.salesOrderItemsProperty().get().addAll(salesOrderItems);
   }

   public final void addToSalesOrderItems(SalesOrderItem entity)
   {
      this.salesOrderItemsProperty().get().add(entity);
   }

   public SimpleObjectProperty<SalesOrderCashDrawer> cashDrawerProperty()
   {
      if (cashDrawer == null)
      {
         cashDrawer = new SimpleObjectProperty<SalesOrderCashDrawer>(new SalesOrderCashDrawer());
      }
      return cashDrawer;
   }
   @NotNull(message = "SalesOrder_cashDrawer_NotNull_validation")
   public SalesOrderCashDrawer getCashDrawer()
   {
      return cashDrawerProperty().get();
   }

   public final void setCashDrawer(SalesOrderCashDrawer cashDrawer)
   {
      if (cashDrawer == null)
      {
         cashDrawer = new SalesOrderCashDrawer();
      }
      PropertyReader.copy(cashDrawer, getCashDrawer());
      cashDrawerProperty().setValue(ObjectUtils.clone(getCashDrawer()));
   }

   public SimpleObjectProperty<SalesOrderCustomer> customerProperty()
   {
      if (customer == null)
      {
         customer = new SimpleObjectProperty<SalesOrderCustomer>(new SalesOrderCustomer());
      }
      return customer;
   }

   @NotNull(message = "SalesOrder_customer_NotNull_validation")
   public SalesOrderCustomer getCustomer()
   {
      return customerProperty().get();
   }

   public final void setCustomer(SalesOrderCustomer customer)
   {
      if (customer == null)
      {
         customer = new SalesOrderCustomer();
      }
      PropertyReader.copy(customer, getCustomer());
      customerProperty().setValue(ObjectUtils.clone(getCustomer()));
   }

   public SimpleObjectProperty<SalesOrderInsurance> insuranceProperty()
   {
      if (insurance == null)
      {
         insurance = new SimpleObjectProperty<SalesOrderInsurance>(new SalesOrderInsurance());
      }
      return insurance;
   }

   public SalesOrderInsurance getInsurance()
   {
      return insuranceProperty().get();
   }

   public final void setInsurance(SalesOrderInsurance insurance)
   {
      if (insurance == null)
      {
         insurance = new SalesOrderInsurance();
      }
      PropertyReader.copy(insurance, getInsurance());
      insuranceProperty().setValue(ObjectUtils.clone(getInsurance()));
   }

   public SimpleObjectProperty<SalesOrderVat> vatProperty()
   {
      if (vat == null)
      {
         vat = new SimpleObjectProperty<SalesOrderVat>(new SalesOrderVat());
      }
      return vat;
   }

   public SalesOrderVat getVat()
   {
      return vatProperty().get();
   }

   public final void setVat(SalesOrderVat vat)
   {
      if (vat == null)
      {
         vat = new SalesOrderVat();
      }
      PropertyReader.copy(vat, getVat());
      vatProperty().setValue(ObjectUtils.clone(getVat()));
   }

   public SimpleObjectProperty<SalesOrderSalesAgent> salesAgentProperty()
   {
      if (salesAgent == null)
      {
         salesAgent = new SimpleObjectProperty<SalesOrderSalesAgent>(new SalesOrderSalesAgent());
      }
      return salesAgent;
   }

   @NotNull(message = "SalesOrder_salesAgent_NotNull_validation")
   public SalesOrderSalesAgent getSalesAgent()
   {
      return salesAgentProperty().get();
   }

   public final void setSalesAgent(SalesOrderSalesAgent salesAgent)
   {
      if (salesAgent == null)
      {
         salesAgent = new SalesOrderSalesAgent();
      }
      PropertyReader.copy(salesAgent, getSalesAgent());
      salesAgentProperty().setValue(ObjectUtils.clone(getSalesAgent()));
   }

   public SimpleObjectProperty<SalesOrderAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<SalesOrderAgency>(new SalesOrderAgency());
      }
      return agency;
   }

   @NotNull(message = "SalesOrder_agency_NotNull_validation")
   public SalesOrderAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(SalesOrderAgency agency)
   {
      if (agency == null)
      {
         agency = new SalesOrderAgency();
      }
      PropertyReader.copy(agency, getAgency());
      agencyProperty().setValue(ObjectUtils.clone(getAgency()));
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
      SalesOrder other = (SalesOrder) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "soNumber");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
      ObservableList<SalesOrderItem> f = salesOrderItems.get();
      for (SalesOrderItem e : f)
      {
         e.setId(null);
         e.setVersion(0);
      }
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      SalesOrder e = new SalesOrder();
      e.id = id;
      e.version = version;

      e.soNumber = soNumber;
      e.cashed = cashed;
      e.salesOrderStatus = salesOrderStatus;
      e.salesOrderType = salesOrderType;
      e.amountBeforeTax = amountBeforeTax;
      e.amountVAT = amountVAT;
      e.amountDiscount = amountDiscount;
      e.totalReturnAmount = totalReturnAmount;
      e.amountAfterTax = amountAfterTax;
      e.creationDate = creationDate;
      e.cancelationDate = cancelationDate;
      e.restorationDate = restorationDate;
      e.salesOrderItems = salesOrderItems;
      e.cashDrawer = cashDrawer;
      e.customer = customer;
      e.insurance = insurance;
      e.vat = vat;
      e.salesAgent = salesAgent;
      e.agency = agency;
      return e;
   }
}