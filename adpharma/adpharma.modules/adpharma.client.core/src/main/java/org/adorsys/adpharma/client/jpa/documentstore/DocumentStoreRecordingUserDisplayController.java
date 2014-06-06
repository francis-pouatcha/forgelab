package org.adorsys.adpharma.client.jpa.documentstore;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DocumentStoreRecordingUserDisplayController extends DocumentStoreRecordingUserController
{

   @Inject
   private DocumentStoreDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent DocumentStore model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getDocumentStoreRecordingUserSelection());
      bind(displayView.getView().getDocumentStoreRecordingUserSelection(), displayView.getView().getDocumentStoreRecordingUserForm());
   }
}
