package org.adorsys.adpharma.client.jpa.articlelot;

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
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoice.InvoiceByAgencyPrintInput;
import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.MenuItemAddRequestedEvent;
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
public class ArticleLotRegistration extends DomainComponentRegistration
{

	@Inject
	@Bundle(ArticleLot.class)
	private ResourceBundle resourceBundle;
	
	@Inject
	@EntitySearchRequestedEvent
	private Event<ArticleSearchInput> stockValueSearchRequestEvent ;
	
	@Inject
	@MenuItemAddRequestedEvent
	private Event<ReportMenuItem> stockValueRepportMenuItemAddEvent;

	private MenuItem stockValueMenuItem;
	
	@Override
	protected Class<?> getComponentClass()
	{
		return ArticleLot.class;
	}
	
	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		stockValueMenuItem = new MenuItem(resourceBundle.getString("ArticleLot_stockValue_description.title"));
		stockValueMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stockValueSearchRequestEvent.fire(new ArticleSearchInput());
			}
		});
	}

	@Override
	protected Class<? extends DomainComponentController> getControllerClass()
	{
		return ArticleLotController.class;
	}

	@Override
	protected String getComponentI18nName()
	{
		Description annotation = ArticleLot.class.getAnnotation(Description.class);
		if (annotation != null)
			return resourceBundle.getString(annotation.value() + ".title");
		return ArticleLot.class.getName();
	}

	@Override
	public String getComponentPermission()
	{
		return "org.adorsys.adpharma.server.jpa.ArticleLot";
	}
	
	public void handleRolesEvent(@Observes(notifyObserver=Reception.ALWAYS) @RolesEvent Set<String> roles){
		if(roles.contains(AccessRoleEnum.MANAGER.name())){
			stockValueRepportMenuItemAddEvent.fire(new ReportMenuItem(stockValueMenuItem));
		}
	}

}
