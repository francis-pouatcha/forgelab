package org.adorsys.adpharma.client.jpa.articlelot;

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
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;

public class ArticleLotAgencySelection extends AbstractSelection<ArticleLot, Agency>
{

   private ComboBox<ArticleLotAgency> agency;

   @Inject
   @Bundle({ CrudKeys.class, Agency.class, ArticleLot.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      agency = viewBuilder.addComboBox("ArticleLot_agency_description.title", "agency", resourceBundle, false);

      agency.setCellFactory(new Callback<ListView<ArticleLotAgency>, ListCell<ArticleLotAgency>>()
      {
         @Override
         public ListCell<ArticleLotAgency> call(ListView<ArticleLotAgency> listView)
         {
            return new ArticleLotAgencyListCell();
         }
      });
      agency.setButtonCell(new ArticleLotAgencyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(ArticleLot model)
   {
      agency.valueProperty().bindBidirectional(model.agencyProperty());
   }

   public ComboBox<ArticleLotAgency> getAgency()
   {
      return agency;
   }
}
