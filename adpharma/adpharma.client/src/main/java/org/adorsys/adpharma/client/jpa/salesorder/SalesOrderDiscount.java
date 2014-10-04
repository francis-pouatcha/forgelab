package org.adorsys.adpharma.client.jpa.salesorder;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SalesOrderDiscount {
	
	/**
	 * Nom de l'utilisateur
	 */
	private String fullName;
	
	/**
	 * Montant total TTC
	 */
	private BigDecimal totalAmountAfterTax;
	
	/**
	 * 
	 */
	private BigDecimal totalDiscount;
	
	
	

	public SalesOrderDiscount() {
		super();
		totalAmountAfterTax=BigDecimal.ZERO;
		totalDiscount= BigDecimal.ZERO;
	}
	
	

	public SalesOrderDiscount(String fullName, BigDecimal totalAmountAfterTax,
			BigDecimal totalDiscount) {
		super();
		this.fullName = fullName;
		this.totalAmountAfterTax = totalAmountAfterTax;
		this.totalDiscount = totalDiscount;
	}



	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public BigDecimal getTotalAmountAfterTax() {
		return totalAmountAfterTax;
	}

	public void setTotalAmountAfterTax(BigDecimal totalAmountAfterTax) {
		this.totalAmountAfterTax = totalAmountAfterTax;
	}

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}
	
	
	@Override
	public String toString() {
		return PropertyReader.buildToString(this, fullName, totalAmountAfterTax.toString(), totalDiscount.toString());
	}
	
	
	
	

}
