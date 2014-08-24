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
public class ProductDetailConfigSourceEditController extends ProductDetailConfigSourceController
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
		activateButton(editView.getView().getProductDetailConfigSourceSelection());
		bind(editView.getView().getProductDetailConfigSourceSelection(), editView.getView().getProductDetailConfigSourceForm());
	}

	public void handleSourceSearchDone(@Observes @ModalEntitySearchDoneEvent Article article){
		if(isSource){
			editView.getView().getProductDetailConfigSourceSelection().getSource().setValue(new ProductDetailConfigSource(article));
			isSource = Boolean.FALSE ;
		}
	}


}
