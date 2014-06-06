package org.adorsys.adpharma.client.jpa.documentstore;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class DocumentStoreRecordingUserCreateController extends DocumentStoreRecordingUserController
{

   @Inject
   DocumentStoreCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent DocumentStore model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getDocumentStoreRecordingUserSelection(), createView.getView().getDocumentStoreRecordingUserForm());
      activateButton(createView.getView().getDocumentStoreRecordingUserSelection());
   }
}
