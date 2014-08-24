package org.adorsys.adpharma.client.jpa.cashdrawer;

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import java.util.Calendar;
import java.math.BigDecimal;
import javafx.beans.property.SimpleBooleanProperty;

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
import org.adorsys.javaext.display.ToStringField;
import org.adorsys.javaext.list.ListField;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("CashDrawer_description")
@JsonIgnoreProperties(ignoreUnknown = true)
@ToStringField("cashDrawerNumber")
@ListField({ "cashDrawerNumber", "agency.name", "openingDate", "closingDate",
	"initialAmount", "totalCashIn", "totalCashOut", "totalCash",
	"totalCheck", "totalCreditCard", "totalCompanyVoucher",
	"totalClientVoucher", "opened" })
public class CashDrawer implements Cloneable
{

	private Long id;
	private int version;

	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> amountDiscount;

	@Description("CashDrawer_cashDrawerNumber_description")
	private SimpleStringProperty cashDrawerNumber;
	@Description("CashDrawer_opened_description")
	private SimpleBooleanProperty opened;
	@Description("CashDrawer_initialAmount_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> initialAmount;
	@Description("CashDrawer_totalCashIn_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> totalCashIn;
	@Description("CashDrawer_totalCashOut_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> totalCashOut;
	@Description("CashDrawer_totalCash_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> totalCash;
	@Description("CashDrawer_totalDrugVoucher_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> totalDrugVoucher;
	@Description("CashDrawer_totalCheck_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> totalCheck;
	@Description("CashDrawer_totalCreditCard_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> totalCreditCard;
	@Description("CashDrawer_totalCompanyVoucher_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> totalCompanyVoucher;
	@Description("CashDrawer_totalClientVoucher_description")
	@NumberFormatType(NumberType.CURRENCY)
	private SimpleObjectProperty<BigDecimal> totalClientVoucher;
	@Description("CashDrawer_openingDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private SimpleObjectProperty<Calendar> openingDate;
	@Description("CashDrawer_closingDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private SimpleObjectProperty<Calendar> closingDate;
	@Description("CashDrawer_cashier_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
	private SimpleObjectProperty<CashDrawerCashier> cashier;
	@Description("CashDrawer_closedBy_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Login.class)
	private SimpleObjectProperty<CashDrawerClosedBy> closedBy;
	@Description("CashDrawer_agency_description")
	@Association(selectionMode = SelectionMode.COMBOBOX, associationType = AssociationType.AGGREGATION, targetEntity = Agency.class)
	private SimpleObjectProperty<CashDrawerAgency> agency;

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
			opened = new SimpleBooleanProperty(Boolean.TRUE);
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
			opened = Boolean.TRUE;
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

	public SimpleObjectProperty<BigDecimal> totalDrugVoucherProperty()
	{
		if (totalDrugVoucher == null)
		{
			totalDrugVoucher = new SimpleObjectProperty<BigDecimal>();
		}
		return totalDrugVoucher;
	}

	public BigDecimal getTotalDrugVoucher()
	{
		return totalDrugVoucherProperty().get();
	}

	public final void setTotalDrugVoucher(BigDecimal totalDrugVoucher)
	{
		this.totalDrugVoucherProperty().set(totalDrugVoucher);
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

	public SimpleObjectProperty<CashDrawerCashier> cashierProperty()
	{
		if (cashier == null)
		{
			cashier = new SimpleObjectProperty<CashDrawerCashier>(new CashDrawerCashier());
		}
		return cashier;
	}

	@NotNull(message = "CashDrawer_cashier_NotNull_validation")
	public CashDrawerCashier getCashier()
	{
		return cashierProperty().get();
	}

	public final void setCashier(CashDrawerCashier cashier)
	{
		if (cashier == null)
		{
			cashier = new CashDrawerCashier();
		}
		PropertyReader.copy(cashier, getCashier());
		cashierProperty().setValue(ObjectUtils.clone(getCashier()));
	}

	public SimpleObjectProperty<CashDrawerClosedBy> closedByProperty()
	{
		if (closedBy == null)
		{
			closedBy = new SimpleObjectProperty<CashDrawerClosedBy>(new CashDrawerClosedBy());
		}
		return closedBy;
	}

	public CashDrawerClosedBy getClosedBy()
	{
		return closedByProperty().get();
	}

	public final void setClosedBy(CashDrawerClosedBy closedBy)
	{
		if (closedBy == null)
		{
			closedBy = new CashDrawerClosedBy();
		}
		PropertyReader.copy(closedBy, getClosedBy());
		closedByProperty().setValue(ObjectUtils.clone(getClosedBy()));
	}

	public SimpleObjectProperty<CashDrawerAgency> agencyProperty()
	{
		if (agency == null)
		{
			agency = new SimpleObjectProperty<CashDrawerAgency>(new CashDrawerAgency());
		}
		return agency;
	}

	@NotNull(message = "CashDrawer_agency_NotNull_validation")
	public CashDrawerAgency getAgency()
	{
		return agencyProperty().get();
	}

	public final void setAgency(CashDrawerAgency agency)
	{
		if (agency == null)
		{
			agency = new CashDrawerAgency();
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
		CashDrawer other = (CashDrawer) obj;
		if (id == other.id)
			return true;
		if (id == null)
			return other.id == null;
		return id.equals(other.id);
	}

	public String toString()
	{
		return PropertyReader.buildToString(this, "cashier","cashDrawerNumber");
	}

	public void cleanIds()
	{
		id = null;
		version = 0;
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		CashDrawer e = new CashDrawer();
		e.id = id;
		e.version = version;

		e.cashDrawerNumber = cashDrawerNumber;
		e.opened = opened;
		e.initialAmount = initialAmount;
		e.totalCashIn = totalCashIn;
		e.totalCashOut = totalCashOut;
		e.totalCash = totalCash;
		e.totalCheck = totalCheck;
		e.totalCreditCard = totalCreditCard;
		e.totalCompanyVoucher = totalCompanyVoucher;
		e.totalClientVoucher = totalClientVoucher;
		e.openingDate = openingDate;
		e.closingDate = closingDate;
		e.cashier = cashier;
		e.closedBy = closedBy;
		e.agency = agency;
		return e;
	}
}