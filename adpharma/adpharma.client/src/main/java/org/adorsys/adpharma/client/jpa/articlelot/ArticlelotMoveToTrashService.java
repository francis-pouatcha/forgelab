package org.adorsys.adpharma.client.jpa.articlelot;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ArticlelotMoveToTrashService extends Service<ArticleLot>
{

	@Inject
	private ArticleLotService remoteService;

	private ArticleLotMovedToTrashData data;


	public ArticlelotMoveToTrashService setData(ArticleLotMovedToTrashData data)
	{
		this.data = data;
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
				if (data == null)
					return null;

				return remoteService.movedToTrash(data);
			}
				};
	}
	
	

}
