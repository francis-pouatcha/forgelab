package org.adorsys.adpharma.client.jpa.articlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.utils.ArticleLotDetailResultHolder;

public class ArticleLotDetailsService extends Service<ArticleLotDetailResultHolder>
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
	   protected Task<ArticleLotDetailResultHolder> createTask()
	   {
	      return new Task<ArticleLotDetailResultHolder>()
	      {
	         @Override
	         protected ArticleLotDetailResultHolder call() throws Exception
	         {
	            if (model == null)
	               return null;
	            return remoteService.processDetails(model);
	         }
	      };
	   }

}
