package org.adorsys.adpharma.client.jpa.articlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.StockValueArticleSearchInput;

public class StockValueSearchService extends Service<ArticleLotSearchResult>
{

	@Inject
	private ArticleLotService remoteService;

	private StockValueArticleSearchInput searchInputs;
	


	public StockValueSearchService setSearchInputs(StockValueArticleSearchInput searchInputs)
	{
		this.searchInputs = searchInputs;
		return this;
	}


	@Override
	protected Task<ArticleLotSearchResult> createTask()
	{
		return new Task<ArticleLotSearchResult>()
				{
			@Override
			protected ArticleLotSearchResult call() throws Exception
			{
				if (searchInputs == null)
					return null;
				return remoteService.stockValue(searchInputs);
			}
				};
	}

}
