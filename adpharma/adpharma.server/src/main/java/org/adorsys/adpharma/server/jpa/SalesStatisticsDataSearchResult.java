package org.adorsys.adpharma.server.jpa;

import java.util.List;

import org.adorsys.adpharma.server.utils.ChartData;

public class SalesStatisticsDataSearchResult {

	private List<ChartData> chartData ;

	public List<ChartData> getChartData() {
		return chartData;
	}

	public void setChartData(List<ChartData> chartData) {
		this.chartData = chartData;
	}


}
