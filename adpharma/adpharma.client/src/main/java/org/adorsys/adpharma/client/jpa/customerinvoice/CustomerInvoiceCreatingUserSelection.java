package org.adorsys.adpharma.client.jpa.customerinvoice;

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

public class CustomerInvoiceCreatingUserSelection extends AbstractSelection<CustomerInvoice, Login>
{

   private ComboBox<CustomerInvoiceCreatingUser> creatingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, CustomerInvoice.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      creatingUser = viewBuilder.addComboBox("CustomerInvoice_creatingUser_description.title", "creatingUser", resourceBundle, false);

      creatingUser.setCellFactory(new Callback<ListView<CustomerInvoiceCreatingUser>, ListCell<CustomerInvoiceCreatingUser>>()
      {
         @Override
         public ListCell<CustomerInvoiceCreatingUser> call(ListView<CustomerInvoiceCreatingUser> listView)
         {
            return new CustomerInvoiceCreatingUserListCell();
         }
      });
      creatingUser.setButtonCell(new CustomerInvoiceCreatingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerInvoice model)
   {
      creatingUser.valueProperty().bindBidirectional(model.creatingUserProperty());
   }

   public ComboBox<CustomerInvoiceCreatingUser> getCreatingUser()
   {
      return creatingUser;
   }
}
