package org.adorsys.adpharma.client.jpa.productdetailconfig;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchResult;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchService;
import org.adorsys.adpharma.client.jpa.section.SectionSearchInput;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class ProductDetailConfigSourceController
{

	@Inject
	   private ArticleSearchService searchService;
	   @Inject
	   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

	   private ArticleSearchResult targetSearchResult;

	   @Inject
	   @Bundle({ CrudKeys.class, ProductDetailConfig.class, Article.class })
	   private ResourceBundle resourceBundle;

	   @Inject
	   private ErrorMessageDialog errorMessageDialog;

	   protected ProductDetailConfig sourceEntity;

	   protected void disableButton(final ProductDetailConfigSourceSelection selection)
	   {
	      selection.getSource().setDisable(true);
	   }

	   protected void activateButton(final ProductDetailConfigSourceSelection selection)
	   {
	   }

	   protected void bind(final ProductDetailConfigSourceSelection selection, final ProductDetailConfigSourceForm form)
	   {

//	      selection.getSection().valueProperty().bindBidirectional(sourceEntity.sectionProperty());

	      // send search result event.
	      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
	      {
	         @Override
	         public void handle(WorkerStateEvent event)
	         {
	        	 ArticleSearchService s = (ArticleSearchService) event
	                  .getSource();
	            targetSearchResult = s.getValue();
	            event.consume();
	            s.reset();
	            List<Article> entities = targetSearchResult.getResultList();
	            selection.getSource().getItems().clear();
	            selection.getSource().getItems().add(new ProductDetailConfigSource());
	            for (Article entity : entities)
	            {
	               selection.getSource().getItems().add(new ProductDetailConfigSource(entity));
	            }
	         }
	      });
	      searchServiceCallFailedEventHandler.setErrorDisplay(new ErrorDisplay()
	      {
	         @Override
	         protected void showError(Throwable exception)
	         {
	            String message = exception.getMessage();
	            errorMessageDialog.getTitleText().setText(
	                  resourceBundle.getString("Entity_search_error.title"));
	            if (!StringUtils.isBlank(message))
	               errorMessageDialog.getDetailText().setText(message);
	            errorMessageDialog.display();
	         }
	      });
	      searchService.setOnFailed(searchServiceCallFailedEventHandler);

	      errorMessageDialog.getOkButton().setOnAction(
	            new EventHandler<ActionEvent>()
	            {
	               @Override
	               public void handle(ActionEvent event)
	               {
	                  errorMessageDialog.closeDialog();
	               }
	            });

	      selection.getSource().valueProperty().addListener(new ChangeListener<ProductDetailConfigSource>()
	      {
	         @Override
	         public void changed(ObservableValue<? extends ProductDetailConfigSource> ov, ProductDetailConfigSource oldValue,
	        		 ProductDetailConfigSource newValue)
	         {
	            if (sourceEntity != null)
	               form.update(newValue);
	            //                sourceEntity.setSection(newValue);
	         }
	      });

	      selection.getSource().armedProperty().addListener(new ChangeListener<Boolean>()
	      {

	         @Override
	         public void changed(ObservableValue<? extends Boolean> observableValue,
	               Boolean oldValue, Boolean newValue)
	         {
	            if (newValue)
	               load();
	         }

	      });
	   }

	   public void load()
	   {
//		  if(searchService.isRunning()) return;
	      searchService.setSearchInputs(new ArticleSearchInput()).start();
	   }

}
