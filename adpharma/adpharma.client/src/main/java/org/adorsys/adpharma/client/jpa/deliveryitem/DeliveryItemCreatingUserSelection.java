package org.adorsys.adpharma.client.jpa.deliveryitem;

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

public class DeliveryItemCreatingUserSelection extends AbstractSelection<DeliveryItem, Login>
{

   private ComboBox<DeliveryItemCreatingUser> creatingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, DeliveryItem.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      creatingUser = viewBuilder.addComboBox("DeliveryItem_creatingUser_description.title", "creatingUser", resourceBundle, false);

      creatingUser.setCellFactory(new Callback<ListView<DeliveryItemCreatingUser>, ListCell<DeliveryItemCreatingUser>>()
      {
         @Override
         public ListCell<DeliveryItemCreatingUser> call(ListView<DeliveryItemCreatingUser> listView)
         {
            return new DeliveryItemCreatingUserListCell();
         }
      });
      creatingUser.setButtonCell(new DeliveryItemCreatingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(DeliveryItem model)
   {
      creatingUser.valueProperty().bindBidirectional(model.creatingUserProperty());
   }

   public ComboBox<DeliveryItemCreatingUser> getCreatingUser()
   {
      return creatingUser;
   }
}
