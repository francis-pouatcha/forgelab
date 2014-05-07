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
	
	
	public static List<Integer> getYearList(){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<Integer> years = new ArrayList<Integer>();
		for (int i = year; i > year-10; i--) {
			years.add(Integer.valueOf(i));
		}
		years.add(0, null);
		return years;
		
	}
}
