package org.adorsys.adpharma.client.jpa.procurementorder;

import java.math.BigDecimal;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemProcurementOrder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemService;
import org.adorsys.adpharma.client.utils.PhmlOrderReceiver;
import org.adorsys.adpharma.client.utils.PhmlOrderSender;

public class PhmlSendAndReceiveService extends Service<ProcurementOrder>
{

	@Inject
	private PhmlOrderSender phmlOrderSender;

	@Inject
	private PhmlOrderReceiver phmlOrderReceiver ;

	@Inject ProcurementOrderService procurementOrderService ;

	@Inject private ProcurementOrderItemService procurementOrderItemService ;

	private ProcurementOrder entity;
	
	private String repartiteur ;

	private boolean toBeSent = false ;

	public boolean isToBeSent() {
		return toBeSent;
	}

	public PhmlSendAndReceiveService setProcurementOrder(ProcurementOrder entity)
	{
		this.entity = entity;
		return this;
	}
	
	public PhmlSendAndReceiveService setRepartiter(String repartiter)
	{
		this.repartiteur = repartiter;
		return this;
	}

	public PhmlSendAndReceiveService setToBeSent(boolean toBeSent)
	{
		this.toBeSent = toBeSent;
		return this;
	}

	@Override
	protected Task<ProcurementOrder> createTask()
	{
		return new Task<ProcurementOrder>()
				{
			@Override
			protected ProcurementOrder call() throws Exception
			{
				entity = procurementOrderService.findById(entity.getId());
				if (entity == null)
					return null;
				if(toBeSent){
					if(DocumentProcessingState.ONGOING.equals(entity.getPoStatus())){
						phmlOrderSender.sendToPhml(entity,repartiteur);
						entity.setPoStatus(DocumentProcessingState.SENT);
						entity = procurementOrderService.update(entity);
					}
					return entity;
				}else {
					if(DocumentProcessingState.SENT.equals(entity.getPoStatus())){
						List<ProcurementOrderItem> receiveFromPhml = phmlOrderReceiver.receiveFromPhml(entity);
						for (ProcurementOrderItem item : receiveFromPhml) {
							ProcurementOrderItemSearchInput itemSearchInput = new ProcurementOrderItemSearchInput();
							itemSearchInput.getEntity().setMainPic(item.getMainPic());
							itemSearchInput.getEntity().setProcurementOrder(new ProcurementOrderItemProcurementOrder(entity));
							itemSearchInput.getFieldNames().add("mainPic");
							itemSearchInput.getFieldNames().add("procurementOrder");
							List<ProcurementOrderItem> itemSearchResult = procurementOrderItemService.findByLike(itemSearchInput).getResultList();
							if(!itemSearchResult.isEmpty()){
								ProcurementOrderItem orderItem = itemSearchResult.iterator().next();
								orderItem.setAvailableQty(item.getAvailableQty());
								orderItem.setPurchasePricePU(item.getPurchasePricePU()!=null?item.getPurchasePricePU():orderItem.getPurchasePricePU());
								procurementOrderItemService.update(orderItem);
							}
						}
						entity.setPoStatus(DocumentProcessingState.RETREIVED);
						entity = procurementOrderService.update(entity);
					} 
				}
				return null ;
			}
				};
	}

}
