package org.adorsys.adpharma.client.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

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

	public static List<Series<String, BigDecimal>> toBarChartData(List<ChartData>  chartDatas){
		ArrayList<Series<String, BigDecimal>> series = new ArrayList<Series<String, BigDecimal>>();
		for (ChartData chartData : chartDatas) {
			Series<String, BigDecimal> series2 = new Series<String, BigDecimal>();
			series2.setName(chartData.getValue()+"");
			series2.getData().add(new XYChart.Data<String, BigDecimal>(chartData.getName(), chartData.getValue()));
			series.add(series2);
		}
		return series;
	}


}
