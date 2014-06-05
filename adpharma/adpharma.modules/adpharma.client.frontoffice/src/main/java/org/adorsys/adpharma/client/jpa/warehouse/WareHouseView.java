package org.adorsys.adpharma.client.jpa.warehouse;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class WareHouseView extends AbstractForm<WareHouse>
{

   private TextField name;

   @Inject
   private WareHouseAgencyForm wareHouseAgencyForm;
   @Inject
   private WareHouseAgencySelection wareHouseAgencySelection;

   @Inject
   @Bundle({ CrudKeys.class, WareHouse.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      name = viewBuilder.addTextField("WareHouse_name_description.title", "name", resourceBundle);
      viewBuilder.addTitlePane("WareHouse_agency_description.title", resourceBundle);
      viewBuilder.addSubForm("WareHouse_agency_description.title", "agency", resourceBundle, wareHouseAgencyForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("WareHouse_agency_description.title", "agency", resourceBundle, wareHouseAgencySelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
   }

   public Set<ConstraintViolation<WareHouse>> validate(WareHouse model)
   {
      Set<ConstraintViolation<WareHouse>> violations = new HashSet<ConstraintViolation<WareHouse>>();
      return violations;
   }

   public void bind(WareHouse model)
   {
      name.textProperty().bindBidirectional(model.nameProperty());
      wareHouseAgencyForm.bind(model);
      wareHouseAgencySelection.bind(model);
   }

   public TextField getName()
   {
      return name;
   }

  

   public WareHouseAgencyForm getWareHouseAgencyForm()
   {
      return wareHouseAgencyForm;
   }

   public WareHouseAgencySelection getWareHouseAgencySelection()
   {
      return wareHouseAgencySelection;
   }
}
