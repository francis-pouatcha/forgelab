package org.adorsys.adpharma.client.jpa.delivery;

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
import org.adorsys.adpharma.client.jpa.delivery.Delivery;

public class DeliveryCreatingUserSelection extends AbstractSelection<Delivery, Login>
{

   private ComboBox<DeliveryCreatingUser> creatingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, Delivery.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      creatingUser = viewBuilder.addComboBox("Delivery_creatingUser_description.title", "creatingUser", resourceBundle, false);

      creatingUser.setCellFactory(new Callback<ListView<DeliveryCreatingUser>, ListCell<DeliveryCreatingUser>>()
      {
         @Override
         public ListCell<DeliveryCreatingUser> call(ListView<DeliveryCreatingUser> listView)
         {
            return new DeliveryCreatingUserListCell();
         }
      });
      creatingUser.setButtonCell(new DeliveryCreatingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Delivery model)
   {
      creatingUser.valueProperty().bindBidirectional(model.creatingUserProperty());
   }

   public ComboBox<DeliveryCreatingUser> getCreatingUser()
   {
      return creatingUser;
   }
}
