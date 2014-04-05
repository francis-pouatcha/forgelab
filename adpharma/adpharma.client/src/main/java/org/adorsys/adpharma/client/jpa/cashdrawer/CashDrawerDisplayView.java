package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCreatingUser;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCustomer;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInsurance;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

@Singleton
public class CashDrawerDisplayView
{


	@FXML
	private BorderPane rootPane;

	private Button editButton;

	private Button removeButton;

	private HBox buttonBarLeft;

	private Button confirmSelectionButton;

	@FXML
	private GridPane invoiceHeadGrid ;

	private TextField invoiceNumber;

	private BigDecimalField customerRestTopay;

	private BigDecimalField insurranceRestTopay;

	private BigDecimalField amountDiscount;

	private ComboBox<CustomerInvoiceCustomer> customer;

	private ComboBox<CustomerInvoiceInsurance> insurrance;

	private ComboBox<CustomerInvoiceCreatingUser> creatingUser;

	private Button cancelButton;

	@FXML
	private GridPane invoiceSearchGrid;

	private TextField invoiceNumberToSearch ;

	private ComboBox<CashDrawer> openCashDrawer; 

	private Button searchButton;
	@Inject
	private CashDrawerView view;

	@FXML
	private Button openCashDrawerButton;

	@FXML
	private Button closeCashDrawerButton;

	@Inject
	@Bundle({ CrudKeys.class, CashDrawer.class ,CustomerInvoice.class })
	private ResourceBundle resourceBundle;

	@Inject
	private FXMLLoader fxmlLoader;

	@Inject
	private Locale locale;


	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		//      ViewBuilder viewBuilder = new ViewBuilder();
		//      viewBuilder.addMainForm(view, ViewType.DISPLAY, true);
		//      viewBuilder.addSeparator();
		//      List<HBox> doubleButtonBar = viewBuilder.addDoubleButtonBar();
		//      buttonBarLeft = doubleButtonBar.get(0);
		//      confirmSelectionButton = viewBuilder.addButton(buttonBarLeft, "Entity_select.title", "confirmSelectionButton", resourceBundle);
		//      HBox buttonBarRight = doubleButtonBar.get(1);
		//      editButton = viewBuilder.addButton(buttonBarRight, "Entity_edit.title", "editButton", resourceBundle, AwesomeIcon.EDIT);
		//      removeButton = viewBuilder.addButton(buttonBarRight, "Entity_remove.title", "removeButton", resourceBundle, AwesomeIcon.TRASH_ALT);
		//      searchButton = viewBuilder.addButton(buttonBarRight, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//      rootPane = viewBuilder.toAnchorPane();
		buildinvoiceHeadGrid();
		buildinvoiceSearchGrid();
	}

	public void buildinvoiceSearchGrid(){
		invoiceNumberToSearch = ViewBuilderUtils.newTextField( "invoiceNumberToSearch", false);
		invoiceNumberToSearch.setPrefHeight(50d);

		openCashDrawer = ViewBuilderUtils.newComboBox(null,"openCashDrawer", false);
		openCashDrawer.setPrefWidth(200d);
		openCashDrawer.setPrefHeight(50d);

		searchButton = ViewBuilderUtils.newButton("Entity_search.text", "ok", resourceBundle, AwesomeIcon.SEARCH_PLUS);
		searchButton.setPrefHeight(50d);

		invoiceSearchGrid.addRow(0,new Label("invoiceNumber"),new Label("Open CashDrawer"));
		invoiceSearchGrid.addRow(1,invoiceNumberToSearch,openCashDrawer,searchButton);
		//		invoiceSearchGrid.setGridLinesVisible(true);
	}

	public void buildinvoiceHeadGrid(){
		invoiceNumber = ViewBuilderUtils.newTextField( "invoiceNumber", true);

		creatingUser = ViewBuilderUtils.newComboBox(null,"creatingUser", false);
		creatingUser.setPrefWidth(130d);

		customer = ViewBuilderUtils.newComboBox(null, "customer",false);
		customer.setPrefWidth(130d);

		insurrance = ViewBuilderUtils.newComboBox(null, "insurrance",false);
		insurrance.setPrefWidth(130d);

		amountDiscount = ViewBuilderUtils.newBigDecimalField("amountDiscount", NumberType.CURRENCY, locale,false);

		customerRestTopay = ViewBuilderUtils.newBigDecimalField( "customerRestTopay", NumberType.CURRENCY, locale,false);

		insurranceRestTopay = ViewBuilderUtils.newBigDecimalField( "insurranceRestTopay", NumberType.CURRENCY, locale,false);


		cancelButton = ViewBuilderUtils.newButton("Entity_cancel.text", "ok", resourceBundle, AwesomeIcon.ASTERISK);

		invoiceHeadGrid.addRow(0,new Label("invoiceNumber"),new Label("Saller"),new Label("customer"),new Label("insurrance")
		,new Label("discount"),new Label("Customer part"),new Label("Insurrance part"));
		invoiceHeadGrid.addRow(1,invoiceNumber,creatingUser,customer,insurrance,amountDiscount,customerRestTopay,insurranceRestTopay,cancelButton);
		invoiceHeadGrid.setGridLinesVisible(true);
	}

	public void bind(CashDrawer model)
	{
		view.bind(model);
	}

	public BorderPane getRootPane()
	{
		return rootPane;
	}

	public Button getEditButton()
	{
		return editButton;
	}

	public Button getRemoveButton()
	{
		return removeButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public CashDrawerView getView()
	{
		return view;
	}

	public Button getConfirmSelectionButton()
	{
		return confirmSelectionButton;
	}

	public Button getOpenCashDrawerButton(){
		return openCashDrawerButton ;
	}

	public Button getCloseCashDrawerButton(){
		return closeCashDrawerButton ;
	}


}
