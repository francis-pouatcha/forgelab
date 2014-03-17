package org.adorsys.adpharma.client.jpa.person;

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
import org.adorsys.adpharma.client.jpa.person.Person;

public class PersonLoginSelection extends AbstractSelection<Person, Login>
{

   private ComboBox<Login> login;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, Person.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      login = viewBuilder.addComboBox("Person_login_description.title", "login", resourceBundle, false);

      login.setCellFactory(new Callback<ListView<Login>, ListCell<Login>>()
      {
         @Override
         public ListCell<Login> call(ListView<Login> listView)
         {
            return new PersonLoginListCell();
         }
      });
      login.setButtonCell(new PersonLoginListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Person model)
   {
   }

   public ComboBox<Login> getLogin()
   {
      return login;
   }
}
