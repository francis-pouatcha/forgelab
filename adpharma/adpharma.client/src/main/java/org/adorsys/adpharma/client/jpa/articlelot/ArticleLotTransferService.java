package org.adorsys.adpharma.client.jpa.articlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.warehousearticlelot.WareHouseArticleLot;
import org.adorsys.adpharma.client.jpa.warehousearticlelot.WareHouseArticleLotService;

public class ArticleLotTransferService extends Service<ArticleLot>
{

	   private ArticleLotTransferManager model;

	   @Inject
	   private ArticleLotService remoteService;

	   public ArticleLotTransferService setModel(ArticleLotTransferManager model)
	   {
	      this.model = model;
	      return this;
	   }

	   @Override
	   protected Task<ArticleLot> createTask()
	   {
	      return new Task<ArticleLot>()
	      {
	         @Override
	         protected ArticleLot call() throws Exception
	         {
	            if (model == null)
	               return null;
	            return remoteService.processTransfer(model);
	         }
	      };
	   }

}
