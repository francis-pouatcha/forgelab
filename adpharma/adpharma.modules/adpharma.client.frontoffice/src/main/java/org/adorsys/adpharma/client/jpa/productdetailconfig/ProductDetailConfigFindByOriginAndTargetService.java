package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class ProductDetailConfigFindByOriginAndTargetService extends Service<List<ProductDetailConfig>>
{

	@Inject
	private ProductDetailConfigService remoteService;

	private String source;

	private String target;

	public ProductDetailConfigFindByOriginAndTargetService setSourceName(String sourceName)
	{
		this.source = sourceName;
		return this;
	}

	public ProductDetailConfigFindByOriginAndTargetService setTargetName(String targetName)
	{
		this.target = targetName;
		return this;
	}

	@Override
	protected Task<List<ProductDetailConfig>> createTask()
	{
		return new Task<List<ProductDetailConfig>>()
				{
			@Override
			protected List<ProductDetailConfig> call() throws Exception
			{

				return remoteService.findByOriginAndTargetNameLike(source, target);
			}
				};
	}

}
