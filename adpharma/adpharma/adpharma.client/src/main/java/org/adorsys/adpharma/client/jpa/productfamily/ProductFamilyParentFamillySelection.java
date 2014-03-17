package org.adorsys.adpharma.client.jpa.productfamily;

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

public class ProductFamilyParentFamillySelection extends AbstractSelection<ProductFamily, ProductFamily>
{

   private ComboBox<ProductFamily> parentFamilly;

   @Inject
   @Bundle({ CrudKeys.class, ProductFamily.class, ProductFamily.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      parentFamilly = viewBuilder.addComboBox("ProductFamily_parentFamilly_description.title", "parentFamilly", resourceBundle, false);

      parentFamilly.setCellFactory(new Callback<ListView<ProductFamily>, ListCell<ProductFamily>>()
      {
         @Override
         public ListCell<ProductFamily> call(ListView<ProductFamily> listView)
         {
            return new ProductFamilyParentFamillyListCell();
         }
      });
      parentFamilly.setButtonCell(new ProductFamilyParentFamillyListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(ProductFamily model)
   {
   }

   public ComboBox<ProductFamily> getParentFamilly()
   {
      return parentFamilly;
   }
}
