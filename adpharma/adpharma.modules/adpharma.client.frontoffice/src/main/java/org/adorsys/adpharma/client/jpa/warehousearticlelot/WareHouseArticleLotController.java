package org.adorsys.adpharma.client.jpa.warehousearticlelot;

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
public class WareHouseArticleLotController extends DomainComponentController
{

	@Inject
	private WareHouseArticleLotSearchController searchController;

	@Inject
	private WareHouseArticleLotCreateController createController;

	@Inject
	private WareHouseArticleLotEditController editController;

	@Inject
	private WareHouseArticleLotListController listController;

	@Inject
	private WareHouseArticleLotDisplayController displayController;

	@Inject
	private WareHouseArticleLotIntialScreenController intialScreenController;

	@Inject
	@CreateModelEvent
	private Event<WareHouseArticleLot> createModelEvent;
	@Inject
	@SearchModelEvent
	private Event<WareHouseArticleLot> searchModelEvent;
	@Inject
	@SelectedModelEvent
	private Event<WareHouseArticleLot> selectedModelEvent;

	private WareHouseArticleLot createModel = new WareHouseArticleLot();
	private WareHouseArticleLot searchModel = new WareHouseArticleLot();
	private WareHouseArticleLot selectedModel = new WareHouseArticleLot();

	@Inject
	private WareHouseArticleLotRegistration registration;

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
	 public void handleSearchResult(@Observes @EntitySearchDoneEvent List<WareHouseArticleLot> entities)
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
	 public void handleSelectionEvent(@Observes @EntitySelectionEvent WareHouseArticleLot selectedEntity)
	 {
		 if (!registration.canRead())
			 return;
		 // if result is empty: display no result.
		 // else display list of WareHouses.
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
	 public void handleSearchRequestedEvent(@Observes @EntitySearchRequestedEvent WareHouseArticleLot selectedEntity)
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
	  * @param selectedWareHouse
	  */
	 public void handleCreateRequestedEvent(@Observes @EntityCreateRequestedEvent WareHouseArticleLot templateEntity)
	 {
		 if (!registration.canCreate())
			 return;
		 List<EntityController> displayedViews = getDisplayedViews();
		 displayedViews.clear();
		 displayedViews.add(listController);
		 //      displayedViews.add(createController);
		 displayComponent();
	 }

	 public void handleEditRequestedEvent(@Observes @EntityEditRequestedEvent WareHouseArticleLot selectedEntity)
	 {
		 if (!registration.canEdit())
			 return;
		 List<EntityController> displayedViews = getDisplayedViews();
		 displayedViews.clear();
		 displayedViews.add(listController);
		 //      displayedViews.add(editController);
		 displayComponent();
	 }

	 public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent WareHouseArticleLot selectedEntity)
	 {
		 List<EntityController> displayedViews = getDisplayedViews();
		 displayedViews.clear();
		 //      displayedViews.add(listController);
		 displayedViews.add(displayController);

		 displayComponent();
	 }

	 public void handleEditDoneEvent(@Observes @EntityEditDoneEvent WareHouseArticleLot selectedEntity)
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
