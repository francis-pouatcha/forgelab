package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;

import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;

public interface DeliveryReportPrintTemplate {
	
	public void closeReport();
	
	public void addItems(List<DeliveryItem> deliveryItems );

}
