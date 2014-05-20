package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;

import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;


public interface DeliveryReportPrintTemplate {

	public void addItems(List<DeliveryItem> resultList);

	public void closeReport();
}
