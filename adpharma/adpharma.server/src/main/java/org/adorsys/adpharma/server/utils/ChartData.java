package org.adorsys.adpharma.server.utils;

import java.math.BigDecimal;

public class ChartData {

	private String name ;

	private BigDecimal value;
	
	

	

	public String getName() {
		return name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	public ChartData() {
		// TODO Auto-generated constructor stub
	}

	public ChartData(String name, BigDecimal value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	
}
