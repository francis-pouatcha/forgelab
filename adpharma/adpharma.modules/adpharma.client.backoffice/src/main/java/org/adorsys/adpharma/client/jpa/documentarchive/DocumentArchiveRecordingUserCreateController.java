package org.adorsys.adpharma.client.jpa.documentarchive;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class DocumentArchiveRecordingUserCreateController extends DocumentArchiveRecordingUserController
{

   @Inject
   DocumentArchiveCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent DocumentArchive model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getDocumentArchiveRecordingUserSelection(), createView.getView().getDocumentArchiveRecordingUserForm());
      activateButton(createView.getView().getDocumentArchiveRecordingUserSelection());
   }
}
