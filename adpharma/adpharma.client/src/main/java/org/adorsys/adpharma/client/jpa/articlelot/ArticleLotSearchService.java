package org.adorsys.adpharma.client.jpa.articlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ArticleLotSearchService extends Service<ArticleLotSearchResult>
{

	@Inject
	private ArticleLotService remoteService;

	private ArticleLotSearchInput searchInputs;
	
	private Boolean searchRealPrice = Boolean.FALSE;


	public ArticleLotSearchService setSearchInputs(ArticleLotSearchInput searchInputs)
	{
		this.searchInputs = searchInputs;
		return this;
	}
	
	public ArticleLotSearchService setSearchRealPrice(Boolean searchRealPrice)
	{
		this.searchRealPrice = searchRealPrice;
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
				if(searchRealPrice)
					return remoteService.findArticleLotByInternalPicWhitRealPrice(searchInputs);
				
				if (searchInputs == null)
					return null;

				return remoteService.findByLike(searchInputs);
			}
				};
	}
}
