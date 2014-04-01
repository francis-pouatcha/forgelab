package org.adorsys.adpharma.client.jpa.procurementorderitem;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.EntitySelectionEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProcurementOrderItemArticleDisplayController extends ProcurementOrderItemArticleController
{

   @Inject
   private ProcurementOrderItemDisplayView displayView;

   @PostConstruct
   public void postConstruct()
   {
   }

   /**
    * This is the only time where the bind method is called on this object.
    * @param model
    */
   public void handleNewModelEvent(@Observes @SelectedModelEvent ProcurementOrderItem model)
   {
      this.sourceEntity = model;
      disableButton(displayView.getView().getProcurementOrderItemArticleSelection(), displayView.getView().getProcurementOrderItemArticleForm());
      bind(displayView.getView().getProcurementOrderItemArticleSelection(), displayView.getView().getProcurementOrderItemArticleForm());
   }

   public void handleSelectionEvent(@Observes @EntitySelectionEvent ProcurementOrderItem selectedEntity)
   {
      loadAssociation();
   }
}
