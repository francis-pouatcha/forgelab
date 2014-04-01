package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProcurementOrderItemArticleEditController extends ProcurementOrderItemArticleController
{

   @Inject
   ProcurementOrderItemEditView editView;

   public void handleNewModelEvent(@Observes @SelectedModelEvent ProcurementOrderItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getProcurementOrderItemArticleSelection(), editView.getView().getProcurementOrderItemArticleForm());
      bind(editView.getView().getProcurementOrderItemArticleSelection(), editView.getView().getProcurementOrderItemArticleForm());
   }

   public void handleEditRequestEvent(
         @Observes @EntityEditRequestedEvent ProcurementOrderItem p)
   {
      loadAssociation();
   }

}
