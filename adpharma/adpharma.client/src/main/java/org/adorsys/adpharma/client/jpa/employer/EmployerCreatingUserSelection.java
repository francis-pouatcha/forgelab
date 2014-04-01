package org.adorsys.adpharma.client.jpa.employer;

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
import org.adorsys.adpharma.client.jpa.employer.Employer;

public class EmployerCreatingUserSelection extends AbstractSelection<Employer, Login>
{

   private ComboBox<EmployerCreatingUser> creatingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, Employer.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      creatingUser = viewBuilder.addComboBox("Employer_creatingUser_description.title", "creatingUser", resourceBundle, false);

      creatingUser.setCellFactory(new Callback<ListView<EmployerCreatingUser>, ListCell<EmployerCreatingUser>>()
      {
         @Override
         public ListCell<EmployerCreatingUser> call(ListView<EmployerCreatingUser> listView)
         {
            return new EmployerCreatingUserListCell();
         }
      });
      creatingUser.setButtonCell(new EmployerCreatingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Employer model)
   {
      creatingUser.valueProperty().bindBidirectional(model.creatingUserProperty());
   }

   public ComboBox<EmployerCreatingUser> getCreatingUser()
   {
      return creatingUser;
   }
}
