package org.adorsys.adpharma.client.jpa.disbursement;

import java.math.BigDecimal;
import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.apache.commons.lang3.ObjectUtils;

public class Disbursement implements Cloneable{
	private Long id;
	private int version;

	private SimpleStringProperty raison;
	private SimpleObjectProperty<BigDecimal> amount;
	private SimpleObjectProperty<PaymentMode> paymentMode;
	private SimpleObjectProperty<DisbursementCashier> cashier;
	private SimpleObjectProperty<DisbursementCashDrawer> cashDrawer;
	private SimpleObjectProperty<DisbursementAgency> agency;
	private SimpleObjectProperty<Calendar> creationDate;



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

	public SimpleStringProperty raisonProperty()
	{
		if (raison == null)
		{
			raison = new SimpleStringProperty();
		}
		return raison;
	}

	public String getRaison()
	{
		return raisonProperty().get();
	}

	public final void setRaison(String raison)
	{
		this.raisonProperty().set(raison);
	}



	public SimpleObjectProperty<BigDecimal> amountProperty()
	{
		if (amount == null)
		{
			amount = new SimpleObjectProperty<BigDecimal>();
		}
		return amount;
	}

	public BigDecimal getAmount()
	{
		return amountProperty().get();
	}

	public final void setAmount(BigDecimal amount)
	{
		this.amountProperty().set(amount);
	}

	public SimpleObjectProperty<PaymentMode> paymentModeProperty()
	{
		if (paymentMode == null)
		{
			paymentMode = new SimpleObjectProperty<PaymentMode>(PaymentMode.CASH);
		}
		return paymentMode;
	}

	public PaymentMode getPaymentMode()
	{
		return paymentModeProperty().get();
	}

	public final void setPaymentMode(PaymentMode paymentMode)
	{
		this.paymentModeProperty().set(paymentMode);
	}

	public SimpleObjectProperty<DisbursementCashier> cashierProperty()
	{
		if (cashier == null)
		{
			cashier = new SimpleObjectProperty<DisbursementCashier>(new DisbursementCashier());
		}
		return cashier;
	}

	public DisbursementCashier getCashier()
	{
		return cashierProperty().get();
	}

	public final void setCashier(DisbursementCashier cashier)
	{
		if (cashier == null)
		{
			cashier = new DisbursementCashier();
		}
		PropertyReader.copy(cashier, getCashier());
		cashierProperty().setValue(ObjectUtils.clone(getCashier()));
	}


	public SimpleObjectProperty<DisbursementCashDrawer> cashDrawerProperty()
	{
		if (cashDrawer == null)
		{
			cashDrawer = new SimpleObjectProperty<DisbursementCashDrawer>(new DisbursementCashDrawer());
		}
		return cashDrawer;
	}

	public DisbursementCashDrawer getCashDrawer()
	{
		return cashDrawerProperty().get();
	}

	public final void setCashDrawer(DisbursementCashDrawer casDrawer)
	{
		if (casDrawer == null)
		{
			casDrawer = new DisbursementCashDrawer();
		}
		PropertyReader.copy(casDrawer, getCashDrawer());

		cashDrawerProperty().setValue(ObjectUtils.clone(getCashDrawer()));
	}

	public SimpleObjectProperty<DisbursementAgency> agencyProperty()
	{
		if (agency == null)
		{
			agency = new SimpleObjectProperty<DisbursementAgency>(new DisbursementAgency());
		}
		return agency;
	}

	public DisbursementAgency getAgency()
	{
		return agencyProperty().get();
	}

	public final void setAgency(DisbursementAgency agency)
	{
		if (agency == null)
		{
			agency = new DisbursementAgency();
		}
		PropertyReader.copy(agency, getAgency());

		agencyProperty().setValue(ObjectUtils.clone(getAgency()));
	}


	public SimpleObjectProperty<Calendar> creationDateProperty()
	{
		if (creationDate == null)
		{
			creationDate = new SimpleObjectProperty<Calendar>(Calendar.getInstance());
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
		Disbursement other = (Disbursement) obj;
		if (id == other.id)
			return true;
		if (id == null)
			return other.id == null;
		return id.equals(other.id);
	}

	public String toString()
	{
		return PropertyReader.buildToString(this, "cashier","amount","raison");
	}

	public void cleanIds()
	{
		id = null;
		version = 0;
	}

}
