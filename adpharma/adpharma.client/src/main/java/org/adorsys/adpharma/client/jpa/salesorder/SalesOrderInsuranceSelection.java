package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.ResourceBundle;

import javafx.scene.control.Button;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class SalesOrderInsuranceSelection extends AbstractSelection<SalesOrder, Insurrance>
{

   private Button selectButton;

   @Inject
   @Bundle({ CrudKeys.class, Insurrance.class, SalesOrder.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      selectButton = viewBuilder.addButton(
            "SalesOrder_insurance_description.title", "Entity_select.title",
            "selectButton", resourceBundle);
      gridRows = viewBuilder.toRows();
   }

   public void bind(SalesOrder model)
   {
   }

   public Button getSelectButton()
   {
      return selectButton;
   }

   public Button getInsurance()
   {
      return selectButton; // select button required to mark invalid field.
   }
}
