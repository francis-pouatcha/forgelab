package org.adorsys.adpharma.client.jpa.productdetailconfig;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;

@Singleton
public class ProductDetailConfigTargetCreateController extends ProductDetailConfigTargetController
{

	@Inject
	ProductDetailConfigCreateView createView;

	@PostConstruct
	public void postConstruct()
	{
	}

	public void handleNewModelEvent(@Observes @CreateModelEvent ProductDetailConfig model)
	{
		this.sourceEntity = model;
		activateButton(createView.getView().getProductDetailConfigTargetSelection());
		bind(createView.getView().getProductDetailConfigTargetSelection(), createView.getView().getProductDetailConfigTargetForm());


	}

	public void handleTargetSearchDone(@Observes @ModalEntitySearchDoneEvent Article article){
		if(isTarget){
			createView.getView().getProductDetailConfigTargetSelection().getTarget().setValue(new ProductDetailConfigTarget(article));
			isTarget = Boolean.FALSE;
		}
	}
}
