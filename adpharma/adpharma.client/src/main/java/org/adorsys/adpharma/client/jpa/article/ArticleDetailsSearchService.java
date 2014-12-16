package org.adorsys.adpharma.client.jpa.article;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemService;

public class ArticleDetailsSearchService extends Service<ArticleDetailsSearchResult>{
	
	
	
	@Inject
	DeliveryItemService remoteService;
	
    private ArticleSearchInput searchInputs;
    

	public ArticleDetailsSearchService setSearchInputs(ArticleSearchInput searchInputs) {
		this.searchInputs = searchInputs;
		return this;
	}


	@Override
	protected Task<ArticleDetailsSearchResult> createTask() {
		return new Task<ArticleDetailsSearchResult>() {

			@Override
			protected ArticleDetailsSearchResult call() throws Exception {
				if(searchInputs!=null) {
					return remoteService.findByArticle(searchInputs);
				}
				return null;
			}
		};
	}

}
