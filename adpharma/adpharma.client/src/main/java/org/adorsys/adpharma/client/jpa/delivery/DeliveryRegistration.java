package org.adorsys.adpharma.client.jpa.delivery;

import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.events.ReportMenuItem;
import org.adorsys.adpharma.client.jpa.accessroleenum.AccessRoleEnum;
import org.adorsys.adpharma.client.jpa.salesorder.PeriodicalDataSearchInput;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.MenuItemAddRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.MenuItemRemoveRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.login.RolesEvent;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
@Eager
@Singleton
public class DeliveryRegistration extends DomainComponentRegistration
{
	

	@Inject
	@Bundle(Delivery.class)
	private ResourceBundle resourceBundle;

	private MenuItem deliveryReportMenuItem;

	@Inject
	@MenuItemAddRequestedEvent
	private Event<ReportMenuItem> deliveryMenuItemAddEvent;
	@Inject
	@MenuItemRemoveRequestedEvent
	private Event<ReportMenuItem> deliveryMenuItemRemoveEvent;


	@Inject
	@EntitySearchRequestedEvent
	private Event<PeriodicalDeliveryDataSearchInput> deliveryInputRequestEvent ;


	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		deliveryReportMenuItem = new MenuItem(resourceBundle.getString("Delivery_repport_description.title"));
		deliveryReportMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				deliveryInputRequestEvent.fire(new PeriodicalDeliveryDataSearchInput());
			}
		});
	}

	@Override
	protected Class<?> getComponentClass()
	{
		return Delivery.class;
	}

	@Override
	protected Class<? extends DomainComponentController> getControllerClass()
	{
		return DeliveryController.class;
	}

	@Override
	protected String getComponentI18nName()
	{
		Description annotation = Delivery.class.getAnnotation(Description.class);
		if (annotation != null)
			return resourceBundle.getString(annotation.value() + ".title");
		return Delivery.class.getName();
	}

	@Override
	public String getComponentPermission()
	{
		return "org.adorsys.adpharma.server.jpa.Delivery";
	}
	
	public void handleRolesEvent(@Observes(notifyObserver=Reception.ALWAYS) @RolesEvent Set<String> roles){
		if(roles.contains(AccessRoleEnum.MANAGER.name())||roles.contains(AccessRoleEnum.PERIODICAL_DELIVERY_REPPORT_PERM.name())){
			deliveryMenuItemAddEvent.fire(new ReportMenuItem(deliveryReportMenuItem));
		}else {
			deliveryMenuItemRemoveEvent.fire(new ReportMenuItem(deliveryReportMenuItem));
		}
	}

}
