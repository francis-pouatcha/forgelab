package org.adorsys.adpharma.client.jpa.procurementorder;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginService;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemProcurementOrder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemService;

public class ProcurementOrderReportPrinterDataService extends
Service<ProcurementOrderReportPrinterData> {

	@Inject
	private ProcurementOrderService poService;
	@Inject
	private LoginService loginService;
	@Inject
	private ProcurementOrderItemService poItemService;

	private ProcurementOrder po;

	public ProcurementOrderReportPrinterDataService setProcuremnetOrder(ProcurementOrder po) {
		this.po = po;
		return this;
	}

	@Override
	protected Task<ProcurementOrderReportPrinterData> createTask() {
		return new Task<ProcurementOrderReportPrinterData>() {
			@Override
			protected ProcurementOrderReportPrinterData call() throws Exception {
				if (po == null || po.getId()==null)
					return null;
				ProcurementOrderReportPrinterData result = null;
				po = poService.findById(po.getId());
				Login login = loginService.findById(po.getCreatingUser().getId());
				ProcurementOrderItem poItem = new ProcurementOrderItem();
				poItem.setProcurementOrder(new ProcurementOrderItemProcurementOrder(po));
				ProcurementOrderItemSearchInput poItemSearchInput = new ProcurementOrderItemSearchInput();
				poItemSearchInput.setEntity(poItem);
				poItemSearchInput.getFieldNames().add("procurementOrder");
				ProcurementOrderItemSearchResult poItemSearchResult = poItemService
						.findBy(poItemSearchInput);
				result = new ProcurementOrderReportPrinterData(po, login,
						poItemSearchResult);
				return result;
			}
		};
	}

}
