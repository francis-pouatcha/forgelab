package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class WareHouseArticleLotWareHouseEditController extends WareHouseArticleLotWareHouseController
{

   @Inject
   WareHouseArticleLotEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent WareHouseArticleLot model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getWareHouseArticleLotWareHouseSelection());
      bind(editView.getView().getWareHouseArticleLotWareHouseSelection(), editView.getView().getWareHouseArticleLotWareHouseForm());
   }
}
