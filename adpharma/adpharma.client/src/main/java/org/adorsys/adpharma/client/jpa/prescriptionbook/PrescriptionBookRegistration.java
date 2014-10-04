package org.adorsys.adpharma.client.jpa.prescriptionbook;

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
public class PrescriptionBookRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle(PrescriptionBook.class)
   private ResourceBundle resourceBundle;
   
   private MenuItem prescriptionBookReportMenuItem;
   
   @Inject
	@MenuItemAddRequestedEvent
	private Event<ReportMenuItem> prescriptionBookMenuItemAddEvent;
	@Inject
	@MenuItemRemoveRequestedEvent
	private Event<ReportMenuItem> prescriptionBookMenuItemRemoveEvent;
	
	@Inject
	@EntitySearchRequestedEvent
	private Event<PeriodicalPrescriptionBookDataSearchInput> prescriptionOrderInputRequestEvent;
	
	
	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		prescriptionBookReportMenuItem= new MenuItem(resourceBundle.getString("PrescriptionBook_repport_description.title"));
		prescriptionBookReportMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				prescriptionOrderInputRequestEvent.fire(new PeriodicalPrescriptionBookDataSearchInput());
				
			}
		});
		
	}
   

   @Override
   protected Class<?> getComponentClass()
   {
      return PrescriptionBook.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return PrescriptionBookController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = PrescriptionBook.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return PrescriptionBook.class.getName();
   }

   @Override
   public String getComponentPermission()
   {
      return "org.adorsys.adpharma.server.jpa.PrescriptionBook";
   }
   
   public void handleRolesEvent(@Observes(notifyObserver=Reception.ALWAYS) @RolesEvent Set<String> roles){
		if(roles.contains(AccessRoleEnum.MANAGER.name())){
			prescriptionBookMenuItemAddEvent.fire(new ReportMenuItem(prescriptionBookReportMenuItem));
		}else {
			prescriptionBookMenuItemRemoveEvent.fire(new ReportMenuItem(prescriptionBookReportMenuItem));
		}
   }

}
