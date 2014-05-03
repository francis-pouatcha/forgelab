package org.adorsys.adpharma.client.jpa.stockmovement;

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

public class StockMovementCreatingUserSelection extends AbstractSelection<StockMovement, Login>
{

   private ComboBox<StockMovementCreatingUser> creatingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, StockMovement.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      creatingUser = viewBuilder.addComboBox("StockMovement_creatingUser_description.title", "creatingUser", resourceBundle, false);

      creatingUser.setCellFactory(new Callback<ListView<StockMovementCreatingUser>, ListCell<StockMovementCreatingUser>>()
      {
         @Override
         public ListCell<StockMovementCreatingUser> call(ListView<StockMovementCreatingUser> listView)
         {
            return new StockMovementCreatingUserListCell();
         }
      });
      creatingUser.setButtonCell(new StockMovementCreatingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(StockMovement model)
   {
      creatingUser.valueProperty().bindBidirectional(model.creatingUserProperty());
   }

   public ComboBox<StockMovementCreatingUser> getCreatingUser()
   {
      return creatingUser;
   }
}
