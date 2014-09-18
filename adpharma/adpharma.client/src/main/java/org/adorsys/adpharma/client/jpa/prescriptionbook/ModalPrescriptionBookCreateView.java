package org.adorsys.adpharma.client.jpa.prescriptionbook;

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
public class ModalPrescriptionBookCreateView  extends ApplicationModal{
	private AnchorPane rootPane;

	private Button saveButton;

	private Button resetButton;
	

	private Button cancelButton;

	private Button createPrescriberButton;

	private Button createHospitalButton;

	@Inject
	private PrescriptionBookView view;

	@Inject
	private PrescriptionBook prescriptionBook;

	@Inject
	@Bundle({ CrudKeys.class, PrescriptionBook.class })
	private ResourceBundle resourceBundle;

	@Override
	public Pane getRootPane() {
		return rootPane ;
	}
	public PrescriptionBookView getView() {
		return view;
	}

	@PostConstruct
	public void postConstruct()
	{

		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addMainForm(view, ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		createHospitalButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.HOSPITAL);
		createHospitalButton.setText("Hopital");
		createPrescriberButton = viewBuilder.addButton(buttonBar, "Entity_reset.title", "resetButton", resourceBundle, AwesomeIcon.USER);
		createPrescriberButton.setText("Prescripteur");
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		resetButton = viewBuilder.addButton(buttonBar, "Entity_reset.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "cancelButton", resourceBundle, AwesomeIcon.TRASH_ALT);
		
		rootPane = viewBuilder.toAnchorPane();
		rootPane.setPrefWidth(600d);

	}


	public void bind(PrescriptionBook model)
	{
		this.prescriptionBook = model;
		view.bind(this.prescriptionBook);

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
	public Button getCreateHospitalButton() {
		return createHospitalButton;
	}
	public void setCreateHospitalButton(Button createHospitalButton) {
		this.createHospitalButton = createHospitalButton;
	}
	public Button getCreatePrescriberButton() {
		return createPrescriberButton;
	}
	public void setCreatePrescriberButton(Button createPrescriberButton) {
		createPrescriberButton = createPrescriberButton;
	}

}
