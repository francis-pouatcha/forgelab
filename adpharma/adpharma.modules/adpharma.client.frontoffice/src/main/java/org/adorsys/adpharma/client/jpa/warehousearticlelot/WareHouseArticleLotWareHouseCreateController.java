package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class WareHouseArticleLotWareHouseCreateController extends WareHouseArticleLotWareHouseController
{

   @Inject
   WareHouseArticleLotCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent WareHouseArticleLot model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getWareHouseArticleLotWareHouseSelection(), createView.getView().getWareHouseArticleLotWareHouseForm());
      activateButton(createView.getView().getWareHouseArticleLotWareHouseSelection());
   }
}
