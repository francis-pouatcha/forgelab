package org.adorsys.adpharma.client.jpa.documentstore;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
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
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

@Singleton
public class DocumentStoreDisplayController implements EntityController
{

   @Inject
   private DocumentStoreDisplayView displayView;

   @Inject
   @EntitySearchRequestedEvent
   private Event<DocumentStore> searchRequestedEvent;

   @Inject
   @EntityEditRequestedEvent
   private Event<DocumentStore> editRequestEvent;

   @Inject
   @EntityRemoveRequestEvent
   private Event<DocumentStore> removeRequest;

   @Inject
   @AssocSelectionResponseEvent
   private Event<AssocSelectionEventData<DocumentStore>> selectionResponseEvent;

   private ObjectProperty<AssocSelectionEventData<DocumentStore>> pendingSelectionRequestProperty = new SimpleObjectProperty<AssocSelectionEventData<DocumentStore>>();

   @Inject
   @ComponentSelectionRequestEvent
   private Event<ComponentSelectionRequestData> componentSelectionRequestEvent;

   private DocumentStore displayedEntity;

   @Inject
   private DocumentStoreRegistration registration;

   @PostConstruct
   public void postConstruct()
   {
      displayView.getEditButton().disableProperty().bind(registration.canEditProperty().not());
      displayView.getRemoveButton().disableProperty().bind(registration.canEditProperty().not());

      /*
       * listen to search button and fire search requested event.
       */
      displayView.getSearchButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            searchRequestedEvent.fire(displayedEntity);
         }
      });

      displayView.getEditButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            editRequestEvent.fire(displayedEntity);
         }
      });

      displayView.getRemoveButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            removeRequest.fire(displayedEntity);
         }
      });

      displayView.getConfirmSelectionButton().setOnAction(new EventHandler<ActionEvent>()
      {
         @Override
         public void handle(ActionEvent e)
         {
            final AssocSelectionEventData<DocumentStore> pendingSelectionRequest = pendingSelectionRequestProperty.get();
            if (pendingSelectionRequest == null)
               return;
            pendingSelectionRequestProperty.set(null);
            pendingSelectionRequest.setTargetEntity(displayedEntity);
            selectionResponseEvent.fire(pendingSelectionRequest);
         }
      });

      displayView.getConfirmSelectionButton().visibleProperty().bind(pendingSelectionRequestProperty.isNotNull());
   }

   public void display(Pane parent)
   {
      AnchorPane rootPane = displayView.getRootPane();
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
   public void handleSelectionEvent(@Observes @EntitySelectionEvent DocumentStore selectedEntity)
   {
      PropertyReader.copy(selectedEntity, displayedEntity);
      displayView.getRemoveButton().setDisable(false);
      displayView.getEditButton().setDisable(false);
   }

   public void handleAssocSelectionRequest(@Observes(notifyObserver = Reception.ALWAYS) @AssocSelectionRequestEvent AssocSelectionEventData<DocumentStore> eventData)
   {
      pendingSelectionRequestProperty.set(eventData);
      componentSelectionRequestEvent.fire(new ComponentSelectionRequestData(DocumentStore.class.getName()));
      searchRequestedEvent.fire(eventData.getTargetEntity() != null ? eventData.getTargetEntity() : new DocumentStore());
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent DocumentStore model)
   {
      this.displayedEntity = model;
      displayView.bind(this.displayedEntity);
   }

	public void reset() {
	     PropertyReader.copy(new DocumentStore(), displayedEntity);
	}
}