package org.adorsys.adpharma.client.jpa.inventory;

import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.BooleanStringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class InventorySectionForm extends AbstractToOneAssociation<Inventory, Agency>
{

	private TextField sectionCode;

	private TextField name;


	@Inject
	@Bundle({ CrudKeys.class, Section.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();
		sectionCode = viewBuilder.addTextField("Section_sectionCode_description.title", "sectionCode", resourceBundle);
		name = viewBuilder.addTextField("Section_name_description.title", "name", resourceBundle);
		gridRows = viewBuilder.toRows();
	}

	public void bind(Inventory model)
	{
		sectionCode.textProperty().bindBidirectional(model.getAgency().agencyNumberProperty());
		name.textProperty().bindBidirectional(model.getAgency().nameProperty());
	}

	public void update(InventorySection data)
	{
		sectionCode.textProperty().set(data.sectionCodeProperty().get());
		name.textProperty().set(data.nameProperty().get());
	}

	public TextField getSectionCode()
	{
		return sectionCode;
	}

	public TextField getName()
	{
		return name;
	}


}
