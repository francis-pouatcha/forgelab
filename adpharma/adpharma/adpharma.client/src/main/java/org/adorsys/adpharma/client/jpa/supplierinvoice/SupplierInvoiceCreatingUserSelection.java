package org.adorsys.adpharma.client.jpa.supplierinvoice;

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

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.supplierinvoice.SupplierInvoice;

public class SupplierInvoiceCreatingUserSelection extends AbstractSelection<SupplierInvoice, Login>
{

   private ComboBox<Login> creatingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, SupplierInvoice.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      creatingUser = viewBuilder.addComboBox("SupplierInvoice_creatingUser_description.title", "creatingUser", resourceBundle, false);

      creatingUser.setCellFactory(new Callback<ListView<Login>, ListCell<Login>>()
      {
         @Override
         public ListCell<Login> call(ListView<Login> listView)
         {
            return new SupplierInvoiceCreatingUserListCell();
         }
      });
      creatingUser.setButtonCell(new SupplierInvoiceCreatingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoice model)
   {
   }

   public ComboBox<Login> getCreatingUser()
   {
      return creatingUser;
   }
}
