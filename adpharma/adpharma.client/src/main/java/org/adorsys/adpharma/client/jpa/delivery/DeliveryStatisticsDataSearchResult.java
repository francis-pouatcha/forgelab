package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;

import org.adorsys.adpharma.client.utils.ChartData;

public class DeliveryStatisticsDataSearchResult {
	private List<ChartData> chartData ;

	public List<ChartData> getChartData() {
		return chartData;
	}

	public void setChartData(List<ChartData> chartData) {
		this.chartData = chartData;
	}
}
