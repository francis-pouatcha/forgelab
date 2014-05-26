package org.adorsys.adpharma.client.data;

import java.util.ResourceBundle;

import javafx.scene.control.Menu;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.events.DataMenuItem;
import org.adorsys.javafx.crud.extensions.events.MenuAddRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.MenuItemAddRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.MenuItemRemoveRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.MenuRemoveRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class DataController {

	private Menu dataMenu;
	
	@Inject
	@Bundle(DataController.class)
	private ResourceBundle resourceBundle;
	
	@Inject
	@MenuAddRequestedEvent
	private Event<Menu> menuAddRequestedEvent; 
	
	@Inject
	@MenuRemoveRequestedEvent
	private Event<Menu> menuRemoveRequestedEvent; 

	@PostConstruct
	public void postConstruct(){
		dataMenu = new Menu(resourceBundle.getString("DataController_Menu_data.title"));
	}
	
	public void handleMenuItemAddRequestedEvent(@Observes(notifyObserver=Reception.ALWAYS) @MenuItemAddRequestedEvent DataMenuItem menuItem){
		if(!dataMenu.getItems().contains(menuItem.getMenuItem())){
			dataMenu.getItems().add(menuItem.getMenuItem());
			menuAddRequestedEvent.fire(dataMenu);
		}
	}

	public void handleMenuItemRemoveRequestedEvent(@Observes @MenuItemRemoveRequestedEvent DataMenuItem menuItem){
		dataMenu.getItems().remove(menuItem.getMenuItem());
		if(dataMenu.getItems().isEmpty()) 
			menuRemoveRequestedEvent.fire(dataMenu);
	}
}
