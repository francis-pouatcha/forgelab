package org.adorsys.adpharma.client.jpa.warehouse;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class WareHouseAgencyEditController extends WareHouseAgencyController
{

   @Inject
   WareHouseEditView editView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @SelectedModelEvent WareHouse model)
   {
      this.sourceEntity = model;
      activateButton(editView.getView().getWareHouseAgencySelection());
      bind(editView.getView().getWareHouseAgencySelection(), editView.getView().getWareHouseAgencyForm());
   }
}
