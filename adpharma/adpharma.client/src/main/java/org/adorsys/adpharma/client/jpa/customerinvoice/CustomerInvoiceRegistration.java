package org.adorsys.adpharma.client.jpa.customerinvoice;

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

import org.adorsys.adpharma.client.events.CustomerInvoiceAgencyRepportMenuItem;
import org.adorsys.adpharma.client.jpa.accessroleenum.AccessRoleEnum;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawerReportPrintTemplate;
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
public class CustomerInvoiceRegistration extends DomainComponentRegistration
{

	@Inject
	@Bundle({CustomerInvoice.class ,CustomerInvoicePrintTemplate.class})
	private ResourceBundle resourceBundle;

	private MenuItem customerInvoiceAgencyRepportMenuItem;

	@Inject
	@MenuItemAddRequestedEvent
	private Event<CustomerInvoiceAgencyRepportMenuItem> customerInvoiceAgencyRepportMenuItemAddEvent;
	@Inject
	@MenuItemRemoveRequestedEvent
	private Event<CustomerInvoiceAgencyRepportMenuItem> customerInvoiceAgencyRepportMenuItemRemoveEvent;

	@Inject
	@EntitySearchRequestedEvent
	private Event<InvoiceByAgencyPrintInput> invoiceByAgencyPrintInputRequestEvent ;
 
	@Override
	protected Class<?> getComponentClass()
	{
		return CustomerInvoice.class;
	}

	@Override
	protected Class<? extends DomainComponentController> getControllerClass()
	{
		return CustomerInvoiceController.class;
	}

	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		customerInvoiceAgencyRepportMenuItem = new MenuItem(resourceBundle.getString("CustomerInvoicePrintTemplate_menuitem.title"));
		customerInvoiceAgencyRepportMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				invoiceByAgencyPrintInputRequestEvent.fire(new InvoiceByAgencyPrintInput());
			}
		});
	}

	@Override
	protected String getComponentI18nName()
	{
		Description annotation = CustomerInvoice.class.getAnnotation(Description.class);
		if (annotation != null)
			return resourceBundle.getString(annotation.value() + ".title");
		return CustomerInvoice.class.getName();
	}

	@Override
	public String getComponentPermission()
	{
		return "org.adorsys.adpharma.server.jpa.CustomerInvoice";
	}

	public void handleRolesEvent(@Observes(notifyObserver=Reception.ALWAYS) @RolesEvent Set<String> roles){
		if(roles.contains(AccessRoleEnum.MANAGER.name())){
			customerInvoiceAgencyRepportMenuItemAddEvent.fire(new CustomerInvoiceAgencyRepportMenuItem(customerInvoiceAgencyRepportMenuItem));
		} else {
			customerInvoiceAgencyRepportMenuItemAddEvent.fire(new CustomerInvoiceAgencyRepportMenuItem(customerInvoiceAgencyRepportMenuItem));
		}
	}

}
