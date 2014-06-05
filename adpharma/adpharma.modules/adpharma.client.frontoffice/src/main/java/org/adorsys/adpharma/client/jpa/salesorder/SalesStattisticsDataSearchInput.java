package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.adorsys.adpharma.client.jpa.customer.Customer;


public class SalesStattisticsDataSearchInput {
	private Integer years ;

	private Customer customer ;

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	
}
