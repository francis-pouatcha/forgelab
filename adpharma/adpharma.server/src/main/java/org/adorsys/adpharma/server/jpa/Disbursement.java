package org.adorsys.adpharma.server.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;

@Entity
@Description("Disbursement_description")
public class Disbursement implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	private String raison;
	
	@Column
	private String receipt;

	@Column
	private BigDecimal amount;
	
	@Column
	private String voucherNumber;
	
	@Column
	private BigDecimal voucherAmount;


	@Column
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;

	@ManyToOne(optional = false)
	@Description("Disbursement_cashier_description")
	@NotNull(message = "Disbursemen_cashier_NotNull_validation")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
	private Login cashier;

	@ManyToOne(optional = false)
	@Description("Disbursement_cashDrawer_description")
	@NotNull(message = "Disbursemen_cashDrawer_NotNull_validation")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = CashDrawer.class)
	private CashDrawer cashDrawer;

	@Description("Disbursement_agency_description")
	@NotNull(message = "Disbursemen_agency_NotNull_validation")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
	@ManyToOne(optional = false)
	private Agency agency;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

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
			return id.equals(((Disbursement) that).id);
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

	public String getRaison()
	{
		return this.raison;
	}

	public void setRaison(final String raison)
	{
		this.raison = raison;
	}

	public BigDecimal getAmount()
	{
		return this.amount;
	}

	public void setAmount(final BigDecimal amount)
	{
		this.amount = amount;
	}

	public PaymentMode getPaymentMode()
	{
		return this.paymentMode;
	}

	public void setPaymentMode(final PaymentMode paymentMode)
	{
		this.paymentMode = paymentMode;
	}

	public Login getCashier()
	{
		return this.cashier;
	}

	public void setCashier(final Login cashier)
	{
		this.cashier = cashier;
	}

	public CashDrawer getCashDrawer()
	{
		return this.cashDrawer;
	}

	public void setCashDrawer(final CashDrawer cashDrawer)
	{
		this.cashDrawer = cashDrawer;
	}

	public Agency getAgency()
	{
		return this.agency;
	}

	public void setAgency(final Agency agency)
	{
		this.agency = agency;
	}

	public Date getCreationDate()
	{
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getVoucherNumber() {
		return voucherNumber;
	}

	public void setVoucherNumber(String voucherNumber) {
		this.voucherNumber = voucherNumber;
	}

	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}
	
	public String getReceipt() {
		return receipt;
	}
	
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (raison != null && !raison.trim().isEmpty())
			result += "raison: " + raison+ "Beneficiaire: "+receipt;
		return result;
	}
}