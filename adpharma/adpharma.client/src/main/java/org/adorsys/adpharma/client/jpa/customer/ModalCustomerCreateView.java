package org.adorsys.adpharma.client.jpa.customer;

import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceCreateService;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceView;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ModalCustomerCreateView  extends ApplicationModal{

	
	private Customer customer ;

	@Inject
	private CustomerView view;

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
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				closeDialog();
				
			}
		});
	}
	
	public void bind(Customer model)
	{
		this.customer = model;
		view.bind(this.customer);
		
	}

	
}
