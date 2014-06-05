package org.adorsys.adpharma.client.jpa.procurementorder;

import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchResult;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchResult;

public class ProcurementOrderReportPrinterData {
	private final ProcurementOrder procurementOrder;
	private final Login login;
	private ProcurementOrderItemSearchResult procurementOrderItemSearchResult;

	public ProcurementOrderReportPrinterData(ProcurementOrder procurementOrder,
			Login login, ProcurementOrderItemSearchResult procurementOrderItemSearchResult) {
		super();
		this.procurementOrder = procurementOrder;
		this.login = login;
		this.procurementOrderItemSearchResult = procurementOrderItemSearchResult;
	}

	public Login getLogin() {
		return login;
	}

	public ProcurementOrder getProcurementOrder() {
		return procurementOrder;
	}

	public ProcurementOrderItemSearchResult getProcurementOrderItemSearchResult() {
		return procurementOrderItemSearchResult;
	}

	public void setProcurementOrderItemSearchResult(
			ProcurementOrderItemSearchResult procurementOrderItemSearchResult) {
		this.procurementOrderItemSearchResult = procurementOrderItemSearchResult;
	}
	
}
