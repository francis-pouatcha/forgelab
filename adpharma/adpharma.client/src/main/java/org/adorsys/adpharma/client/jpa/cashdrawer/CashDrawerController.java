package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.events.ReportMenuItem;
import org.adorsys.adpharma.client.jpa.accessroleenum.AccessRoleEnum;
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
import org.adorsys.javafx.crud.extensions.login.RolesEvent;

@Singleton
public class CashDrawerController extends DomainComponentController
{

	@Inject
	private CashDrawerSearchController searchController;

	@Inject
	private CashDrawerCreateController createController;

	@Inject
	private CashDrawerEditController editController;

	@Inject
	private CashDrawerListController listController;

	@Inject
	private CashDrawerDisplayController displayController;

	@Inject
	private CashDrawerIntialScreenController intialScreenController;

	@Inject
	@CreateModelEvent
	private Event<CashDrawer> createModelEvent;
	@Inject
	@SearchModelEvent
	private Event<CashDrawer> searchModelEvent;
	@Inject
	@SelectedModelEvent
	private Event<CashDrawer> selectedModelEvent;

	private CashDrawer createModel = new CashDrawer();
	private CashDrawer searchModel = new CashDrawer();
	private CashDrawer selectedModel = new CashDrawer();

	@Inject
	private CashDrawerRegistration registration;

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
	public void handleSearchResult(@Observes @EntitySearchDoneEvent List<CashDrawer> entities)
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
	public void handleSelectionEvent(@Observes @EntitySelectionEvent CashDrawer selectedEntity)
	{
		if (!registration.canRead())
			return;
		// if result is empty: display no result.
		// else display list of cashDrawers.
		List<EntityController> displayedViews = getDisplayedViews();
		displayedViews.clear();
		//		       displayedViews.add(listController);
		displayedViews.add(displayController);

		displayComponent();
	}

	/**
	 * Display the search and list panel
	 * @param selectedEntity
	 */
	public void handleSearchRequestedEvent(@Observes @EntitySearchRequestedEvent CashDrawer selectedEntity)
	{
		if (!registration.canRead())
			return;
		List<EntityController> displayedViews = getDisplayedViews();
		displayedViews.clear();
		//		displayedViews.add(searchController);
		displayedViews.add(listController);
		displayComponent();
	}

	/**
	 * Display search form.
	 * @param selectedCashDrawer
	 */
	public void handleCreateRequestedEvent(@Observes @EntityCreateRequestedEvent CashDrawer templateEntity)
	{
		if (!registration.canCreate())
			return;
		List<EntityController> displayedViews = getDisplayedViews();
		displayedViews.clear();
		displayedViews.add(listController);
		displayedViews.add(createController);
		displayComponent();
	}

	public void handleEditRequestedEvent(@Observes @EntityEditRequestedEvent CashDrawer selectedEntity)
	{
		if (!registration.canEdit())
			return;
		List<EntityController> displayedViews = getDisplayedViews();
		displayedViews.clear();
		displayedViews.add(listController);
		displayedViews.add(editController);
		displayComponent();
	}

	public void handleEditCanceledEvent(@Observes @EntityEditCanceledEvent CashDrawer selectedEntity)
	{
		List<EntityController> displayedViews = getDisplayedViews();
		displayedViews.clear();
		displayedViews.add(listController);
		displayedViews.add(displayController);

		displayComponent();
	}

	public void handleEditDoneEvent(@Observes @EntityEditDoneEvent CashDrawer selectedEntity)
	{
		List<EntityController> displayedViews = getDisplayedViews();
		displayedViews.clear();
		displayedViews.add(listController);
		displayedViews.add(displayController);

		displayComponent();
	}

	public void handleRolesEvent(@Observes(notifyObserver=Reception.ALWAYS) @RolesEvent Set<String> roles)
	{
		List<EntityController> displayedViews = getDisplayedViews();
		displayedViews.clear();
		if(roles.contains(AccessRoleEnum.MANAGER.name())){
			displayedViews.add(listController);
			displayController.getDisplayView().getOpenCashDrawerButton().setDisable(false);
		}else {
			displayedViews.add(displayController);
			displayController.getDisplayView().getOpenCashDrawerButton().setDisable(true);
		}
	}

	@Override
	protected void selectDisplay()
	{
		if (displayController != null)
		{
			intialScreenController.startWithDisplay();
		}
		else if (createController != null)
		{
			intialScreenController.startWithCreate(); 
		}
		else if (searchController!=null) {
			intialScreenController.startWithSeach();
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
