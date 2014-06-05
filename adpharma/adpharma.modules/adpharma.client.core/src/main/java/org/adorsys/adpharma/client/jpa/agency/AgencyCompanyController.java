package org.adorsys.adpharma.client.jpa.agency;

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

import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.company.CompanySearchInput;
import org.adorsys.adpharma.client.jpa.company.CompanySearchResult;
import org.adorsys.adpharma.client.jpa.company.CompanySearchService;
import org.adorsys.adpharma.client.jpa.agency.Agency;

public abstract class AgencyCompanyController
{
   @Inject
   private CompanySearchService searchService;
   @Inject
   private ServiceCallFailedEventHandler searchServiceCallFailedEventHandler;

   private CompanySearchResult targetSearchResult;

   @Inject
   @Bundle({ CrudKeys.class, Company.class, Agency.class })
   private ResourceBundle resourceBundle;

   @Inject
   private ErrorMessageDialog errorMessageDialog;

   protected Agency sourceEntity;

   protected void disableButton(final AgencyCompanySelection selection)
   {
      selection.getCompany().setDisable(true);
   }

   protected void activateButton(final AgencyCompanySelection selection)
   {
   }

   protected void bind(final AgencyCompanySelection selection, final AgencyCompanyForm form)
   {

      //	    selection.getCompany().valueProperty().bindBidirectional(sourceEntity.companyProperty());

      // send search result event.
      searchService.setOnSucceeded(new EventHandler<WorkerStateEvent>()
      {
         @Override
         public void handle(WorkerStateEvent event)
         {
            CompanySearchService s = (CompanySearchService) event
                  .getSource();
            targetSearchResult = s.getValue();
            event.consume();
            s.reset();
            List<Company> entities = targetSearchResult.getResultList();
            selection.getCompany().getItems().clear();
            selection.getCompany().getItems().add(new AgencyCompany());
            for (Company entity : entities)
            {
               selection.getCompany().getItems().add(new AgencyCompany(entity));
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

      selection.getCompany().valueProperty().addListener(new ChangeListener<AgencyCompany>()
      {
         @Override
         public void changed(ObservableValue<? extends AgencyCompany> ov, AgencyCompany oldValue,
               AgencyCompany newValue)
         {
            if (sourceEntity != null)
               form.update(newValue);
            //                sourceEntity.setCompany(newValue);
         }
      });

      selection.getCompany().armedProperty().addListener(new ChangeListener<Boolean>()
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
      searchService.setSearchInputs(new CompanySearchInput()).start();
   }

}
