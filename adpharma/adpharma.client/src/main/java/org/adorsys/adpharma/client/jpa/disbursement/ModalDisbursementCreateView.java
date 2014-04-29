package org.adorsys.adpharma.client.jpa.disbursement;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;
@Singleton
public class ModalDisbursementCreateView extends ApplicationModal{


	private AnchorPane rootPane;

	private Button saveButton;

	private Button resetButton;

	private Button cancelButton;

	
	@Inject
	private DisbursementView view;

	@Inject
	private Disbursement cashOut;

	@Inject
	@Bundle({ CrudKeys.class, Disbursement.class })
	private ResourceBundle resourceBundle;

	@Override
	public Pane getRootPane() {
		return rootPane ;
	}
	public DisbursementView getView() {
		return view;
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


	public void bind(Disbursement model)
	{
		this.cashOut = model;
		view.bind(this.cashOut);
		
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
