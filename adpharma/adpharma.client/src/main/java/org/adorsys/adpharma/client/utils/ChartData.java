package org.adorsys.adpharma.client.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
		super();
		this.name = name;
		this.value = value;
	}
	
	public static List<PieChart.Data> toPieChartData(List<ChartData>  chartDatas){
		ArrayList<Data> arrayList = new ArrayList<PieChart.Data>();
		for (ChartData chartData : chartDatas) {
			arrayList.add(new PieChart.Data(chartData.getName(),chartData.getValue().doubleValue()));
		}
		return arrayList;
	}
	
	public static List<ChartData> getTestData (){
		ArrayList<ChartData> arrayList = new ArrayList<ChartData>();
		arrayList.add(new ChartData("JANVIER", BigDecimal.valueOf(1200000)));
		arrayList.add(new ChartData("FEVRIE", BigDecimal.valueOf(300000)));
		arrayList.add(new ChartData("MARS", BigDecimal.valueOf(2300000)));
		arrayList.add(new ChartData("AVRIL", BigDecimal.valueOf(4000000)));
		arrayList.add(new ChartData("MAI", BigDecimal.valueOf(2300000)));
		arrayList.add(new ChartData("JUIN", BigDecimal.valueOf(500000)));
		arrayList.add(new ChartData("JUILLET", BigDecimal.valueOf(750000)));
		arrayList.add(new ChartData("AOUT", BigDecimal.valueOf(3590000)));
		arrayList.add(new ChartData("SEPTEMBRE", BigDecimal.valueOf(4000000)));
		arrayList.add(new ChartData("OCTOBRE", BigDecimal.valueOf(30000000)));
		arrayList.add(new ChartData("NOVEMBRE", BigDecimal.valueOf(700000)));
		arrayList.add(new ChartData("DECEMBRE", BigDecimal.valueOf(4500000)));
		return arrayList ;
	}
}
