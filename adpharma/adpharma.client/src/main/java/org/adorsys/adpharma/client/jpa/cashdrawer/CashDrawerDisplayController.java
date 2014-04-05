package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.DomainComponent;
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
import org.adorsys.javafx.crud.extensions.events.ModalEntityCreateDoneEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.PermissionsEvent;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class CashDrawerDisplayController implements EntityController
{

	@Inject
	private CashDrawerDisplayView displayView;

	@Inject
	@EntitySearchRequestedEvent
	private Event<CashDrawer> searchRequestedEvent;

	@Inject
	@EntityEditRequestedEvent
	private Event<CashDrawer> editRequestEvent;

	@Inject
	@EntityRemoveRequestEvent
	private Event<CashDrawer> removeRequest;

	@Inject
	@PermissionsEvent
	private Event<DomainComponent> permissionEvent;

	@Inject
	private CashDrawerCreateService cashDrawerCreateService ;

	@Inject
	private CashDrawerCloseService cashDrawerCloseService ;


	@Inject
	private ServiceCallFailedEventHandler serviceCallFailedEventHandler;

	@Inject
	@AssocSelectionResponseEvent
	private Event<AssocSelectionEventData<CashDrawer>> selectionResponseEvent;

	private ObjectProperty<AssocSelectionEventData<CashDrawer>> pendingSelectionRequestProperty = new SimpleObjectProperty<AssocSelectionEventData<CashDrawer>>();

	@Inject
	@ComponentSelectionRequestEvent
	private Event<ComponentSelectionRequestData> componentSelectionRequestEvent;

	@Inject
	@Bundle({ CrudKeys.class, CashDrawer.class })
	private ResourceBundle resourceBundle;

	private CashDrawer displayedEntity; 

	@Inject
	private CashDrawerRegistration registration;

	@PostConstruct
	public void postConstruct()
	{
		displayView.getOpenCashDrawerButton().disableProperty().bind(registration.canCreateProperty().not());
		displayView.getCloseCashDrawerButton().disableProperty().bind(registration.canEditProperty().not());


		serviceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().nativeTitleBar().showException(exception);
			}
		});

		/*
		 * handle open cash drawer action
		 */
		displayView.getOpenCashDrawerButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				BigDecimal cashDrawerInitialAmount = getCashDrawerInitialAmount();
				CashDrawer cashDrawer = new CashDrawer();
				cashDrawer.setInitialAmount(cashDrawerInitialAmount);
				cashDrawerCreateService.setModel(cashDrawer).start();

			}
		});

		cashDrawerCreateService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerCreateService s = (CashDrawerCreateService) event.getSource();
				CashDrawer cd = s.getValue();
				event.consume();
				s.reset();
				PropertyReader.copy(cd, displayedEntity);
				System.out.println(displayedEntity);

			}
		});
		cashDrawerCreateService.setOnFailed(serviceCallFailedEventHandler);

		/*
		 * handle close cash drawer action
		 */
		displayView.getCloseCashDrawerButton().setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(displayedEntity.getId()!=null)
					cashDrawerCloseService.setCashDrawer(displayedEntity).start();

			}
		});

		cashDrawerCloseService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CashDrawerCloseService s = (CashDrawerCloseService) event.getSource();
				CashDrawer cd = s.getValue();
				event.consume();
				s.reset();
				PropertyReader.copy(cd, displayedEntity);
				System.out.println(displayedEntity +":"+displayedEntity.getClosingDate());
				permissionEvent.fire(new DomainComponent(null, null));

			}
		});
		cashDrawerCloseService.setOnFailed(serviceCallFailedEventHandler);
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
		//      displayView.getEditButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            editRequestEvent.fire(displayedEntity);
		//         }
		//      });
		//
		//      displayView.getRemoveButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            removeRequest.fire(displayedEntity);
		//         }
		//      });
		//
		//      displayView.getConfirmSelectionButton().setOnAction(new EventHandler<ActionEvent>()
		//      {
		//         @Override
		//         public void handle(ActionEvent e)
		//         {
		//            final AssocSelectionEventData<CashDrawer> pendingSelectionRequest = pendingSelectionRequestProperty.get();
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

	public BigDecimal getCashDrawerInitialAmount(){
		String showTextInput = Dialogs.create().message(resourceBundle.getString("CashDrawer_initialAmount_description.title")).showTextInput("0");
		BigDecimal initialAmount = BigDecimal.ZERO ;
		try {
			initialAmount = new BigDecimal(showTextInput);
		} catch (Exception e) {
			getCashDrawerInitialAmount();
		}

		return initialAmount;
	}

	/**
	 * Listens to list selection events and bind selected to pane
	 */
	public void handleSelectionEvent(@Observes @EntitySelectionEvent CashDrawer selectedEntity)
	{
		PropertyReader.copy(selectedEntity, displayedEntity);


	}
	public void handleCashDrawerCreateDoneEvent(@Observes @ModalEntityCreateDoneEvent CashDrawer cashDrawer){
		PropertyReader.copy(cashDrawer, displayedEntity);
	}
	public void handleAssocSelectionRequest(@Observes(notifyObserver = Reception.ALWAYS) @AssocSelectionRequestEvent AssocSelectionEventData<CashDrawer> eventData)
	{
		pendingSelectionRequestProperty.set(eventData);
		componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(CashDrawer.class.getName()));
		searchRequestedEvent.fire(eventData.getTargetEntity() != null ? eventData.getTargetEntity() : new CashDrawer());
	}

	/**
	 * This is the only time where the bind method is called on this object.
	 * @param model
	 */
	public void handleNewModelEvent(@Observes @SelectedModelEvent CashDrawer model)
	{
		this.displayedEntity = model;
		displayView.bind(this.displayedEntity);
	}

}