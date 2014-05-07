package org.adorsys.adpharma.client.jpa.supplierinvoice;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SupplierInvoiceCreatingUserSelection extends AbstractSelection<SupplierInvoice, Login>
{

   private ComboBox<SupplierInvoiceCreatingUser> creatingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, SupplierInvoice.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      creatingUser = viewBuilder.addComboBox("SupplierInvoice_creatingUser_description.title", "creatingUser", resourceBundle, false);

      creatingUser.setCellFactory(new Callback<ListView<SupplierInvoiceCreatingUser>, ListCell<SupplierInvoiceCreatingUser>>()
      {
         @Override
         public ListCell<SupplierInvoiceCreatingUser> call(ListView<SupplierInvoiceCreatingUser> listView)
         {
            return new SupplierInvoiceCreatingUserListCell();
         }
      });
      creatingUser.setButtonCell(new SupplierInvoiceCreatingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(SupplierInvoice model)
   {
      creatingUser.valueProperty().bindBidirectional(model.creatingUserProperty());
   }

   public ComboBox<SupplierInvoiceCreatingUser> getCreatingUser()
   {
      return creatingUser;
   }
}
