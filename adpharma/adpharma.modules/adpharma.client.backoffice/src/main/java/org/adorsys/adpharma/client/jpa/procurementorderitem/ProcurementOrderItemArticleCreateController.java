package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class ProcurementOrderItemArticleCreateController extends ProcurementOrderItemArticleController
{

   @Inject
   ProcurementOrderItemCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent ProcurementOrderItem model)
   {
      this.sourceEntity = model;
      activateButton(createView.getView().getProcurementOrderItemArticleSelection(), createView.getView().getProcurementOrderItemArticleForm());
      bind(createView.getView().getProcurementOrderItemArticleSelection(), createView.getView().getProcurementOrderItemArticleForm());
   }
}
