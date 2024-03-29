package org.adorsys.adpharma.client.jpa.disbursement;

import java.util.List;
import java.util.Map;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.EntityCreateRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SearchModelEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

public class DisbursementController extends DomainComponentController
{


	@Inject
	private DisbursementCreateController createController;
	
//	@Inject
//	private CashOutSearchController searchController;
//
//	@Inject
//	private CashOutEditController editController;
//
//	@Inject
//	private CashOutListController listController;
//
//	@Inject
//	private CashOutDisplayController displayController;
//
	@Inject
	private DisbursementIntialScreenController intialScreenController;

	@Inject
	@CreateModelEvent
	private Event<Disbursement> createModelEvent;
	@Inject
	@SearchModelEvent
	private Event<Disbursement> searchModelEvent;
	@Inject
	@SelectedModelEvent
	private Event<Disbursement> selectedModelEvent;

	private Disbursement createModel = new Disbursement();
//	private CashOut searchModel = new CashOut();
//	private CashOut selectedModel = new CashOut();

	@Inject
	private DisbursementRegistration registration;

	@Override
	protected void initViews(Map<ViewType, EntityController> entityViews)
	{
//		entityViews.put(searchController.getViewType(), searchController);
//		entityViews.put(listController.getViewType(), listController);
//		entityViews.put(displayController.getViewType(), displayController);
//		entityViews.put(editController.getViewType(), editController);
		entityViews.put(createController.getViewType(), createController);
		createModelEvent.fire(createModel);
//		searchModelEvent.fire(searchModel);
//		selectedModelEvent.fire(selectedModel);
	}

	/**
	 * Listen to search result and display.
	 * @param entities
	 */
	 public void handleSearchResult(@Observes @EntitySearchDoneEvent List<Disbursement> entities)
	 {
		 if (!registration.canRead())
			 return;

		 // if result is empty: display no result.
		 if (!getDisplayedViews().contains(createController))
		 {
			 getDisplayedViews().add(createController);
		 }

		 displayComponent();
	 }

	 /**
	  * Listens to list selection events and display
	  */
	 public void handleSelectionEvent(@Observes @EntitySelectionEvent Disbursement selectedEntity)
	 {
		 if (!registration.canRead())
			 return;
		 // if result is empty: display no result.
		 // else display list of CashOuts.
		 List<EntityController> displayedViews = getDisplayedViews();
		 displayedViews.clear();
		 //      displayedViews.add(listController);
		 displayedViews.add(createController);

		 displayComponent();
	 }

	 /**
	  * Display the search and list panel
	  * @param selectedEntity
	  */
	 public void handleSearchRequestedEvent(@Observes @EntitySearchRequestedEvent Disbursement selectedEntity)
	 {
		 if (!registration.canRead())
			 return;
		 List<EntityController> displayedViews = getDisplayedViews();
		 displayedViews.clear();
		 //      displayedViews.add(searchController);
		 displayedViews.add(createController);
		 displayComponent();
	 }

	 /**
	  * Display search form.
	  * @param selectedCashOut
	  */
	 public void handleCreateRequestedEvent(@Observes @EntityCreateRequestedEvent Disbursement templateEntity)
	 {
		 if (!registration.canCreate())
			 return;
		 List<EntityController> displayedViews = getDisplayedViews();
		 displayedViews.clear();
		 displayedViews.add(createController);
		 displayedViews.add(createController);
		 displayComponent();
	 }

	 //   public void handleEditRequestedEvent(@Observes @EntityEditRequestedEvent CashOut selectedEntity)
	 //   {
	 //      if (!registration.canEdit())
	 //         return;
	 //      List<EntityController> displayedViews = getDisplayedViews();
	 //      displayedViews.clear();
	 //      displayedViews.add(listController);
	 //      displayedViews.add(editController);
	 //      displayComponent();
	 //   }

	 //   public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent CashOut selectedEntity)
	 //   {
	 //      List<EntityController> displayedViews = getDisplayedViews();
	 //      displayedViews.clear();
	 //      displayedViews.add(listController);
	 //      displayedViews.add(displayController);
	 //
	 //      displayComponent();
	 //   }

	 //   public void handleEditDoneEvent(@Observes @EntityEditDoneEvent CashOut selectedEntity)
	 //   {
	 //      List<EntityController> displayedViews = getDisplayedViews();
	 //      displayedViews.clear();
	 //      displayedViews.add(listController);
	 //      displayedViews.add(displayController);
	 //
	 //      displayComponent();
	 //   }

	 @Override
	 protected void selectDisplay()
	 {
		 if (createController != null)
		 {
			 intialScreenController.startWithCreate();
		 }
		 
		 else
		 {
			 throw new IllegalStateException("Missing create  component.");
		 }
	 }


		@Override
		public void reset() {
		}
}
