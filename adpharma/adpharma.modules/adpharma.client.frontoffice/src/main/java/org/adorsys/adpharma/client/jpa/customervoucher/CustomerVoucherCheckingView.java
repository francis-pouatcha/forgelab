package org.adorsys.adpharma.client.jpa.customervoucher;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.interceptor.InterceptorBinding;

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialog.Actions;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class CustomerVoucherCheckingView extends VBox{
	private Dialog dialog ;

	private TextField voucherNumber;

	private BigDecimalField restAmount ;

	private Button saveButton;

	private Button cancelButton;

	@Inject
	@Bundle({ CrudKeys.class, CustomerVoucher.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale ;
	@PostConstruct
	public void postConstruct(){
		LazyViewBuilder laziviewBuilder = new LazyViewBuilder();
		voucherNumber = laziviewBuilder.addTextField("CustomerVoucher_voucherNumber_description.title", "voucherNumber", resourceBundle);
		restAmount = laziviewBuilder.addBigDecimalField("CustomerVoucher_restAmount_description.title", "restAmount", resourceBundle, NumberType.INTEGER, locale);

		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(laziviewBuilder.toRows(), ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		getChildren().add(viewBuilder.toAnchorPane()) ;

		dialog = new Dialog(null, "Saisir Les infos de l'avoir");
		dialog.setResizable(false);
		dialog.setClosable(false);
		setPrefWidth(350d);
		setAlignment(Pos.TOP_CENTER);
		setSpacing(5);
		dialog.setContent(this);
		
	}



	public void show(){
		reset();
		dialog.show();

	}
	
	public void reset(){
		voucherNumber.setText(null);
		restAmount.setNumber(BigDecimal.ZERO);
	}

	public void close(){
		dialog.hide();
	}



	public Dialog getDialog() {
		return dialog;
	}



	public TextField getVoucherNumber() {
		return voucherNumber;
	}



	public BigDecimalField getRestAmount() {
		return restAmount;
	}



	public Button getSaveButton() {
		return saveButton;
	}



	public Button getCancelButton() {
		return cancelButton;
	}


	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}



	public Locale getLocale() {
		return locale;
	}



}
