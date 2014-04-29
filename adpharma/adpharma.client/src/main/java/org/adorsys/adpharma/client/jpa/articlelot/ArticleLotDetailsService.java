package org.adorsys.adpharma.client.jpa.articlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ArticleLotDetailsService extends Service<ArticleLot>
{

	   private ArticleLotDetailsManager model;

	   @Inject
	   private ArticleLotService remoteService;

	   public ArticleLotDetailsService setModel(ArticleLotDetailsManager model)
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
	            return remoteService.processDetails(model);
	         }
	      };
	   }

}
