package org.adorsys.adpharma.client.jpa.inventory;

import java.util.List;
import java.util.Map;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateDoneEvent;
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
public class InventoryController extends DomainComponentController
{

   @Inject
   private InventorySearchController searchController;

   @Inject
   private InventoryCreateController createController;

   @Inject
   private InventoryEditController editController;

   @Inject
   private InventoryListController listController;

   @Inject
   private InventoryDisplayController displayController;

   @Inject
   private InventoryIntialScreenController intialScreenController;

   @Inject
   @CreateModelEvent
   private Event<Inventory> createModelEvent;
   @Inject
   @SearchModelEvent
   private Event<Inventory> searchModelEvent;
   @Inject
   @SelectedModelEvent
   private Event<Inventory> selectedModelEvent;

   private Inventory createModel = new Inventory();
   private Inventory searchModel = new Inventory();
   private Inventory selectedModel = new Inventory();

   @Inject
   private InventoryRegistration registration;

   @Override
   protected void initViews(Map<ViewType, EntityController> entityViews)
   {
      entityViews.put(searchController.getViewType(), searchController);
      entityViews.put(listController.getViewType(), listController);
      entityViews.put(displayController.getViewType(), displayController);
      entityViews.put(editController.getViewType(), editController);
      entityViews.put(createController.getViewType(), createController);
      createModelEvent.fire(createModel);
      searchModelEvent.fire(searchModel);
      selectedModelEvent.fire(selectedModel);
   }

   /**
    * Listen to search result and display.
    * @param entities
    */
   public void handleSearchResult(@Observes @EntitySearchDoneEvent List<Inventory> entities)
   {
      if (!registration.canRead())
         return;

      // if result is empty: display no result.
      if (!getDisplayedViews().contains(listController))
      {
         getDisplayedViews().add(listController);
      }

      displayComponent();
   }

   /**
    * Listens to list selection events and display
    */
   public void handleSelectionEvent(@Observes @EntitySelectionEvent Inventory selectedEntity)
   {
      if (!registration.canRead())
         return;
      // if result is empty: display no result.
      // else display list of inventorys.
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
//      displayedViews.add(listController);
      displayedViews.add(displayController);

      displayComponent();
   }

   /**
    * Display the search and list panel
    * @param selectedEntity
    */
   public void handleSearchRequestedEvent(@Observes @EntitySearchRequestedEvent Inventory selectedEntity)
   {
      if (!registration.canRead())
         return;
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
//      displayedViews.add(searchController);
      displayedViews.add(listController);
      displayComponent();
   }

   /**
    * Display search form.
    * @param selectedInventory
    */
   public void handleCreateRequestedEvent(@Observes @EntityCreateRequestedEvent Inventory templateEntity)
   {
      if (!registration.canCreate())
         return;
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
//      displayedViews.add(listController);
      displayedViews.add(createController);
      displayComponent();
   }

   public void handleEditRequestedEvent(@Observes @EntityEditRequestedEvent Inventory selectedEntity)
   {
      if (!registration.canEdit())
         return;
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
//      displayedViews.add(listController);
      displayedViews.add(editController);
      displayComponent();
   }

   public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent Inventory selectedEntity)
   {
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
      displayedViews.add(listController);
//      displayedViews.add(displayController);

      displayComponent();
   }

   public void handleEditDoneEvent(@Observes @EntityEditDoneEvent Inventory selectedEntity)
   {
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
      displayedViews.add(listController);
//      displayedViews.add(displayController);

      displayComponent();
   }

   public void handleCreateDoneEvent(@Observes @EntityCreateDoneEvent Inventory selectedEntity)
   {
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
      displayedViews.add(listController);
//      displayedViews.add(displayController);

      displayComponent();
   }
   
   @Override
   protected void selectDisplay()
   {
      if (searchController != null)
      {
         intialScreenController.startWithSeach();
      }
      else if (createController != null)
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
		editController.reset();
		searchController.reset();
		listController.reset();
	}
}
