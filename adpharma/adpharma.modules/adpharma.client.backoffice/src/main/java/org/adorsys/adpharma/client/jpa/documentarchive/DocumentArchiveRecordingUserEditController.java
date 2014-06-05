package org.adorsys.adpharma.client.jpa.documentarchive;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class DocumentArchiveRecordingUserEditController extends DocumentArchiveRecordingUserController
{

   @Inject
   DocumentArchiveEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent DocumentArchive model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getDocumentArchiveRecordingUserSelection());
      bind(editView.getView().getDocumentArchiveRecordingUserSelection(), editView.getView().getDocumentArchiveRecordingUserForm());
   }
}
