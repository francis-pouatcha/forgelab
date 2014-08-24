package org.adorsys.adpharma.client.jpa.productdetailconfig;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.EntityEditRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.SelectedModelEvent;

@Singleton
public class ProductDetailConfigTargetEditController extends ProductDetailConfigTargetController
{

   @Inject
   ProductDetailConfigEditView editView;
   @PostConstruct
	public void postConstruct()
	{
	}

	public void handleNewModelEvent(@Observes @CreateModelEvent ProductDetailConfig model)
	{
		this.sourceEntity = model;
		activateButton(editView.getView().getProductDetailConfigTargetSelection());
		bind(editView.getView().getProductDetailConfigTargetSelection(), editView.getView().getProductDetailConfigTargetForm());


	}

	public void handleTargetSearchDone(@Observes @ModalEntitySearchDoneEvent Article article){
		if(isTarget){
			editView.getView().getProductDetailConfigTargetSelection().getTarget().setValue(new ProductDetailConfigTarget(article));
			isTarget = Boolean.FALSE;
		}
	}

}
