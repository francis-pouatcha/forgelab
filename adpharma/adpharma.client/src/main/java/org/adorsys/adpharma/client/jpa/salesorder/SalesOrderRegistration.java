package org.adorsys.adpharma.client.jpa.salesorder;

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
import org.adorsys.adpharma.client.jpa.customerinvoice.InvoiceByAgencyPrintInput;
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
public class SalesOrderRegistration extends DomainComponentRegistration
{

	@Inject
	@Bundle(SalesOrder.class)
	private ResourceBundle resourceBundle;

	private MenuItem salesReportMenuItem;

	@Inject
	@MenuItemAddRequestedEvent
	private Event<ReportMenuItem> salesMenuItemAddEvent;
	@Inject
	@MenuItemRemoveRequestedEvent
	private Event<ReportMenuItem> salesMenuItemRemoveEvent;


	@Inject
	@EntitySearchRequestedEvent
	private Event<PeriodicalDataSearchInput> salesInputRequestEvent ;


	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		salesReportMenuItem = new MenuItem(resourceBundle.getString("SalesOrder_repport_description.title"));
		salesReportMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				salesInputRequestEvent.fire(new PeriodicalDataSearchInput());
			}
		});
	}

	@Override
	protected Class<?> getComponentClass()
	{
		return SalesOrder.class;
	}

	@Override
	protected Class<? extends DomainComponentController> getControllerClass()
	{
		return SalesOrderController.class;
	}

	@Override
	protected String getComponentI18nName()
	{
		Description annotation = SalesOrder.class.getAnnotation(Description.class);
		if (annotation != null)
			return resourceBundle.getString(annotation.value() + ".title");
		return SalesOrder.class.getName();
	}

	@Override
	public String getComponentPermission()
	{
		return "org.adorsys.adpharma.server.jpa.SalesOrder";
	}
	
	public void handleRolesEvent(@Observes(notifyObserver=Reception.ALWAYS) @RolesEvent Set<String> roles){
		if(roles.contains(AccessRoleEnum.MANAGER.name())){
			salesMenuItemAddEvent.fire(new ReportMenuItem(salesReportMenuItem));
		}else {
			salesMenuItemRemoveEvent.fire(new ReportMenuItem(salesReportMenuItem));
		}
	}

}
