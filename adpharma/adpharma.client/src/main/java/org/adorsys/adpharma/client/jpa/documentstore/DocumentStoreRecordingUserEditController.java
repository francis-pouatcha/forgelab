package org.adorsys.adpharma.client.jpa.documentstore;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DocumentStoreRecordingUserEditController extends DocumentStoreRecordingUserController
{

   @Inject
   DocumentStoreEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent DocumentStore model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getDocumentStoreRecordingUserSelection());
      bind(editView.getView().getDocumentStoreRecordingUserSelection(), editView.getView().getDocumentStoreRecordingUserForm());
   }
}
