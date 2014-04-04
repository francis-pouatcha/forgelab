package org.adorsys.adpharma.client.jpa.insurrance;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerView;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.controlsfx.dialog.Dialogs;

import de.jensd.fx.fontawesome.AwesomeIcon;
@Singleton
public class ModalInsurranceCreateView extends ApplicationModal {


	@Inject
	private InsurranceView view;

	private AnchorPane rootPane;

	private Button saveButton;

	private Button resetButton;

	private Button cancelButton;

	@Inject
	@Bundle({ CrudKeys.class, Insurrance.class })
	private ResourceBundle resourceBundle;


	@Override
	public AnchorPane getRootPane() {
		return rootPane;
	}

	@PostConstruct
	public void postConstruct()
	{

		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addMainForm(view, ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		resetButton = viewBuilder.addButton(buttonBar, "Entity_reset.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "cancelButton", resourceBundle, AwesomeIcon.TRASH_ALT);
		rootPane = viewBuilder.toAnchorPane();
		rootPane.setPrefWidth(600d);
	}
	
	public void bind(Insurrance model)
	{
		view.bind(model);
		
	}

	public InsurranceView getView() {
		return view;
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public Button getResetButton() {
		return resetButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	
	
}
