package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceType;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;
import org.adorsys.javaext.relation.Relationship;
import org.adorsys.javaext.relation.RelationshipEnd;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("CustomerInvoice_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ListField({ "invoiceType", "invoiceNumber", "creationDate",
	"customer.fullName", "insurance.customer.fullName",
	"insurance.insurer.fullName", "creatingUser.fullName", "agency.name",
	"salesOrder.soNumber", "settled", "amountBeforeTax", "taxAmount",
	"amountDiscount", "amountAfterTax", "netToPay", "customerRestTopay",
	"insurranceRestTopay", "cashed", "advancePayment", "totalRestToPay" })
@ToStringField("invoiceNumber")
public class CustomerInvoice implements Cloneable
{

	private Long id;
	private int version;

	@Description("CustomerInvoice_invoiceNumber_description")
	private SimpleStringProperty invoiceNumber;
	@Description("CustomerInvoice_patientMatricle_description")
	private SimpleStringProperty patientMatricle;
	@Description("CustomerInvoice_settled_description")
	private SimpleBooleanProperty settled;
	@Description("CustomerInvoice_cashed_description")
	private SimpleBooleanProperty cashed;
	@Description("CustomerInvoice_invoiceType_description")
	private SimpleObjectProperty<InvoiceType> invoiceType;
	@Description("CustomerInvoice_amountBeforeTax_description")
	private SimpleObjectProperty<BigDecimal> amountBeforeTax;
	@Description("CustomerInvoice_taxAmount_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> taxAmount;
	@Description("CustomerInvoice_amountDiscount_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> amountDiscount;
	@Description("CustomerInvoice_amountAfterTax_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> amountAfterTax;
	@Description("CustomerInvoice_netToPay_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> netToPay;
	@Description("CustomerInvoice_customerRestTopay_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> customerRestTopay;
	@Description("CustomerInvoice_insurranceRestTopay_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> insurranceRestTopay;
	@Description("CustomerInvoice_advancePayment_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> advancePayment;
	@Description("CustomerInvoice_totalRestToPay_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> totalRestToPay;
	@Description("CustomerInvoice_creationDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private SimpleObjectProperty<Calendar> creationDate;
	@Description("CustomerInvoice_invoiceItems_description")
	@Association(associationType = AssociationType.COMPOSITION, targetEntity = CustomerInvoiceItem.class, selectionMode = SelectionMode.TABLE)
	private SimpleObjectProperty<ObservableList<CustomerInvoiceItem>> invoiceItems;
	@Description("CustomerInvoice_customer_description")
	@Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Customer.class)
	private SimpleObjectProperty<CustomerInvoiceCustomer> customer;
	@Description("CustomerInvoice_insurance_description")
	@Association(selectionMode = SelectionMode.FORWARD, associationType = AssociationType.AGGREGATION, targetEntity = Insurrance.class)
	private SimpleObjectProperty<CustomerInvoiceInsurance> insurance;
	@Description("CustomerInvoice_creatingUser_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
	private SimpleObjectProperty<CustomerInvoiceCreatingUser> creatingUser;
	@Description("CustomerInvoice_agency_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
	private SimpleObjectProperty<CustomerInvoiceAgency> agency;
	@Description("CustomerInvoice_salesOrder_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = SalesOrder.class)
	private SimpleObjectProperty<CustomerInvoiceSalesOrder> salesOrder;
	@Relationship(end = RelationshipEnd.TARGET, sourceEntity = Payment.class, targetEntity = CustomerInvoice.class, sourceQualifier = "invoices", targetQualifier = "payments")
	@Description("CustomerInvoice_payments_description")
	@Association(associationType = AssociationType.AGGREGATION, targetEntity = Payment.class)
	private SimpleObjectProperty<ObservableList<Payment>> payments;

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

	public SimpleStringProperty invoiceNumberProperty()
	{
		if (invoiceNumber == null)
		{
			invoiceNumber = new SimpleStringProperty();
		}
		return invoiceNumber;
	}

	public String getInvoiceNumber()
	{
		return invoiceNumberProperty().get();
	}

	public final void setInvoiceNumber(String invoiceNumber)
	{
		this.invoiceNumberProperty().set(invoiceNumber);
	}
	
	public SimpleStringProperty patientMatricleProperty()
	{
		if (patientMatricle == null)
		{
			patientMatricle = new SimpleStringProperty();
		}
		return patientMatricle;
	}

	public String getPatientMatricle()
	{
		return patientMatricleProperty().get();
	}

	public final void setPatientMatricle(String patientMatricle)
	{
		this.patientMatricleProperty().set(patientMatricle);
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

	public SimpleObjectProperty<InvoiceType> invoiceTypeProperty()
	{
		if (invoiceType == null)
		{
			invoiceType = new SimpleObjectProperty<InvoiceType>();
		}
		return invoiceType;
	}

	public InvoiceType getInvoiceType()
	{
		return invoiceTypeProperty().get();
	}

	public final void setInvoiceType(InvoiceType invoiceType)
	{
		this.invoiceTypeProperty().set(invoiceType);
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

	public SimpleObjectProperty<BigDecimal> netToPayProperty()
	{
		if (netToPay == null)
		{
			netToPay = new SimpleObjectProperty<BigDecimal>();
		}
		return netToPay;
	}

	public BigDecimal getNetToPay()
	{
		return netToPayProperty().get();
	}

	public final void setNetToPay(BigDecimal netToPay)
	{
		this.netToPayProperty().set(netToPay);
	}

	public SimpleObjectProperty<BigDecimal> customerRestTopayProperty()
	{
		if (customerRestTopay == null)
		{
			customerRestTopay = new SimpleObjectProperty<BigDecimal>();
		}
		return customerRestTopay;
	}

	public BigDecimal getCustomerRestTopay()
	{
		return customerRestTopayProperty().get();
	}

	public final void setCustomerRestTopay(BigDecimal customerRestTopay)
	{
		this.customerRestTopayProperty().set(customerRestTopay);
	}

	public SimpleObjectProperty<BigDecimal> insurranceRestTopayProperty()
	{
		if (insurranceRestTopay == null)
		{
			insurranceRestTopay = new SimpleObjectProperty<BigDecimal>();
		}
		return insurranceRestTopay;
	}

	public BigDecimal getInsurranceRestTopay()
	{
		return insurranceRestTopayProperty().get();
	}

	public final void setInsurranceRestTopay(BigDecimal insurranceRestTopay)
	{
		this.insurranceRestTopayProperty().set(insurranceRestTopay);
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

	public SimpleObjectProperty<BigDecimal> totalRestToPayProperty()
	{
		if (totalRestToPay == null)
		{
			totalRestToPay = new SimpleObjectProperty<BigDecimal>();
		}
		return totalRestToPay;
	}

	public BigDecimal getTotalRestToPay()
	{
		return totalRestToPayProperty().get();
	}

	public final void setTotalRestToPay(BigDecimal totalRestToPay)
	{
		this.totalRestToPayProperty().set(totalRestToPay);
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

	public SimpleObjectProperty<ObservableList<CustomerInvoiceItem>> invoiceItemsProperty()
	{
		if (invoiceItems == null)
		{
			ObservableList<CustomerInvoiceItem> observableArrayList = FXCollections.observableArrayList();
			invoiceItems = new SimpleObjectProperty<ObservableList<CustomerInvoiceItem>>(observableArrayList);
		}
		return invoiceItems;
	}

	public List<CustomerInvoiceItem> getInvoiceItems()
	{
		return new ArrayList<CustomerInvoiceItem>(invoiceItemsProperty().get());
	}

	public final void setInvoiceItems(List<CustomerInvoiceItem> invoiceItems)
	{
		this.invoiceItemsProperty().get().clear();
		if (invoiceItems != null)
			this.invoiceItemsProperty().get().addAll(invoiceItems);
	}

	public final void addToInvoiceItems(CustomerInvoiceItem entity)
	{
		this.invoiceItemsProperty().get().add(entity);
	}

	public SimpleObjectProperty<CustomerInvoiceCustomer> customerProperty()
	{
		if (customer == null)
		{
			customer = new SimpleObjectProperty<CustomerInvoiceCustomer>(new CustomerInvoiceCustomer());
		}
		return customer;
	}

	@NotNull(message = "CustomerInvoice_customer_NotNull_validation")
	public CustomerInvoiceCustomer getCustomer()
	{
		return customerProperty().get();
	}

	public final void setCustomer(CustomerInvoiceCustomer customer)
	{
		if (customer == null)
		{
			customer = new CustomerInvoiceCustomer();
		}
		PropertyReader.copy(customer, getCustomer());
		customerProperty().setValue(ObjectUtils.clone(getCustomer()));
		getCustomer().setSociete(customer.getSociete());
	}

	public SimpleObjectProperty<CustomerInvoiceInsurance> insuranceProperty()
	{
		if (insurance == null)
		{
			insurance = new SimpleObjectProperty<CustomerInvoiceInsurance>(new CustomerInvoiceInsurance());
		}
		return insurance;
	}

	public CustomerInvoiceInsurance getInsurance()
	{
		return insuranceProperty().get();
	}

	public final void setInsurance(CustomerInvoiceInsurance insurance)
	{
		if (insurance == null)
		{
			insurance = new CustomerInvoiceInsurance();
		}
		PropertyReader.copy(insurance, getInsurance());
		insuranceProperty().setValue(ObjectUtils.clone(getInsurance()));
	}

	public SimpleObjectProperty<CustomerInvoiceCreatingUser> creatingUserProperty()
	{
		if (creatingUser == null)
		{
			creatingUser = new SimpleObjectProperty<CustomerInvoiceCreatingUser>(new CustomerInvoiceCreatingUser());
		}
		return creatingUser;
	}

	@NotNull(message = "CustomerInvoice_creatingUser_NotNull_validation")
	public CustomerInvoiceCreatingUser getCreatingUser()
	{
		return creatingUserProperty().get();
	}

	public final void setCreatingUser(CustomerInvoiceCreatingUser creatingUser)
	{
		if (creatingUser == null)
		{
			creatingUser = new CustomerInvoiceCreatingUser();
		}
		PropertyReader.copy(creatingUser, getCreatingUser());
		creatingUserProperty().setValue(ObjectUtils.clone(getCreatingUser()));
	}

	public SimpleObjectProperty<CustomerInvoiceAgency> agencyProperty()
	{
		if (agency == null)
		{
			agency = new SimpleObjectProperty<CustomerInvoiceAgency>(new CustomerInvoiceAgency());
		}
		return agency;
	}

	@NotNull(message = "CustomerInvoice_agency_NotNull_validation")
	public CustomerInvoiceAgency getAgency()
	{
		return agencyProperty().get();
	}

	public final void setAgency(CustomerInvoiceAgency agency)
	{
		if (agency == null)
		{
			agency = new CustomerInvoiceAgency();
		}
		PropertyReader.copy(agency, getAgency());
		agencyProperty().setValue(ObjectUtils.clone(getAgency()));
	}

	public SimpleObjectProperty<CustomerInvoiceSalesOrder> salesOrderProperty()
	{
		if (salesOrder == null)
		{
			salesOrder = new SimpleObjectProperty<CustomerInvoiceSalesOrder>(new CustomerInvoiceSalesOrder());
		}
		return salesOrder;
	}

	public CustomerInvoiceSalesOrder getSalesOrder()
	{
		return salesOrderProperty().get();
	}

	public final void setSalesOrder(CustomerInvoiceSalesOrder salesOrder)
	{
		if (salesOrder == null)
		{
			salesOrder = new CustomerInvoiceSalesOrder();
		}
		PropertyReader.copy(salesOrder, getSalesOrder());
		salesOrderProperty().setValue(ObjectUtils.clone(getSalesOrder()));
	}

	public SimpleObjectProperty<ObservableList<Payment>> paymentsProperty()
	{
		if (payments == null)
		{
			ObservableList<Payment> observableArrayList = FXCollections.observableArrayList();
			payments = new SimpleObjectProperty<ObservableList<Payment>>(observableArrayList);
		}
		return payments;
	}

	public List<Payment> getPayments()
	{
		return new ArrayList<Payment>(paymentsProperty().get());
	}

	public final void setPayments(List<Payment> payments)
	{
		this.paymentsProperty().get().clear();
		if (payments != null)
			this.paymentsProperty().get().addAll(payments);
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
		CustomerInvoice other = (CustomerInvoice) obj;
		if (id == other.id)
			return true;
		if (id == null)
			return other.id == null;
		return id.equals(other.id);
	}

	public String toString()
	{
		return PropertyReader.buildToString(this, "invoiceNumber");
	}

	public void cleanIds()
	{
		id = null;
		version = 0;
		ObservableList<CustomerInvoiceItem> f = invoiceItems.get();
		for (CustomerInvoiceItem e : f)
		{
			e.setId(null);
			e.setVersion(0);
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		CustomerInvoice e = new CustomerInvoice();
		e.id = id;
		e.version = version;

		e.invoiceNumber = invoiceNumber;
		e.settled = settled;
		e.cashed = cashed;
		e.invoiceType = invoiceType;
		e.amountBeforeTax = amountBeforeTax;
		e.taxAmount = taxAmount;
		e.amountDiscount = amountDiscount;
		e.amountAfterTax = amountAfterTax;
		e.netToPay = netToPay;
		e.customerRestTopay = customerRestTopay;
		e.insurranceRestTopay = insurranceRestTopay;
		e.advancePayment = advancePayment;
		e.totalRestToPay = totalRestToPay;
		e.creationDate = creationDate;
		e.invoiceItems = invoiceItems;
		e.customer = customer;
		e.insurance = insurance;
		e.creatingUser = creatingUser;
		e.agency = agency;
		e.salesOrder = salesOrder;
		e.payments = payments;
		return e;
	}
}