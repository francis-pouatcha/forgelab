package org.adorsys.adpharma.client.jpa.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemInventory;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemSearchInput;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemSearchResult;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemService;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

public class InventoryResultLoaderService extends Service<Inventory>{
	
	@Inject
	private InventoryService inventoryService ;

	@Inject
	private InventoryItemService inventoryItemService ;

	private Inventory entity;

	private List<InventoryItem> itemFromResult = new ArrayList<InventoryItem>();



	public InventoryResultLoaderService setInventory(Inventory entity)
	{
		this.entity = entity;
		return this;
	}

	public InventoryResultLoaderService setItemFromResult( List<InventoryItem> itemFromResul)
	{
		this.itemFromResult = itemFromResul;
		return this;
	}

	@Override
	protected Task<Inventory> createTask()
	{
		return new Task<Inventory>()
				{
			@Override
			protected Inventory call() throws Exception
			{
				if(entity == null) return null;
				if(itemFromResult == null) return null;
				 Iterator<InventoryItem> iterator = itemFromResult.iterator();
				 int j= 0 ;
				while (iterator.hasNext()) {
					System.out.println("j = "+j++);
					InventoryItem nextResult = iterator.next();
					InventoryItemSearchInput itemSearchInput = new InventoryItemSearchInput();
					itemSearchInput.getEntity().setInventory(new InventoryItemInventory(entity));
					itemSearchInput.getEntity().setInternalPic(nextResult.getInternalPic());
					itemSearchInput.getFieldNames().add("internalPic");
					itemSearchInput.getFieldNames().add("inventory");
					List<InventoryItem> itemSearchResult = inventoryItemService.findBy(itemSearchInput).getResultList() ;
					if(!itemSearchResult.isEmpty()){
						InventoryItem inventoryItem = itemSearchResult.iterator().next();
						inventoryItem.setAsseccedQty(nextResult.getAsseccedQty());
						InventoryItem update = inventoryItemService.update(inventoryItem);
					}
				}
				entity =  inventoryService.findById(entity.getId()) ;
				return entity;
			}
				};
	}
}
