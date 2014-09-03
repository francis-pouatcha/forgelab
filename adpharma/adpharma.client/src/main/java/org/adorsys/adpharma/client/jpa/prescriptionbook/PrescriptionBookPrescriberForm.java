package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class PrescriptionBookPrescriberForm extends AbstractToOneAssociation<PrescriptionBook, Prescriber>
{

	private TextField name;

	private TextField phone;

	private TextField street;

	private TextField city;

	@Inject
	@Bundle({ CrudKeys.class, Prescriber.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();
		name = viewBuilder.addTextField("Prescriber_name_description.title", "name", resourceBundle);
		phone = viewBuilder.addTextField("Prescriber_phone_description.title", "phone", resourceBundle);
		street = viewBuilder.addTextField("Prescriber_street_description.title", "street", resourceBundle);
		city = viewBuilder.addTextField("Prescriber_city_description.title", "city", resourceBundle);

		gridRows = viewBuilder.toRows();
	}

	public void bind(PrescriptionBook model)
	{
		name.textProperty().bindBidirectional(model.getPrescriber().nameProperty());
		phone.textProperty().bindBidirectional(model.getPrescriber().phoneProperty());
		street.textProperty().bindBidirectional(model.getPrescriber().streetProperty());
		city.textProperty().bindBidirectional(model.getPrescriber().cityProperty());
	}

	public void update(PrescriptionBookPrescriber data)
	{
		if(data!=null){
			name.textProperty().set(data.nameProperty().get());
			phone.textProperty().set(data.phoneProperty().get());
			street.textProperty().set(data.streetProperty().get());
			city.textProperty().set(data.cityProperty().get());
		}
	}

	public TextField getName()
	{
		return name;
	}

	public TextField getPhone()
	{
		return phone;
	}

	public TextField getStreet()
	{
		return street;
	}

	public TextField getCity()
	{
		return city;
	}
}
