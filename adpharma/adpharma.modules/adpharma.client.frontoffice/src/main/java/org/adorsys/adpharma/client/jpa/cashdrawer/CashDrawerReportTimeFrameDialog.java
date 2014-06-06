package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ModalPanelBuilder;

public class CashDrawerReportTimeFrameDialog {

	private VBox rootNode;

	private Button okButton;
	private Button cancelButton;

	private CalendarTextField startTime;

	private CalendarTextField endTime;

	@Inject
	@Bundle({ CrudKeys.class, CashDrawerReportPrintTemplate.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	private Stage dialog;

	@PostConstruct
	public void postConstruct() {
		ModalPanelBuilder modal = new ModalPanelBuilder(2);
		modal.getRootNode().setPrefWidth(400);
		startTime = modal.addCalendarTextField(
				"CashDrawerReportPrintTemplate_startTime.title", "startTime",
				resourceBundle, "dd-MM-yyyy HH:mm", locale);
		endTime = modal.addCalendarTextField(
				"CashDrawerReportPrintTemplate_endTime.title", "endTime",
				resourceBundle, "dd-MM-yyyy HH:mm", locale);
		modal.addButtonBar();
		okButton = modal.addButton("okButton", "Entity_ok.title",
				resourceBundle);
		cancelButton = modal.addButton("cancelButton", "Entity_cancel.title",
				resourceBundle);
		rootNode = modal.getRootNode();
	}

	public VBox getRootNode() {
		return rootNode;
	}

	public void display() {
		if (dialog == null) {
			dialog = new Stage();
			dialog.initModality(Modality.APPLICATION_MODAL);
			// Stage
			Scene scene = new Scene(rootNode);
			scene.getStylesheets().add("/styles/application.css");
			dialog.setScene(scene);
			dialog.setTitle(resourceBundle.getString("CashDrawerReportPrintTemplate_select_timeFrame.title"));
		}
		dialog.show();
	}

	public void closeDialog() {
		// if (dialog != null) {
		dialog.close();
		// dialog = null;
		// }
	}

	public boolean hasDialog() {
		return dialog != null;
	}

	public Button getOkButton() {
		return okButton;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public CalendarTextField getStartTime() {
		return startTime;
	}

	public CalendarTextField getEndTime() {
		return endTime;
	}
}
