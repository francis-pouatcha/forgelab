package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import java.lang.Override;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

import javax.validation.constraints.NotNull;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.adpharma.server.jpa.Login;

import javax.persistence.ManyToOne;

import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.adpharma.server.jpa.Supplier;

import java.math.BigDecimal;

import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.adpharma.server.jpa.VAT;
import org.adorsys.adpharma.server.jpa.Currency;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;

import javax.persistence.Enumerated;

import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.utils.SequenceGenerator;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

@Entity
@Description("Delivery_description")
@ToStringField("deliveryNumber")
@ListField({ "deliveryNumber", "deliverySlipNumber", "dateOnDeliverySlip",
	"amountBeforeTax", "amountAfterTax", "amountDiscount",
	"netAmountToPay", "vat.rate", "receivingAgency.name" })
public class Delivery implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	@Description("Delivery_deliveryNumber_description")
	@NotNull(message = "Delivery_deliveryNumber_NotNull_validation")
	private String deliveryNumber;

	@Column
	@Description("Delivery_deliverySlipNumber_description")
	@NotNull(message = "Delivery_deliverySlipNumber_NotNull_validation")
	private String deliverySlipNumber;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Delivery_dateOnDeliverySlip_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy")
	private Date dateOnDeliverySlip;

	@ManyToOne
	@Description("Delivery_creatingUser_description")
	@NotNull(message = "Delivery_creatingUser_NotNull_validation")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
	private Login creatingUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Delivery_orderDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date orderDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Delivery_deliveryDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date deliveryDate;

	@ManyToOne
	@Description("Delivery_supplier_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Supplier.class)
	@NotNull(message = "Delivery_supplier_NotNull_validation")
	private Supplier supplier;

	@Column
	@Description("Delivery_amountBeforeTax_description")
	@NotNull(message = "Delivery_amountBeforeTax_NotNull_validation")
	private BigDecimal amountBeforeTax;

	@Column
	@Description("Delivery_amountAfterTax_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal amountAfterTax;

	@Column
	@Description("Delivery_amountDiscount_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal amountDiscount;

	@Column
	@Description("Delivery_netAmountToPay_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal netAmountToPay;

	@ManyToOne
	@Description("Delivery_vat_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = VAT.class)
	private VAT vat;

	@ManyToOne
	@Description("Delivery_currency_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Currency.class)
	private Currency currency;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Delivery_recordingDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date recordingDate;

	@Column
	@Description("Delivery_deliveryProcessingState_description")
	@Enumerated
	private DocumentProcessingState deliveryProcessingState;

	@ManyToOne
	@Description("Delivery_receivingAgency_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
	@NotNull(message = "Delivery_receivingAgency_NotNull_validation")
	private Agency receivingAgency;

	@OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL)
	@Description("Delivery_deliveryItems_description")
	@Association(associationType = AssociationType.COMPOSITION, targetEntity = DeliveryItem.class, selectionMode = SelectionMode.TABLE)
	private Set<DeliveryItem> deliveryItems = new HashSet<DeliveryItem>();

	@PrePersist
	public void prePersist(){
		recordingDate = new Date();
		deliveryNumber = SequenceGenerator.DELIVERY_SEQUENCE_PREFIXE +RandomStringUtils.randomNumeric(6);
	}

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
			return id.equals(((Delivery) that).id);
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

	public String getDeliveryNumber()
	{
		return this.deliveryNumber;
	}

	public void setDeliveryNumber(final String deliveryNumber)
	{
		this.deliveryNumber = deliveryNumber;
	}

	public String getDeliverySlipNumber()
	{
		return this.deliverySlipNumber;
	}

	public void setDeliverySlipNumber(final String deliverySlipNumber)
	{
		this.deliverySlipNumber = deliverySlipNumber;
	}

	public Date getDateOnDeliverySlip()
	{
		return this.dateOnDeliverySlip;
	}

	public void setDateOnDeliverySlip(final Date dateOnDeliverySlip)
	{
		this.dateOnDeliverySlip = dateOnDeliverySlip;
	}

	public Login getCreatingUser()
	{
		return this.creatingUser;
	}

	public void setCreatingUser(final Login creatingUser)
	{
		this.creatingUser = creatingUser;
	}

	public Date getOrderDate()
	{
		return this.orderDate;
	}

	public void setOrderDate(final Date orderDate)
	{
		this.orderDate = orderDate;
	}

	public Date getDeliveryDate()
	{
		return this.deliveryDate;
	}

	public void setDeliveryDate(final Date deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	public Supplier getSupplier()
	{
		return this.supplier;
	}

	public void setSupplier(final Supplier supplier)
	{
		this.supplier = supplier;
	}

	public BigDecimal getAmountBeforeTax()
	{
		return this.amountBeforeTax;
	}

	public void setAmountBeforeTax(final BigDecimal amountBeforeTax)
	{
		this.amountBeforeTax = amountBeforeTax;
	}

	public BigDecimal getAmountAfterTax()
	{
		return this.amountAfterTax;
	}

	public void setAmountAfterTax(final BigDecimal amountAfterTax)
	{
		this.amountAfterTax = amountAfterTax;
	}

	public BigDecimal getAmountDiscount()
	{
		return this.amountDiscount;
	}

	public void setAmountDiscount(final BigDecimal amountDiscount)
	{
		this.amountDiscount = amountDiscount;
	}

	public BigDecimal getNetAmountToPay()
	{
		return this.netAmountToPay;
	}

	public void setNetAmountToPay(final BigDecimal netAmountToPay)
	{
		this.netAmountToPay = netAmountToPay;
	}

	public VAT getVat()
	{
		return this.vat;
	}

	public void setVat(final VAT vat)
	{
		this.vat = vat;
	}

	public Currency getCurrency()
	{
		return this.currency;
	}

	public void setCurrency(final Currency currency)
	{
		this.currency = currency;
	}

	public Date getRecordingDate()
	{
		return this.recordingDate;
	}

	public void setRecordingDate(final Date recordingDate)
	{
		this.recordingDate = recordingDate;
	}

	public DocumentProcessingState getDeliveryProcessingState()
	{
		return this.deliveryProcessingState;
	}

	public void setDeliveryProcessingState(
			final DocumentProcessingState deliveryProcessingState)
	{
		this.deliveryProcessingState = deliveryProcessingState;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (deliveryNumber != null && !deliveryNumber.trim().isEmpty())
			result += "deliveryNumber: " + deliveryNumber;
		if (deliverySlipNumber != null && !deliverySlipNumber.trim().isEmpty())
			result += ", deliverySlipNumber: " + deliverySlipNumber;
		return result;
	}

	public Agency getReceivingAgency()
	{
		return this.receivingAgency;
	}

	public void setReceivingAgency(final Agency receivingAgency)
	{
		this.receivingAgency = receivingAgency;
	}

	public Set<DeliveryItem> getDeliveryItems()
	{
		return this.deliveryItems;
	}

	public void setDeliveryItems(final Set<DeliveryItem> deliveryItems)
	{
		this.deliveryItems = deliveryItems;
	}
}