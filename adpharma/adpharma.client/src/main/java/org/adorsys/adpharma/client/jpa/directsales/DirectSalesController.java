package org.adorsys.adpharma.client.jpa.directsales;

import java.util.List;
import java.util.Map;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditCanceledEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SearchModelEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DirectSalesController extends DomainComponentController
{


   @Inject
   private DirectSalesDisplayController displayController;

   @Inject
   private DirectSalesIntialScreenController intialScreenController;

   @Inject
   @CreateModelEvent
   private Event<SalesOrder> createModelEvent;
   @Inject
   @SearchModelEvent
   private Event<DirectSales> searchModelEvent;
   @Inject
   @SelectedModelEvent
   private Event<DirectSales> selectedModelEvent;

   private DirectSales selectedModel = new DirectSales();

   @Inject
   private DirectSalesRegistration registration;

   @Override
   protected void initViews(Map<ViewType, EntityController> entityViews)
   {
      entityViews.put(displayController.getViewType(), displayController);
      selectedModelEvent.fire(selectedModel);
   }

//   /**
//    * Listen to search result and display.
//    * @param entities
//    */
//   public void handleSearchResult(@Observes @EntitySearchDoneEvent List<DirectSales> entities)
//   {
//      if (!registration.canRead())
//         return;
//
//      // if result is empty: display no result.
//      if (!getDisplayedViews().contains(displayController))
//      {
//         getDisplayedViews().add(displayController);
//      }
//
//      displayComponent();
//   }
//
//   /**
//    * Listens to list selection events and display
//    */
//   public void handleSelectionEvent(@Observes @EntitySelectionEvent DirectSales selectedEntity)
//   {
//      if (!registration.canRead())
//         return;
//      // if result is empty: display no result.
//      // else display list of vATs.
//      List<EntityController> displayedViews = getDisplayedViews();
//      displayedViews.clear();
//      displayedViews.add(displayController);
//
//      displayComponent();
//   }
//
//   /**
//    * Display the search and list panel
//    * @param selectedEntity
//    */
//   public void handleSearchRequestedEvent(@Observes @EntitySearchRequestedEvent DirectSales selectedEntity)
//   {
//      if (!registration.canRead())
//         return;
//      List<EntityController> displayedViews = getDisplayedViews();
//      displayedViews.clear();
//      displayedViews.add(displayController);
//      displayComponent();
//   }
//
//   /**
//    * Display search form.
//    * @param selectedVAT
//    */
//   public void handleCreateRequestedEvent(@Observes @EntityCreateRequestedEvent DirectSales templateEntity)
//   {
//      if (!registration.canCreate())
//         return;
//      List<EntityController> displayedViews = getDisplayedViews();
//      displayedViews.clear();
//      displayedViews.add(displayController);
//      displayComponent();
//   }
//
//   public void handleEditRequestedEvent(@Observes @EntityEditRequestedEvent DirectSales selectedEntity)
//   {
//      if (!registration.canEdit())
//         return;
//      List<EntityController> displayedViews = getDisplayedViews();
//      displayedViews.clear();
//      displayedViews.add(displayController);
//      displayComponent();
//   }
//
//   public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent DirectSales selectedEntity)
//   {
//      List<EntityController> displayedViews = getDisplayedViews();
//      displayedViews.clear();
//      displayedViews.add(displayController);
//
//      displayComponent();
//   }
//
//   public void handleEditDoneEvent(@Observes @EntityEditDoneEvent DirectSales selectedEntity)
//   {
//      List<EntityController> displayedViews = getDisplayedViews();
//      displayedViews.clear();
//      displayedViews.add(displayController);
//
//      displayComponent();
//   }

   @Override
   protected void selectDisplay()
   {
      if (displayController != null)
      {
         intialScreenController.startWithSeach();
      }
      else if (displayController != null)
      {
         intialScreenController.startWithCreate();
      }
      else
      {
         throw new IllegalStateException("Missing search and display component.");
      }
   }

	@Override
	public void reset() {
		displayController.reset();
	}
}
