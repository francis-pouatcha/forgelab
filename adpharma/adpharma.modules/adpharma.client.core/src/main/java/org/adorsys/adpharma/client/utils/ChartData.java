package org.adorsys.adpharma.client.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

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
		this.name = name;
		this.value = value;
	}
	
	public static List<PieChart.Data> toPieChartData(List<ChartData>  chartDatas){
		List<Data> arrayList = new ArrayList<PieChart.Data>();
		for (ChartData chartData : chartDatas) {
			arrayList.add(new PieChart.Data(chartData.getName(),chartData.getValue().doubleValue()));
		}
		return arrayList;
	}
	
	
}
