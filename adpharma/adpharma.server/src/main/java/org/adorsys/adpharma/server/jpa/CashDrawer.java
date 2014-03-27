package org.adorsys.adpharma.server.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PostPersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.adorsys.adpharma.server.utils.SequenceGenerator;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.Association;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javaext.list.ListField;

@Entity
@Description("CashDrawer_description")
@ToStringField("cashDrawerNumber")
@ListField({ "cashDrawerNumber", "agency.name", "openingDate", "closingDate",
	"initialAmount", "totalCashIn", "totalCashOut", "totalCash",
	"totalCheck", "totalCreditCard", "totalCompanyVoucher",
	"totalClientVoucher", "opened" })
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
	@Description("CashDrawer_cashDrawerNumber_description")
	private String cashDrawerNumber;

	@ManyToOne
	@Description("CashDrawer_cashier_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
	@NotNull(message = "CashDrawer_cashier_NotNull_validation")
	private Login cashier;

	@ManyToOne
	@Description("CashDrawer_closedBy_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
	private Login closedBy;

	@ManyToOne
	@Description("CashDrawer_agency_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
	@NotNull(message = "CashDrawer_agency_NotNull_validation")
	private Agency agency;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("CashDrawer_openingDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date openingDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("CashDrawer_closingDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date closingDate;

	@Column
	@Description("CashDrawer_initialAmount_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal initialAmount;

	@Column
	@Description("CashDrawer_totalCashIn_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalCashIn;

	@Column
	@Description("CashDrawer_totalCashOut_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalCashOut;

	@Column
	@Description("CashDrawer_totalCash_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalCash;

	@Column
	@Description("CashDrawer_totalCheck_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalCheck;

	@Column
	@Description("CashDrawer_totalCreditCard_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalCreditCard;

	@Column
	@Description("CashDrawer_totalCompanyVoucher_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalCompanyVoucher;

	@Column
	@Description("CashDrawer_totalClientVoucher_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal totalClientVoucher;

	@Column
	@Description("CashDrawer_opened_description")
	private Boolean opened;

	@PostPersist
	public void onPostPersist(){
		opened = Boolean.TRUE;
		openingDate = new Date();
		cashDrawerNumber = SequenceGenerator.getSequence(getId(), SequenceGenerator.CASHDRAWER_SEQUENCE_PREFIXE);
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

	public Login getCashier()
	{
		return this.cashier;
	}

	public void setCashier(final Login cashier)
	{
		this.cashier = cashier;
	}

	public Login getClosedBy()
	{
		return this.closedBy;
	}

	public void setClosedBy(final Login closedBy)
	{
		this.closedBy = closedBy;
	}

	public Agency getAgency()
	{
		return this.agency;
	}

	public void setAgency(final Agency agency)
	{
		this.agency = agency;
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

	public Boolean getOpened()
	{
		return this.opened;
	}

	public void setOpened(final Boolean opened)
	{
		this.opened = opened;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (cashDrawerNumber != null && !cashDrawerNumber.trim().isEmpty())
			result += "cashDrawerNumber: " + cashDrawerNumber;
		if (opened != null)
			result += ", opened: " + opened;
		return result;
	}
}