package org.adorsys.adpharma.client.jpa.articlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;

public class StockValueSearchService extends Service<ArticleLotSearchResult>
{

	@Inject
	private ArticleLotService remoteService;

	private ArticleSearchInput searchInputs;
	
	private Boolean stockValueBypic = Boolean.FALSE ;

	private Boolean stockValue = Boolean.FALSE ;

	public StockValueSearchService setSearchInputs(ArticleSearchInput searchInputs)
	{
		this.searchInputs = searchInputs;
		return this;
	}

	public StockValueSearchService setStockValueBypic(Boolean stockValueBypic)
	{
		this.stockValueBypic = stockValueBypic;
		return this;
	}

	public StockValueSearchService setStockValue(Boolean stockValue)
	{
		this.stockValue = stockValue;
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
				if(stockValue)
					return remoteService.stockValue(searchInputs);
				return null ;
			}
				};
	}

}
