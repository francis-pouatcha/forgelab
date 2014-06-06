package org.adorsys.adpharma.client.jpa.salesorder;

import java.math.BigDecimal;
import java.util.Date;

import org.adorsys.adpharma.client.jpa.vat.VAT;

public class SalesOrderRestToPay {

	private BigDecimal customerRestToPay = BigDecimal.ZERO;
	private BigDecimal insuranceRestToPay = BigDecimal.ZERO;
	public SalesOrderRestToPay(SalesOrder salesOrder) {
		BigDecimal amountAfterTax = salesOrder.getAmountAfterTax();
		if(amountAfterTax==null || amountAfterTax.compareTo(BigDecimal.ZERO)==0) return;
		SalesOrderInsurance insurance = salesOrder.getInsurance();
		if(insurance==null || 
			insurance.getCoverageRate()==null ||
			insurance.getCoverageRate().compareTo(BigDecimal.ZERO)==0 
			
		) {
			this.customerRestToPay = amountAfterTax;
			return ;
		}
		
		BigDecimal coverageRateAbs = insurance.getCoverageRate();
		BigDecimal coverageRate = VAT.getRawRate(coverageRateAbs);
		customerRestToPay = amountAfterTax.multiply(BigDecimal.ONE.subtract(coverageRate));
		insuranceRestToPay = amountAfterTax.multiply(coverageRate);
	}
	public BigDecimal getCustomerRestToPay() {
		return customerRestToPay;
	}
	public BigDecimal getInsuranceRestToPay() {
		return insuranceRestToPay;
	}
}
