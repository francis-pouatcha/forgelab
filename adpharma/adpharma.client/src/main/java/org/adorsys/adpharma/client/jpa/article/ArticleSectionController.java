package org.adorsys.adpharma.client.jpa.article;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.section.SectionSearchInput;
import org.adorsys.adpharma.client.jpa.section.SectionSearchResult;
import org.adorsys.adpharma.client.jpa.section.SectionSearchService;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;

public abstract class ArticleSectionController
{
   @Inject
   private SectionSearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private SectionSearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Section.class, Article.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Article sourceEntity;

   protected void disableButton(final ArticleSectionSelection selection)
   {
      selection.getSection().setDisable(true);
   }

   protected void activateButton(final ArticleSectionSelection selection)
   {
   }

   protected void bind(final ArticleSectionSelection selection, final ArticleSectionForm form)
   {

//      selection.getSection().valueProperty().bindBidirectional(sourceEntity.sectionProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            SectionSearchService s = (SectionSearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Section> entities = targetSearchResult.getResultList();
            selection.getSection().getItems().clear();
            selection.getSection().getItems().add(new ArticleSection());
            entities.sort(new Comparator<Section>() {

				@Override
				public int compare(Section o1, Section o2) {
					// TODO Auto-generated method stub
					return o1.getName().compareToIgnoreCase(o2.getName());
				}
			});
            ArrayList<ArticleSection> arrayList = new ArrayList<ArticleSection>();
            for (Section entity : entities)
            {
            	arrayList.add(new ArticleSection(entity));
            }
            selection.getSection().getItems().addAll(arrayList);
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

      selection.getSection().valueProperty().addListener(new ChangeListener<ArticleSection>()
      {
         @Override
         public void changed(ObservableValue<? extends ArticleSection> ov, ArticleSection oldValue,
               ArticleSection newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setSection(newValue);
         }
      });

      selection.getSection().armedProperty().addListener(new ChangeListener<Boolean>()
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
//	  if(searchService.isRunning()) return;
      searchService.setSearchInputs(new SectionSearchInput()).start();
   }

}
