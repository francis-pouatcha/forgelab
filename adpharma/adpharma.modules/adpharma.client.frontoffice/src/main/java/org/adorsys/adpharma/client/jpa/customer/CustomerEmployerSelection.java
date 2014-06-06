package org.adorsys.adpharma.client.jpa.customer;

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

import org.adorsys.adpharma.client.jpa.employer.Employer;
import org.adorsys.adpharma.client.jpa.customer.Customer;

public class CustomerEmployerSelection extends AbstractSelection<Customer, Employer>
{

   private ComboBox<CustomerEmployer> employer;

   @Inject
   @Bundle({ CrudKeys.class, Employer.class, Customer.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      employer = viewBuilder.addComboBox("Customer_employer_description.title", "employer", resourceBundle, false);

      employer.setCellFactory(new Callback<ListView<CustomerEmployer>, ListCell<CustomerEmployer>>()
      {
         @Override
         public ListCell<CustomerEmployer> call(ListView<CustomerEmployer> listView)
         {
            return new CustomerEmployerListCell();
         }
      });
      employer.setButtonCell(new CustomerEmployerListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(Customer model)
   {
      employer.valueProperty().bindBidirectional(model.employerProperty());
   }

   public ComboBox<CustomerEmployer> getEmployer()
   {
      return employer;
   }
}
