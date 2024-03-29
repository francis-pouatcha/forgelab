package org.adorsys.adpharma.client.jpa.rolename;

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
public class RoleNameController extends DomainComponentController
{

   @Inject
   private RoleNameSearchController searchController;

   @Inject
   private RoleNameCreateController createController;

   @Inject
   private RoleNameEditController editController;

   @Inject
   private RoleNameListController listController;

   @Inject
   private RoleNameDisplayController displayController;

   @Inject
   private RoleNameIntialScreenController intialScreenController;

   @Inject
   @CreateModelEvent
   private Event<RoleName> createModelEvent;
   @Inject
   @SearchModelEvent
   private Event<RoleName> searchModelEvent;
   @Inject
   @SelectedModelEvent
   private Event<RoleName> selectedModelEvent;

   private RoleName createModel = new RoleName();
   private RoleName searchModel = new RoleName();
   private RoleName selectedModel = new RoleName();

   @Inject
   private RoleNameRegistration registration;

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
   public void handleSearchResult(@Observes @EntitySearchDoneEvent List<RoleName> entities)
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
   public void handleSelectionEvent(@Observes @EntitySelectionEvent RoleName selectedEntity)
   {
      if (!registration.canRead())
         return;
      // if result is empty: display no result.
      // else display list of roleNames.
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
   public void handleSearchRequestedEvent(@Observes @EntitySearchRequestedEvent RoleName selectedEntity)
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
    * @param selectedRoleName
    */
   public void handleCreateRequestedEvent(@Observes @EntityCreateRequestedEvent RoleName templateEntity)
   {
      if (!registration.canCreate())
         return;
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
//      displayedViews.add(listController);
      displayedViews.add(createController);
      displayComponent();
   }

   public void handleEditRequestedEvent(@Observes @EntityEditRequestedEvent RoleName selectedEntity)
   {
      if (!registration.canEdit())
         return;
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
//      displayedViews.add(listController);
      displayedViews.add(editController);
      displayComponent();
   }

   public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent RoleName selectedEntity)
   {
      List<EntityController> displayedViews = getDisplayedViews();
      displayedViews.clear();
      displayedViews.add(listController);
//      displayedViews.add(displayController);

      displayComponent();
   }

   public void handleEditDoneEvent(@Observes @EntityEditDoneEvent RoleName selectedEntity)
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
