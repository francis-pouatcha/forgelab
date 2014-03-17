package org.adorsys.adpharma.client.jpa.customerinvoice;

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
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;

public class CustomerInvoiceCreatingUserSelection extends AbstractSelection<CustomerInvoice, Login>
{

   private ComboBox<Login> creatingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, CustomerInvoice.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      creatingUser = viewBuilder.addComboBox("CustomerInvoice_creatingUser_description.title", "creatingUser", resourceBundle, false);

      creatingUser.setCellFactory(new Callback<ListView<Login>, ListCell<Login>>()
      {
         @Override
         public ListCell<Login> call(ListView<Login> listView)
         {
            return new CustomerInvoiceCreatingUserListCell();
         }
      });
      creatingUser.setButtonCell(new CustomerInvoiceCreatingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerInvoice model)
   {
   }

   public ComboBox<Login> getCreatingUser()
   {
      return creatingUser;
   }
}
