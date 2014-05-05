package org.adorsys.adpharma.client.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChartDataSearchInput {
	private Integer years ;

	public Integer getYears() {
		return years;
	}

	public void setYears(Integer years) {
		this.years = years;
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
