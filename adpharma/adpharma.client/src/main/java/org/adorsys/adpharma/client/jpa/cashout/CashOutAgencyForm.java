package org.adorsys.adpharma.client.jpa.cashout;

import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class CashOutAgencyForm  extends AbstractToOneAssociation<CashOut, Agency>
{

	   private TextField agencyNumber;

	   private TextField name;

	   private CheckBox active;

	   private TextField phone;

	   private TextField fax;

	   @Inject
	   @Bundle({ CrudKeys.class, Agency.class })
	   private ResourceBundle resourceBundle;

	   @PostConstruct
	   public void postConstruct()
	   {
	      LazyViewBuilder viewBuilder = new LazyViewBuilder();
	      agencyNumber = viewBuilder.addTextField("Agency_agencyNumber_description.title", "agencyNumber", resourceBundle);
	      name = viewBuilder.addTextField("Agency_name_description.title", "name", resourceBundle);
	      active = viewBuilder.addCheckBox("Agency_active_description.title", "active", resourceBundle);
	      phone = viewBuilder.addTextField("Agency_phone_description.title", "phone", resourceBundle);
	      fax = viewBuilder.addTextField("Agency_fax_description.title", "fax", resourceBundle);

	      gridRows = viewBuilder.toRows();
	   }

	   public void bind(CashOut model)
	   {
	      agencyNumber.textProperty().bindBidirectional(model.getAgency().agencyNumberProperty());
	      name.textProperty().bindBidirectional(model.getAgency().nameProperty());
	      phone.textProperty().bindBidirectional(model.getAgency().phoneProperty());
//	      active.textProperty().bindBidirectional(model.getAgency().activeProperty(), new BooleanStringConverter());
//	      fax.textProperty().bindBidirectional(model.getAgency().faxProperty());
	   }

	   public void update(CashOutAgency data)
	   {
	      agencyNumber.textProperty().set(data.agencyNumberProperty().get());
	      name.textProperty().set(data.nameProperty().get());
	      phone.textProperty().set(data.phoneProperty().get());
//	      active.textProperty().set(new BooleanStringConverter().toString(data.activeProperty().get()));
//	      fax.textProperty().set(data.faxProperty().get());
	   }

	   public TextField getAgencyNumber()
	   {
	      return agencyNumber;
	   }

	   public TextField getName()
	   {
	      return name;
	   }

	   public CheckBox getActive()
	   {
	      return active;
	   }

	   public TextField getPhone()
	   {
	      return phone;
	   }

	   public TextField getFax()
	   {
	      return fax;
	   }
}
