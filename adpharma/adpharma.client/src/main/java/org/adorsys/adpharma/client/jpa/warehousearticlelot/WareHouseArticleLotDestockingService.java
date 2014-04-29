package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotTransferManager;

public class WareHouseArticleLotDestockingService extends Service<WareHouseArticleLot>
{

	   private ArticleLotTransferManager model;

	   @Inject
	   private WareHouseArticleLotService remoteService;

	   public WareHouseArticleLotDestockingService setModel(ArticleLotTransferManager model)
	   {
	      this.model = model;
	      return this;
	   }

	   @Override
	   protected Task<WareHouseArticleLot> createTask()
	   {
	      return new Task<WareHouseArticleLot>()
	      {
	         @Override
	         protected WareHouseArticleLot call() throws Exception
	         {
	            if (model == null)
	               return null;
	            return remoteService.processDestocking(model);
	         }
	      };
	   }

}
