package org.adorsys.adpharma.client.jpa.inventory;

import java.awt.Desktop;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.events.PrintRequestedEvent;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemInventory;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemSearchInput;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemSearchResult;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItemSearchService;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;

import com.google.common.collect.Lists;

@Singleton
public class InventoryRepportPrinter {

	@Inject
	private SecurityUtil securityUtil;

	@Inject
	private InventoryRepportData inventoryRepportData ;

	@Inject
	private InventoryItemSearchService itemSearchService ;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler ;



	@PostConstruct
	public void postConstruct(){
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});

		itemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				InventoryItemSearchService source = (InventoryItemSearchService) event.getSource();
				InventoryItemSearchResult searchResult = source.getValue();
				inventoryRepportData.getInventory().setInventoryItems(searchResult.getResultList());
				event.consume();
				source.reset();
				printInventoryRepport(inventoryRepportData.getInventory());

			}
		});
		itemSearchService.setOnFailed(callFailedEventHandler);
	}


	public void printInventoryRepport(Inventory inventory){
		if(inventory!=null){
			Login login = securityUtil.getConnectedUser();
			LoginAgency agency = securityUtil.getAgency();
			List<InventoryItem> items = inventory.getInventoryItems();
			try {
				InventoryComptRepportTemplatePdf repportTemplatePdf = new InventoryComptRepportTemplatePdf(login, agency, inventoryRepportData);
				repportTemplatePdf.addItems(items);
				repportTemplatePdf.closeDocument();
				Desktop.getDesktop().open(new File(repportTemplatePdf.getFileName()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void handleInventoryRepportPrintRequest(@Observes @PrintRequestedEvent InventoryRepportData model){
		PropertyReader.copy(model, inventoryRepportData);
		InventoryItemSearchInput searchInput = new InventoryItemSearchInput();
		searchInput.getEntity().setInventory(new InventoryItemInventory(inventoryRepportData.getInventory()));
		searchInput.getFieldNames().add("inventory");
		searchInput.setMax(-1);
		itemSearchService.setSearchInputs(searchInput).start();
	}
}
