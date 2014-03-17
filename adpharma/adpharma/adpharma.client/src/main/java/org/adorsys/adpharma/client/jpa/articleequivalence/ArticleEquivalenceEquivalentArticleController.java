package org.adorsys.adpharma.client.jpa.articleequivalence;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchResult;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchService;
import org.adorsys.adpharma.client.jpa.articleequivalence.ArticleEquivalence;

public abstract class ArticleEquivalenceEquivalentArticleController
{
   @Inject
   private ArticleSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private ArticleSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Article.class, ArticleEquivalence.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected ArticleEquivalence sourceEntity;

   protected void disableButton(final ArticleEquivalenceEquivalentArticleSelection selection)
   {
      selection.getEquivalentArticle().setDisable(true);
   }

   protected void activateButton(final ArticleEquivalenceEquivalentArticleSelection selection)
   {
   }

   protected void bind(final ArticleEquivalenceEquivalentArticleSelection selection)
   {

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
            selection.getEquivalentArticle().getItems().clear();
            selection.getEquivalentArticle().getItems().addAll(entities);
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

      selection.getEquivalentArticle().valueProperty().addListener(new ChangeListener<Article>()
      {
         @Override
         public void changed(ObservableValue<? extends Article> ov, Article oldValue,
               Article newValue)
         {
            if (sourceEntity != null)
               sourceEntity.setEquivalentArticle(new ArticleEquivalenceEquivalentArticle(newValue));
         }
      });

      selection.getEquivalentArticle().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new ArticleSearchInput()).start();
   }

}
