package org.adorsys.adpharma.client.jpa.login;

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

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.login.Login;

public class LoginAgencySelection extends AbstractSelection<Login, Agency>
{

   private ComboBox<LoginAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, Login.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("Login_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<LoginAgency>, ListCell<LoginAgency>>()
      {
         @Override
         public ListCell<LoginAgency> call(ListView<LoginAgency> listView)
         {
            return new LoginAgencyListCell();
         }
      });
      agency.setButtonCell(new LoginAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Login model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<LoginAgency> getAgency()
   {
      return agency;
   }
}
