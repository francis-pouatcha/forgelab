package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProcurementOrderItemArticleEditController extends ProcurementOrderItemArticleController
{

   @Inject
   ProcurementOrderItemEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent ProcurementOrderItem model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getProcurementOrderItemArticleSelection());
      bind(editView.getView().getProcurementOrderItemArticleSelection());
   }
}
