package org.adorsys.adpharma.client.jpa.procurementorder;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.EntityController;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionEventData;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.AssocSelectionResponseEvent;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestData;
import org.adorsys.javafx.crud.extensions.events.ComponentSelectionRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntityRemoveRequestEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemDelivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchInput;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchResult;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemSearchService;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemProcurementOrder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchResult;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchService;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class ProcurementOrderDisplayController implements EntityController
{

	@Inject
	private ProcurementOrderDisplayView displayView;

	@Inject
	@EntitySearchRequestedEvent
	private Event<ProcurementOrder> searchRequestedEvent;

	@Inject
	@EntityEditRequestedEvent
	private Event<ProcurementOrder> editRequestEvent;

	@Inject
	@EntityRemoveRequestEvent
	private Event<ProcurementOrder> removeRequest;
	
	@Inject
	@AssocSelectionResponseEvent
	private Event<AssocSelectionEventData<ProcurementOrder>> selectionResponseEvent;

	private ObjectProperty<AssocSelectionEventData<ProcurementOrder>> pendingSelectionRequestProperty = new SimpleObjectProperty<AssocSelectionEventData<ProcurementOrder>>();

	@Inject
	@ComponentSelectionRequestEvent
	private Event<ComponentSelectionRequestData> componentSelectionRequestEvent;

	@Inject
	private ProcurementOrderItemSearchService itemSearchService;

	@Inject
	private ServiceCallFailedEventHandler serviceCallFailedEventHandler;

	@Inject
	private ProcurementOrder displayedEntity;

	@Inject
	private ProcurementOrderRegistration registration;

	@Inject
	private ProcurementOrderItem item ;

	@PostConstruct
	public void postConstruct()
	{
		displayView.bind(item);
		displayView.bind(displayedEntity);
		serviceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});
		
		itemSearchService.setOnFailed(serviceCallFailedEventHandler);

		itemSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
				{
			@Override
			public void handle(WorkerStateEvent event)
			{
				ProcurementOrderItemSearchService s = (ProcurementOrderItemSearchService) event.getSource();
				ProcurementOrderItemSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				List<ProcurementOrderItem> resultList = searchResult.getResultList();
				displayedEntity.setProcurementOrderItems(resultList);
			}
				});
		//      displayView.getEditButton().disableProperty().bind(registration.canEditProperty().not());
		//      displayView.getRemoveButton().disableProperty().bind(registration.canEditProperty().not());
		//
		//      /*
		//       * listen to search button and fire search requested event.
		//       */
		//      displayView.getSearchButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            searchRequestedEvent.fire(displayedEntity);
		//         }
		//      });
		//
		      displayView.getCancelButton().setOnAction(new EventHandler<ActionEvent>()
		      {
		         @Override
		         public void handle(ActionEvent e)
		         {
		            searchRequestedEvent.fire(displayedEntity);
		         }
		      });
		
		//
		//      displayView.getConfirmSelectionButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            final AssocSelectionEventData<ProcurementOrder> pendingSelectionRequest = pendingSelectionRequestProperty.get();
		//            if (pendingSelectionRequest == null)
		//               return;
		//            pendingSelectionRequestProperty.set(null);
		//            pendingSelectionRequest.setTargetEntity(displayedEntity);
		//            selectionResponseEvent.fire(pendingSelectionRequest);
		//         }
		//      });
		//
		//      displayView.getConfirmSelectionButton().visibleProperty().bind(pendingSelectionRequestProperty.isNotNull());
	}

	public void display(Pane parent)
	{
		BorderPane rootPane = displayView.getRootPane();
		ObservableList<Node> children = parent.getChildren();
		if (!children.contains(rootPane))
		{
			children.add(rootPane);
		}
	}

	@Override
	public ViewType getViewType()
	{
		return ViewType.DISPLAY;
	}

	/**
	 * Listens to list selection events and bind selected to pane
	 */
	public void handleSelectionEvent(@Observes @EntitySelectionEvent ProcurementOrder selectedEntity)
	{
		PropertyReader.copy(selectedEntity, displayedEntity);
		itemSearchService.setSearchInputs(getSearchInput(displayedEntity)).start();
	}

	//   public void handleAssocSelectionRequest(@Observes(notifyObserver = Reception.ALWAYS) @AssocSelectionRequestEvent AssocSelectionEventData<ProcurementOrder> eventData)
	//   {
	//      pendingSelectionRequestProperty.set(eventData);
	//      componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(ProcurementOrder.class.getName()));
	//      searchRequestedEvent.fire(eventData.getTargetEntity() != null ? eventData.getTargetEntity() : new ProcurementOrder());
	//   }
	
	public ProcurementOrderItemSearchInput getSearchInput(ProcurementOrder template){
		ProcurementOrderItemSearchInput searchInput = new ProcurementOrderItemSearchInput();
		searchInput.setMax(-1);
		searchInput.getEntity().setProcurementOrder(new ProcurementOrderItemProcurementOrder(template));
//		searchInput.getFieldNames().add("procurementOrder");
		return searchInput;
	}

	/**
	 * This is the only time where the bind method is called on this object.
	 * @param model
	 */
	public void handleNewModelEvent(@Observes @SelectedModelEvent ProcurementOrder model)
	{
		this.displayedEntity = model;
		displayView.bind(this.displayedEntity);
	}

}