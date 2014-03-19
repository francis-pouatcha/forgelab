package org.adorsys.adpharma.client.jpa.login;

import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class LoginAgencyForm extends AbstractToOneAssociation<Login, Agency>
{

	private TextField agencyNumber;

	private TextField name;


	@Inject
	@Bundle({ CrudKeys.class, Agency.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();
		agencyNumber = viewBuilder.addTextField("Agency_agencyNumber_description.title", "agencyNumber", resourceBundle);
		name = viewBuilder.addTextField("Agency_name_description.title", "name", resourceBundle);
		gridRows = viewBuilder.toRows();
	}

	public void bind(Login model)
	{
		agencyNumber.textProperty().bindBidirectional(model.getAgency().agencyNumberProperty());
		name.textProperty().bindBidirectional(model.getAgency().nameProperty());
	}

	public void setAgencyNumber(TextField agencyNumber) {
		this.agencyNumber = agencyNumber;
	}

	public void setName(TextField name) {
		this.name = name;
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}


}
