package org.adorsys.adpharma.client.jpa.warehouse;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;

@Singleton
public class WareHouseAgencyCreateController extends WareHouseAgencyController
{

   @Inject
   WareHouseCreateView createView;

   @PostConstruct
   public void postConstruct()
   {
   }

   public void handleNewModelEvent(@Observes @CreateModelEvent WareHouse model)
   {
      this.sourceEntity = model;
      bind(createView.getView().getWareHouseAgencySelection(), createView.getView().getWareHouseAgencyForm());
      activateButton(createView.getView().getWareHouseAgencySelection());
   }
}
