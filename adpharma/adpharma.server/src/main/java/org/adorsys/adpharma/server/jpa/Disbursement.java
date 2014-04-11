package org.adorsys.adpharma.server.jpa;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;
import java.math.BigDecimal;

import org.adorsys.adpharma.server.jpa.PaymentMode;
import org.adorsys.adpharma.server.jpa.Login;

import javax.persistence.ManyToOne;

import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

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
	private BigDecimal amount;

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

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (raison != null && !raison.trim().isEmpty())
			result += "raison: " + raison;
		return result;
	}
}