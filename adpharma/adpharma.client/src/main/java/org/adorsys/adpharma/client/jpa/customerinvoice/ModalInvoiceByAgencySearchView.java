package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ModalInvoiceByAgencySearchView extends ApplicationModal {
	private AnchorPane rootPane;

	private Button saveButton;

	private Button cancelButton;

	@Inject
	private InvoiceByAgencySearchView view;

	@Inject
	@Bundle({ CrudKeys.class})
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addMainForm(view, ViewType.EDIT, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "saveButton", resourceBundle, AwesomeIcon.SEARCH_PLUS);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "searchButton", resourceBundle, AwesomeIcon.BUG);
		rootPane = viewBuilder.toAnchorPane();
		rootPane.setPrefWidth(400d);

	}

	@Override
	public Pane getRootPane() {
		return rootPane ;

	}

	public void bind(InvoiceByAgencyPrintInput model)
	{
		view.bind(model);
	}


	public Button getSaveButton()
	{
		return saveButton;
	}

	public Button getCancelButton()
	{
		return cancelButton;
	}

	public InvoiceByAgencySearchView getView()
	{
		return view;
	}

}
