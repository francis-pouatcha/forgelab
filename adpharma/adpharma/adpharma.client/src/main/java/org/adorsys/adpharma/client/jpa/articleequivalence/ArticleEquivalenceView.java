package org.adorsys.adpharma.client.jpa.articleequivalence;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.adorsys.adpharma.client.jpa.article.Article;
import javafx.beans.property.SimpleObjectProperty;
import java.util.Calendar;

import org.adorsys.javafx.crud.extensions.ViewModel;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.articleequivalence.ArticleEquivalence;

public class ArticleEquivalenceView extends AbstractForm<ArticleEquivalence>
{

   private CalendarTextField recordingDate;

   @Inject
   private ArticleEquivalenceMainArticleForm articleEquivalenceMainArticleForm;
   @Inject
   private ArticleEquivalenceMainArticleSelection articleEquivalenceMainArticleSelection;

   @Inject
   private ArticleEquivalenceEquivalentArticleForm articleEquivalenceEquivalentArticleForm;
   @Inject
   private ArticleEquivalenceEquivalentArticleSelection articleEquivalenceEquivalentArticleSelection;

   @Inject
   @Bundle({ CrudKeys.class, ArticleEquivalence.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      recordingDate = viewBuilder.addCalendarTextField("ArticleEquivalence_recordingDate_description.title", "recordingDate", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addTitlePane("ArticleEquivalence_mainArticle_description.title", resourceBundle);
      viewBuilder.addSubForm("ArticleEquivalence_mainArticle_description.title", "mainArticle", resourceBundle, articleEquivalenceMainArticleForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ArticleEquivalence_mainArticle_description.title", "mainArticle", resourceBundle, articleEquivalenceMainArticleSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("ArticleEquivalence_equivalentArticle_description.title", resourceBundle);
      viewBuilder.addSubForm("ArticleEquivalence_equivalentArticle_description.title", "equivalentArticle", resourceBundle, articleEquivalenceEquivalentArticleForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("ArticleEquivalence_equivalentArticle_description.title", "equivalentArticle", resourceBundle, articleEquivalenceEquivalentArticleSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
   }

   public Set<ConstraintViolation<ArticleEquivalence>> validate(ArticleEquivalence model)
   {
      Set<ConstraintViolation<ArticleEquivalence>> violations = new HashSet<ConstraintViolation<ArticleEquivalence>>();
      return violations;
   }

   public void bind(ArticleEquivalence model)
   {
      recordingDate.calendarProperty().bindBidirectional(model.recordingDateProperty());
      articleEquivalenceMainArticleForm.bind(model);
      articleEquivalenceMainArticleSelection.bind(model);
      articleEquivalenceEquivalentArticleForm.bind(model);
      articleEquivalenceEquivalentArticleSelection.bind(model);
   }

   public CalendarTextField getRecordingDate()
   {
      return recordingDate;
   }

   public ArticleEquivalenceMainArticleForm getArticleEquivalenceMainArticleForm()
   {
      return articleEquivalenceMainArticleForm;
   }

   public ArticleEquivalenceMainArticleSelection getArticleEquivalenceMainArticleSelection()
   {
      return articleEquivalenceMainArticleSelection;
   }

   public ArticleEquivalenceEquivalentArticleForm getArticleEquivalenceEquivalentArticleForm()
   {
      return articleEquivalenceEquivalentArticleForm;
   }

   public ArticleEquivalenceEquivalentArticleSelection getArticleEquivalenceEquivalentArticleSelection()
   {
      return articleEquivalenceEquivalentArticleSelection;
   }
}
