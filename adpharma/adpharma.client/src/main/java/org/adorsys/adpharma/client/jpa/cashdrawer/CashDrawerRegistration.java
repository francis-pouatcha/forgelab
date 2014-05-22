package org.adorsys.adpharma.client.jpa.cashdrawer;

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

import org.adorsys.adpharma.client.events.CashDrawerListMenuItem;
import org.adorsys.adpharma.client.events.CashDrawerPrintRequest;
import org.adorsys.adpharma.client.events.PrintRequestedEvent;
import org.adorsys.adpharma.client.events.ReportMenuItem;
import org.adorsys.adpharma.client.jpa.accessroleenum.AccessRoleEnum;
import org.adorsys.adpharma.client.utils.AdTimeFrame;
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
 * @author francis pouatcha
 *
 */
@Eager
@Singleton
public class CashDrawerRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle({CashDrawer.class, CashDrawerReportPrintTemplate.class})
   private ResourceBundle resourceBundle;
   
   private MenuItem cashDrawerReportMenuItem;
   
   private MenuItem cashDrawerListmenuItem ;
   @Inject
   @MenuItemAddRequestedEvent
   private Event<ReportMenuItem> cashDrawerReportMenuItemAddEvent;
   @Inject
   @MenuItemRemoveRequestedEvent
   private Event<ReportMenuItem> cashDrawerReportMenuItemRemoveEvent;
   
   @Inject
   @MenuItemAddRequestedEvent
   private Event<CashDrawerListMenuItem> cashDrawerListMenuItemAddEvent;
   @Inject
   @MenuItemRemoveRequestedEvent
   private Event<CashDrawerListMenuItem> cashDrawerListMenuItemRemoveEvent;
   
   @Inject
   @PrintRequestedEvent
   private Event<CashDrawerPrintRequest> cashDrawerPrintRequestEvent;
   
   @Inject
   @EntitySearchRequestedEvent
   private Event<CashDrawer>   cashDrawerSearchRequestEvent ;

   @Override
   protected Class<?> getComponentClass()
   {
      return CashDrawer.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return CashDrawerController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = CashDrawer.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return CashDrawer.class.getName();
   }

   @Override
   public String getComponentPermission()
   {
      return "org.adorsys.adpharma.server.jpa.CashDrawer";
   }
   
   @PostConstruct
   public void postConstruct(){
	   super.postConstruct();
	   cashDrawerReportMenuItem = new MenuItem(resourceBundle.getString("CashDrawerReportPrintTemplate_menuItem.title"));
	   cashDrawerReportMenuItem.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			cashDrawerPrintRequestEvent.fire(new CashDrawerPrintRequest(new AdTimeFrame()));
		}
	   });
	   
	   cashDrawerReportMenuItem = new MenuItem(resourceBundle.getString("CashDrawer_Menu_Item_list.title"));
	   cashDrawerReportMenuItem.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent event) {
			cashDrawerSearchRequestEvent.fire(new CashDrawer());
		}         
	   });
   }

   public void handleRolesEvent(@Observes(notifyObserver=Reception.ALWAYS) @RolesEvent Set<String> roles){
	   if(roles.contains(AccessRoleEnum.MANAGER.name())){
		   cashDrawerReportMenuItemAddEvent.fire(new ReportMenuItem(cashDrawerReportMenuItem));
		   cashDrawerListMenuItemAddEvent.fire(new CashDrawerListMenuItem(cashDrawerListmenuItem));
	   } else {
		   cashDrawerReportMenuItemRemoveEvent.fire(new ReportMenuItem(cashDrawerReportMenuItem));
		   cashDrawerListMenuItemRemoveEvent.fire(new CashDrawerListMenuItem(cashDrawerListmenuItem));
	   }
   }
}
