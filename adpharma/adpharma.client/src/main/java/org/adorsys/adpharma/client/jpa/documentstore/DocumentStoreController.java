package org.adorsys.adpharma.client.jpa.documentstore;

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
public class DocumentStoreController extends DomainComponentController
{

   @Inject
   private DocumentStoreSearchController searchController;

   @Inject
   private DocumentStoreCreateController createController;

   @Inject
   private DocumentStoreEditController editController;

   @Inject
   private DocumentStoreListController listController;

   @Inject
   private DocumentStoreDisplayController displayController;

   @Inject
   private DocumentStoreIntialScreenController intialScreenController;

   @Inject
   @CreateModelEvent
   private Event<DocumentStore> createModelEvent;
   @Inject
   @SearchModelEvent
   private Event<DocumentStore> searchModelEvent;
   @Inject
   @SelectedModelEvent
   private Event<DocumentStore> selectedModelEvent;

   private DocumentStore createModel = new DocumentStore();
   private DocumentStore searchModel = new DocumentStore();
   private DocumentStore selectedModel = new DocumentStore();

   @Inject
   private DocumentStoreRegistration registration;

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
   public void handleSearchResult(@Observes @EntitySearchDoneEvent List<DocumentStore> entities)
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
   public void handleSelectionEvent(@Observes @EntitySelectionEvent DocumentStore selectedEntity)
   {
      if (!registration.canRead())
         return;
      // if result is empty: display no result.
      // else display list of documentStores.
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
      displayedViews.add(listController);
      displayedViews.add(displayController);

      displayComponent();
   }

   /**
    * Display the search and list panel
    * @param selectedEntity
    */
   public void handleSearchRequestedEvent(@Observes @EntitySearchRequestedEvent DocumentStore selectedEntity)
   {
      if (!registration.canRead())
         return;
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
      displayedViews.add(searchController);
      displayedViews.add(listController);
      displayComponent();
   }

   /**
    * Display search form.
    * @param selectedDocumentStore
    */
   public void handleCreateRequestedEvent(@Observes @EntityCreateRequestedEvent DocumentStore templateEntity)
   {
      if (!registration.canCreate())
         return;
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
      displayedViews.add(listController);
      displayedViews.add(createController);
      displayComponent();
   }

   public void handleEditRequestedEvent(@Observes @EntityEditRequestedEvent DocumentStore selectedEntity)
   {
      if (!registration.canEdit())
         return;
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
      displayedViews.add(listController);
      displayedViews.add(editController);
      displayComponent();
   }

   public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent DocumentStore selectedEntity)
   {
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
      displayedViews.add(listController);
      displayedViews.add(displayController);

      displayComponent();
   }

   public void handleEditDoneEvent(@Observes @EntityEditDoneEvent DocumentStore selectedEntity)
   {
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
      displayedViews.add(listController);
      displayedViews.add(displayController);

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
