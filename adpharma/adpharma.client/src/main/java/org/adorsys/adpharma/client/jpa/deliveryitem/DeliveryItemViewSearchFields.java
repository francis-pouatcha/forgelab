package org.adorsys.adpharma.client.jpa.deliveryitem;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DeliveryItemViewSearchFields extends AbstractForm<DeliveryItem>
{

	private TextField internalPic;

	private TextField mainPic;

	private TextField secondaryPic;

	private TextField articleName;

	@Inject
	@Bundle({ CrudKeys.class, DeliveryItem.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();
		      internalPic = viewBuilder.addTextField("DeliveryItem_internalPic_description.title", "internalPic", resourceBundle);
		mainPic = viewBuilder.addTextField("DeliveryItem_mainPic_description.title", "mainPic", resourceBundle);
		//      secondaryPic = viewBuilder.addTextField("DeliveryItem_secondaryPic_description.title", "secondaryPic", resourceBundle);
		articleName = viewBuilder.addTextField("DeliveryItem_articleName_description.title", "articleName", resourceBundle);

		gridRows = viewBuilder.toRows();
	}

	public void bind(DeliveryItem model)
	{
		      internalPic.textProperty().bindBidirectional(model.internalPicProperty());
		mainPic.textProperty().bindBidirectional(model.mainPicProperty());
		//      secondaryPic.textProperty().bindBidirectional(model.secondaryPicProperty());
		articleName.textProperty().bindBidirectional(model.articleNameProperty());

	}

	public TextField getInternalPic()
	{
		return internalPic;
	}

	public TextField getMainPic()
	{
		return mainPic;
	}

	public TextField getSecondaryPic()
	{
		return secondaryPic;
	}

	public TextField getArticleName()
	{
		return articleName;
	}
}
