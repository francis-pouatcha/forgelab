package org.adorsys.adpharma.client.jpa.productdetailconfig;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javafx.crud.extensions.events.CreateModelEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;

@Singleton
public class ProductDetailConfigSourceCreateController extends ProductDetailConfigSourceController
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
		activateButton(createView.getView().getProductDetailConfigSourceSelection());
		bind(createView.getView().getProductDetailConfigSourceSelection(), createView.getView().getProductDetailConfigSourceForm());
	}

	public void handleSourceSearchDone(@Observes @ModalEntitySearchDoneEvent Article article){
		if(isSource){
			createView.getView().getProductDetailConfigSourceSelection().getSource().setValue(new ProductDetailConfigSource(article));
			isSource = Boolean.FALSE ;
		}
	}


}
