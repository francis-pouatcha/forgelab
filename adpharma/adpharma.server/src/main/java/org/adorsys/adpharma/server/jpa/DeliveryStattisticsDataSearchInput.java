package org.adorsys.adpharma.server.jpa;


public class DeliveryStattisticsDataSearchInput {
	private Integer years ;

	private Supplier supplier ;

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
	}

	public Supplier getDeliverySupplier() {
		return supplier;
	}

	public void setDeliverySupplier(Supplier supplier) {
		this.supplier = supplier;
	}


}
