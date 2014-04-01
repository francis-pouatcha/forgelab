package org.adorsys.adpharma.client.jpa.debtstatement;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import java.util.Calendar;
import java.math.BigDecimal;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
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
import javax.validation.constraints.NotNull;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.relation.Relationship;
import org.adorsys.javaext.relation.RelationshipEnd;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("DebtStatement_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("statementNumber")
@ListField({ "statementNumber", "insurrance.fullName", "agency.name",
      "paymentDate", "initialAmount", "advancePayment", "restAmount",
      "settled", "amountFromVouchers", "canceled", "useVoucher" })
public class DebtStatement implements Cloneable
{

   private Long id;
   private int version;

   @Description("DebtStatement_statementNumber_description")
   private SimpleStringProperty statementNumber;
   @Description("DebtStatement_settled_description")
   private SimpleBooleanProperty settled;
   @Description("DebtStatement_canceled_description")
   private SimpleBooleanProperty canceled;
   @Description("DebtStatement_useVoucher_description")
   private SimpleBooleanProperty useVoucher;
   @Description("DebtStatement_initialAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> initialAmount;
   @Description("DebtStatement_advancePayment_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> advancePayment;
   @Description("DebtStatement_restAmount_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> restAmount;
   @Description("DebtStatement_amountFromVouchers_description")
   @NumberFormatType(NumberType.CURRENCY)
   private SimpleObjectProperty<BigDecimal> amountFromVouchers;
   @Description("DebtStatement_paymentDate_description")
   @DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
   private SimpleObjectProperty<Calendar> paymentDate;
   @Description("DebtStatement_insurrance_description")
   @Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
   private SimpleObjectProperty<DebtStatementInsurrance> insurrance;
   @Description("DebtStatement_agency_description")
   @Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
   private SimpleObjectProperty<DebtStatementAgency> agency;
   @Relationship(end = RelationshipEnd.SOURCE, sourceEntity = DebtStatement.class, targetEntity = CustomerInvoice.class, sourceQualifier = "invoices")
   @Description("DebtStatement_invoices_description")
   @Association(associationType = AssociationType.AGGREGATION, targetEntity = CustomerInvoice.class)
   private SimpleObjectProperty<ObservableList<CustomerInvoice>> invoices;

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

   public SimpleStringProperty statementNumberProperty()
   {
      if (statementNumber == null)
      {
         statementNumber = new SimpleStringProperty();
      }
      return statementNumber;
   }

   public String getStatementNumber()
   {
      return statementNumberProperty().get();
   }

   public final void setStatementNumber(String statementNumber)
   {
      this.statementNumberProperty().set(statementNumber);
   }

   public SimpleBooleanProperty settledProperty()
   {
      if (settled == null)
      {
         settled = new SimpleBooleanProperty();
      }
      return settled;
   }

   public Boolean getSettled()
   {
      return settledProperty().get();
   }

   public final void setSettled(Boolean settled)
   {
      if (settled == null)
         settled = Boolean.FALSE;
      this.settledProperty().set(settled);
   }

   public SimpleBooleanProperty canceledProperty()
   {
      if (canceled == null)
      {
         canceled = new SimpleBooleanProperty();
      }
      return canceled;
   }

   public Boolean getCanceled()
   {
      return canceledProperty().get();
   }

   public final void setCanceled(Boolean canceled)
   {
      if (canceled == null)
         canceled = Boolean.FALSE;
      this.canceledProperty().set(canceled);
   }

   public SimpleBooleanProperty useVoucherProperty()
   {
      if (useVoucher == null)
      {
         useVoucher = new SimpleBooleanProperty();
      }
      return useVoucher;
   }

   public Boolean getUseVoucher()
   {
      return useVoucherProperty().get();
   }

   public final void setUseVoucher(Boolean useVoucher)
   {
      if (useVoucher == null)
         useVoucher = Boolean.FALSE;
      this.useVoucherProperty().set(useVoucher);
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

   public SimpleObjectProperty<BigDecimal> advancePaymentProperty()
   {
      if (advancePayment == null)
      {
         advancePayment = new SimpleObjectProperty<BigDecimal>();
      }
      return advancePayment;
   }

   public BigDecimal getAdvancePayment()
   {
      return advancePaymentProperty().get();
   }

   public final void setAdvancePayment(BigDecimal advancePayment)
   {
      this.advancePaymentProperty().set(advancePayment);
   }

   public SimpleObjectProperty<BigDecimal> restAmountProperty()
   {
      if (restAmount == null)
      {
         restAmount = new SimpleObjectProperty<BigDecimal>();
      }
      return restAmount;
   }

   public BigDecimal getRestAmount()
   {
      return restAmountProperty().get();
   }

   public final void setRestAmount(BigDecimal restAmount)
   {
      this.restAmountProperty().set(restAmount);
   }

   public SimpleObjectProperty<BigDecimal> amountFromVouchersProperty()
   {
      if (amountFromVouchers == null)
      {
         amountFromVouchers = new SimpleObjectProperty<BigDecimal>();
      }
      return amountFromVouchers;
   }

   public BigDecimal getAmountFromVouchers()
   {
      return amountFromVouchersProperty().get();
   }

   public final void setAmountFromVouchers(BigDecimal amountFromVouchers)
   {
      this.amountFromVouchersProperty().set(amountFromVouchers);
   }

   public SimpleObjectProperty<Calendar> paymentDateProperty()
   {
      if (paymentDate == null)
      {
         paymentDate = new SimpleObjectProperty<Calendar>();
      }
      return paymentDate;
   }

   public Calendar getPaymentDate()
   {
      return paymentDateProperty().get();
   }

   public final void setPaymentDate(Calendar paymentDate)
   {
      this.paymentDateProperty().set(paymentDate);
   }

   public SimpleObjectProperty<DebtStatementInsurrance> insurranceProperty()
   {
      if (insurrance == null)
      {
         insurrance = new SimpleObjectProperty<DebtStatementInsurrance>(new DebtStatementInsurrance());
      }
      return insurrance;
   }

   public DebtStatementInsurrance getInsurrance()
   {
      return insurranceProperty().get();
   }

   public final void setInsurrance(DebtStatementInsurrance insurrance)
   {
      if (insurrance == null)
      {
         insurrance = new DebtStatementInsurrance();
      }
      PropertyReader.copy(insurrance, getInsurrance());
      insurranceProperty().setValue(ObjectUtils.clone(getInsurrance()));
   }

   public SimpleObjectProperty<DebtStatementAgency> agencyProperty()
   {
      if (agency == null)
      {
         agency = new SimpleObjectProperty<DebtStatementAgency>(new DebtStatementAgency());
      }
      return agency;
   }

   @NotNull(message = "DebtStatement_agency_NotNull_validation")
   public DebtStatementAgency getAgency()
   {
      return agencyProperty().get();
   }

   public final void setAgency(DebtStatementAgency agency)
   {
      if (agency == null)
      {
         agency = new DebtStatementAgency();
      }
      PropertyReader.copy(agency, getAgency());
      agencyProperty().setValue(ObjectUtils.clone(getAgency()));
   }

   public SimpleObjectProperty<ObservableList<CustomerInvoice>> invoicesProperty()
   {
      if (invoices == null)
      {
         ObservableList<CustomerInvoice> observableArrayList = FXCollections.observableArrayList();
         invoices = new SimpleObjectProperty<ObservableList<CustomerInvoice>>(observableArrayList);
      }
      return invoices;
   }

   public List<CustomerInvoice> getInvoices()
   {
      return new ArrayList<CustomerInvoice>(invoicesProperty().get());
   }

   public final void setInvoices(List<CustomerInvoice> invoices)
   {
      this.invoicesProperty().get().clear();
      if (invoices != null)
         this.invoicesProperty().get().addAll(invoices);
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
      DebtStatement other = (DebtStatement) obj;
      if (id == other.id)
         return true;
      if (id == null)
         return other.id == null;
      return id.equals(other.id);
   }

   public String toString()
   {
      return PropertyReader.buildToString(this, "statementNumber");
   }

   public void cleanIds()
   {
      id = null;
      version = 0;
   }

   @Override
   public Object clone() throws CloneNotSupportedException
   {
      DebtStatement e = new DebtStatement();
      e.id = id;
      e.version = version;

      e.statementNumber = statementNumber;
      e.settled = settled;
      e.canceled = canceled;
      e.useVoucher = useVoucher;
      e.initialAmount = initialAmount;
      e.advancePayment = advancePayment;
      e.restAmount = restAmount;
      e.amountFromVouchers = amountFromVouchers;
      e.paymentDate = paymentDate;
      e.insurrance = insurrance;
      e.agency = agency;
      e.invoices = invoices;
      return e;
   }
}