package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotDetailsManager;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.TextInputControlValidator;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ModalDebtStatementCreateView extends ApplicationModal{
	private AnchorPane rootPane;
	private ComboBox<Customer> customer;
	private DatePicker fromDate;
	private DatePicker toDate;
	private Button saveButton ;
	private Button cancelButton ;

	@Inject
	@Bundle({ CrudKeys.class, SalesOrder.class,ArticleLot.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@PostConstruct
	public void postConstruct(){
		LazyViewBuilder lvb = new LazyViewBuilder();
		fromDate =lvb.addDatePicker("SalesOrder_repport_fromDate_description.title", "fromDate", resourceBundle,"dd-MM-yyyy", locale);
		toDate =lvb.addDatePicker("SalesOrder_repport_toDate_description.title", "toDate", resourceBundle,"dd-MM-yyyy", locale);
		customer = lvb.addComboBox("SalesOrder_insurance_description.title", "salesAgent", resourceBundle);
		fromDate.setPrefWidth(300d);
		toDate.setPrefWidth(300d);
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lvb.toRows(),ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		rootPane = viewBuilder.toAnchorPane();

	}


	public void bind(ArticleLotDetailsManager model)
	{
		//		lotToDetails.valueProperty().bindBidirectional(model.lotToDetailsProperty());
		//		detailConfig.valueProperty().bindBidirectional(model.detailConfigProperty());
		//		detailsQty.numberProperty().bindBidirectional(model.detailsQtyProperty());
		//		lotQty.numberProperty().bindBidirectional(model.lotQtyProperty());
		//		//		targetQty.numberProperty().bindBidirectional(model.getDetailConfig(). targetQuantityProperty());
		//		//		targetPrice.numberProperty().bindBidirectional(model.getDetailConfig().salesPriceProperty());
	}

	public void addValidators()
	{
		//		detailsQty.focusedProperty().addListener(new TextInputControlFoccusChangedListener<ArticleLotDetailsManager>(textInputControlValidator, detailsQty, ArticleLotDetailsManager.class, "detailsQty", resourceBundle));
		// no active validator
		// no active validator
	}


	public AnchorPane getRootPane() {
		return rootPane;
	}


	public ComboBox<Customer> getCustomer() {
		return customer;
	}



	public DatePicker getFromDate() {
		return fromDate;
	}


	public DatePicker getToDate() {
		return toDate;
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




	//	public Set<ConstraintViolation<ArticleLotDetailsManager>> validate(ArticleLotDetailsManager model)
	//	{
	////		Set<ConstraintViolation<ArticleLotDetailsManager>> violations = new HashSet<ConstraintViolation<ArticleLotDetailsManager>>();
	////		violations.addAll(textInputControlValidator.validate(detailsQty, ArticleLotDetailsManager.class, "detailsQty", resourceBundle));
	////		violations.addAll(toOneAggreggationFieldValidator.validate(detailConfig, model.getDetailConfig(), ArticleLotDetailsManager.class, "detailConfig", resourceBundle));
	////		violations.addAll(toOneAggreggationFieldValidator.validate(lotToDetails, model.getLotToDetails(), ArticleLotDetailsManager.class, "lotToDetails", resourceBundle));
	////		return violations;
	//	}






}
