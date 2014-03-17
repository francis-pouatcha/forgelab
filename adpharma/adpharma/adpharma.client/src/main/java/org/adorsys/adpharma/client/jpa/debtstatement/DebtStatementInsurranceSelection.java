package org.adorsys.adpharma.client.jpa.debtstatement;

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

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatement;

public class DebtStatementInsurranceSelection extends AbstractSelection<DebtStatement, Customer>
{

   private ComboBox<Customer> insurrance;

   @Inject
   @Bundle({ CrudKeys.class, Customer.class, DebtStatement.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      insurrance = viewBuilder.addComboBox("DebtStatement_insurrance_description.title", "insurrance", resourceBundle, false);

      insurrance.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>()
      {
         @Override
         public ListCell<Customer> call(ListView<Customer> listView)
         {
            return new DebtStatementInsurranceListCell();
         }
      });
      insurrance.setButtonCell(new DebtStatementInsurranceListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(DebtStatement model)
   {
   }

   public ComboBox<Customer> getInsurrance()
   {
      return insurrance;
   }
}
