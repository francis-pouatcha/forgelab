package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.math.BigDecimal;
import java.util.Calendar;

import javafx.beans.property.SimpleObjectProperty;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceCustomer;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceInsurer;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.display.AssociationType;
import org.adorsys.javaext.display.SelectionMode;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.Association;
import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@Description("Insurrance_description")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerInvoiceInsurance implements Association<CustomerInvoice, Insurrance>, Cloneable
{

	private Long id;
	private int version;

	private SimpleObjectProperty<BigDecimal> coverageRate;
	private SimpleObjectProperty<Calendar> beginDate;
	private SimpleObjectProperty<Calendar> endDate;
	private SimpleObjectProperty<InsurranceCustomer> customer;
	private SimpleObjectProperty<InsurranceInsurer> insurer;

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
	public CustomerInvoiceInsurance(Insurrance entity) {
		PropertyReader.copy(entity, this);
		// TODO Auto-generated constructor stub
	}
	
	public CustomerInvoiceInsurance() {
	}
	public SimpleObjectProperty<BigDecimal> coverageRateProperty()
	{
		if (coverageRate == null)
		{
			coverageRate = new SimpleObjectProperty<BigDecimal>();
		}
		return coverageRate;
	}

	public BigDecimal getCoverageRate()
	{
		return coverageRateProperty().get();
	}

	public final void setCoverageRate(BigDecimal coverageRate)
	{
		this.coverageRateProperty().set(coverageRate);
	}

	public SimpleObjectProperty<Calendar> beginDateProperty()
	{
		if (beginDate == null)
		{
			beginDate = new SimpleObjectProperty<Calendar>();
		}
		return beginDate;
	}

	@NotNull(message = "Insurrance_beginDate_NotNull_validation")
	public Calendar getBeginDate()
	{
		return beginDateProperty().get();
	}

	public final void setBeginDate(Calendar beginDate)
	{
		this.beginDateProperty().set(beginDate);
	}

	public SimpleObjectProperty<Calendar> endDateProperty()
	{
		if (endDate == null)
		{
			endDate = new SimpleObjectProperty<Calendar>();
		}
		return endDate;
	}

	@NotNull(message = "Insurrance_endDate_NotNull_validation")
	public Calendar getEndDate()
	{
		return endDateProperty().get();
	}

	public final void setEndDate(Calendar endDate)
	{
		this.endDateProperty().set(endDate);
	}

	public SimpleObjectProperty<InsurranceCustomer> customerProperty()
	{
		if (customer == null)
		{
			customer = new SimpleObjectProperty<InsurranceCustomer>(new InsurranceCustomer());
		}
		return customer;
	}

	@NotNull(message = "Insurrance_customer_NotNull_validation")
	public InsurranceCustomer getCustomer()
	{
		return customerProperty().get();
	}

	public final void setCustomer(InsurranceCustomer customer)
	{
		if (customer == null)
		{
			customer = new InsurranceCustomer();
		}
		PropertyReader.copy(customer, getCustomer());
		customerProperty().setValue(ObjectUtils.clone(getCustomer()));
		getCustomer().setSerialNumber(customer.getSerialNumber());
		getCustomer().setSociete(customer.getSociete());
	}

	public SimpleObjectProperty<InsurranceInsurer> insurerProperty()
	{
		if (insurer == null)
		{
			insurer = new SimpleObjectProperty<InsurranceInsurer>(new InsurranceInsurer());
		}
		return insurer;
	}

	@NotNull(message = "Insurrance_insurer_NotNull_validation")
	public InsurranceInsurer getInsurer()
	{
		return insurerProperty().get();
	}

	public final void setInsurer(InsurranceInsurer insurer)
	{
		if (insurer == null)
		{
			insurer = new InsurranceInsurer();
		}
		PropertyReader.copy(insurer, getInsurer());
		insurerProperty().setValue(ObjectUtils.clone(getInsurer()));
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
		CustomerInvoiceInsurance other = (CustomerInvoiceInsurance) obj;
		if (id == other.id)
			return true;
		if (id == null)
			return other.id == null;
		return id.equals(other.id);
	}

	public String toString()
	{
		return PropertyReader.buildToString(this, "insurer", "coverageRate");
	}

	public void cleanIds()
	{
		id = null;
		version = 0;
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		CustomerInvoiceInsurance e = new CustomerInvoiceInsurance();
		e.id = id;
		e.version = version;

		e.coverageRate = coverageRate;
		e.beginDate = beginDate;
		e.endDate = endDate;
		e.customer = customer;
		e.insurer = insurer;
		return e;
	}

}
