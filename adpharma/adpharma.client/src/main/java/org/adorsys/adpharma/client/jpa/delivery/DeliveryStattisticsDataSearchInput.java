package org.adorsys.adpharma.client.jpa.delivery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.adorsys.adpharma.client.jpa.customer.Customer;

public class DeliveryStattisticsDataSearchInput {
	private Integer years ;

	private DeliverySupplier supplier ;

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public DeliverySupplier getDeliverySupplier() {
		return supplier;
	}

	public void setDeliverySupplier(DeliverySupplier supplier) {
		this.supplier = supplier;
	}


}
