package org.adorsys.adpharma.client.reports;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.events.CustomerInvoiceAgencyRepportMenuItem;
import org.adorsys.adpharma.client.events.ReportMenuItem;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.javafx.crud.extensions.events.MenuAddRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.MenuItemAddRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.MenuItemRemoveRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.MenuRemoveRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;

import javafx.scene.control.Menu;

@Singleton
public class ReportsController {

	private Menu reportsMenu;
	
	@Inject
	@Bundle(ReportsController.class)
	private ResourceBundle resourceBundle;
	
	@Inject
	@MenuAddRequestedEvent
	private Event<Menu> menuAddRequestedEvent; 
	
	@Inject
	@MenuRemoveRequestedEvent
	private Event<Menu> menuRemoveRequestedEvent; 

	@PostConstruct
	public void postConstruct(){
		reportsMenu = new Menu(resourceBundle.getString("ReportsController_Menu_reports.title"));
	}
	
	public void handleMenuItemAddRequestedEvent(@Observes(notifyObserver=Reception.ALWAYS) @MenuItemAddRequestedEvent ReportMenuItem menuItem){
		if(!reportsMenu.getItems().contains(menuItem.getMenuItem())){
			reportsMenu.getItems().add(menuItem.getMenuItem());
			menuAddRequestedEvent.fire(reportsMenu);
		}
	}

	public void handleMenuItemRemoveRequestedEvent(@Observes @MenuItemRemoveRequestedEvent ReportMenuItem menuItem){
		reportsMenu.getItems().remove(menuItem.getMenuItem());
		if(reportsMenu.getItems().isEmpty()) 
			menuRemoveRequestedEvent.fire(reportsMenu);
	}
	

	public void handleMenuItemAdddRequestedEvent(@Observes(notifyObserver=Reception.ALWAYS) @MenuItemAddRequestedEvent CustomerInvoiceAgencyRepportMenuItem menuItem){
		if(!reportsMenu.getItems().contains(menuItem.getMenuItem())){
			reportsMenu.getItems().add(menuItem.getMenuItem());
			menuAddRequestedEvent.fire(reportsMenu);
		}
	}

	public void handleMenuItemRemovdeRequestedEvent(@Observes @MenuItemRemoveRequestedEvent CustomerInvoiceAgencyRepportMenuItem menuItem){
		reportsMenu.getItems().remove(menuItem.getMenuItem());
		if(reportsMenu.getItems().isEmpty()) 
			menuRemoveRequestedEvent.fire(reportsMenu);
	}
}
