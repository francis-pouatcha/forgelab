package org.adorsys.adpharma.client.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

import jfxtras.scene.layout.HBox;
import jfxtras.scene.layout.VBox;

import org.apache.commons.lang3.StringUtils;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialog.Actions;

@Singleton
public class SalesKeyReciever  extends VBox{
	private Dialog dialog ;

	private PasswordField passwordField ;

	private Button okButton ;

	private Button koButton ;

	private Dialog.Actions userAction =  Dialog.Actions.CANCEL ;

	private Label salesKeyError ;

	public SalesKeyReciever() {
		super();
		salesKeyError = new Label();
		dialog = new Dialog(null, "Saisir La cle de vente");
		dialog.setResizable(false);
		dialog.setClosable(false);
		setPrefWidth(350d);
		setAlignment(Pos.TOP_CENTER);
		setSpacing(5);
		passwordField = new PasswordField();
		Separator firstSeparator = new Separator(Orientation.HORIZONTAL);
		Separator secondSeparator = new Separator(Orientation.HORIZONTAL);
		HBox actionBox = new HBox();
		actionBox.setAlignment(Pos.CENTER_RIGHT);
		actionBox.setSpacing(5);
		okButton = new Button("Save");
		koButton = new Button("Cancel");
		actionBox.getChildren().addAll(okButton,koButton);
		VBox.setMargin(passwordField, new Insets(2));

		getChildren().addAll(passwordField,firstSeparator ,salesKeyError,actionBox,secondSeparator);


		dialog.setContent(this);
	}

	@PostConstruct
	public void postConstruct(){
		okButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent actionEvent) {
				userAction = Actions.OK ;
				if(StringUtils.isBlank(passwordField.getText())){
					showKeyError();
					return;
				}
				close();
			}

		});



		koButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				userAction = Actions.CANCEL ;
				close();

			}
		});


		passwordField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				KeyCode code = event.getCode();
				if(code== KeyCode.ENTER){
					userAction = Actions.OK ;
					if(StringUtils.isBlank(passwordField.getText())){
						showKeyError();
						return;
					}
					close();
				}
			}
		});
	}

	public String show(){
		passwordField.setText(null);
		this.userAction = Actions.CANCEL;
		hideKeyError();
		passwordField.requestFocus();
		dialog.show();
		return getUserSalesKey();

	}

	public void close(){
		dialog.hide();
	}

	public String getUserSalesKey(){
		return passwordField.getText();
	}

	public Dialog getDialog() {
		return dialog;
	}

	public PasswordField getPasswordField() {
		return passwordField;
	}

	public Button getOkButton() {
		return okButton;
	}

	public Button getKoButton() {
		return koButton;
	}

	public Dialog.Actions getUserAction() {
		return userAction;
	}

	public void showKeyError(){
		salesKeyError.setText("Cle de vente Incorrect ");
		salesKeyError.setStyle("-fx-text-fill: red ");
	}

	public void hideKeyError(){
		salesKeyError.setText(null);
		salesKeyError.setStyle("-fx-text-fill: null ");
	}

}
