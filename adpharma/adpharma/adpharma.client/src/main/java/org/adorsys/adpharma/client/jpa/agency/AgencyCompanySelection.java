package org.adorsys.adpharma.client.jpa.agency;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.agency.Agency;

public class AgencyCompanySelection extends AbstractSelection<Agency, Company>
{

   private ComboBox<Company> company;

   @Inject
   @Bundle({ CrudKeys.class, Company.class, Agency.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      company = viewBuilder.addComboBox("Agency_company_description.title", "company", resourceBundle, false);

      company.setCellFactory(new Callback<ListView<Company>, ListCell<Company>>()
      {
         @Override
         public ListCell<Company> call(ListView<Company> listView)
         {
            return new AgencyCompanyListCell();
         }
      });
      company.setButtonCell(new AgencyCompanyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Agency model)
   {
   }

   public ComboBox<Company> getCompany()
   {
      return company;
   }
}
