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

import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;

public class ArticleLotVatSelection extends AbstractSelection<ArticleLot, VAT>
{

   private ComboBox<ArticleLotVat> vat;

   @Inject
   @Bundle({ CrudKeys.class, VAT.class, ArticleLot.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      vat = viewBuilder.addComboBox("ArticleLot_vat_description.title", "vat", resourceBundle, false);

      vat.setCellFactory(new Callback<ListView<ArticleLotVat>, ListCell<ArticleLotVat>>()
      {
         @Override
         public ListCell<ArticleLotVat> call(ListView<ArticleLotVat> listView)
         {
            return new ArticleLotVatListCell();
         }
      });
      vat.setButtonCell(new ArticleLotVatListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(ArticleLot model)
   {
      vat.valueProperty().bindBidirectional(model.vatProperty());
   }

   public ComboBox<ArticleLotVat> getVat()
   {
      return vat;
   }
}
