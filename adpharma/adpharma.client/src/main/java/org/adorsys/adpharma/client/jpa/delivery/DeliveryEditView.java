package org.adorsys.adpharma.client.jpa.delivery;

import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlFoccusChangedListener;

import javafx.scene.control.TextField;

import java.util.Locale;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.ViewType;

import de.jensd.fx.fontawesome.AwesomeIcon;

import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;

@Singleton
public class DeliveryEditView
{

	AnchorPane rootPane;

	private Button saveButton;

	private Button cancelButton;

	@Inject
	private DeliveryView view;

	@Inject
	FXMLLoader fxmlLoader;

	@Inject
	Locale locale;

	@Inject
	@Bundle({ CrudKeys.class})
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		//		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addMainForm(view, ViewType.EDIT, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "cancelButton", resourceBundle, AwesomeIcon.STOP);
		rootPane = viewBuilder.toAnchorPane();

	}
	 public void bind(Delivery model)
	   {
	      view.bind(model);
	   }


	public AnchorPane getRootPane()
	{
		return rootPane;
	}

	public Button getSaveButton()
	{
		return saveButton;
	}

	public Button getCancelButton()
	{
		return cancelButton;
	}

	public DeliveryView getView()
	{
		return view;
	}



}
