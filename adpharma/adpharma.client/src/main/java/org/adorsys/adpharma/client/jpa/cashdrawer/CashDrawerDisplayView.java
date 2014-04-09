package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCreatingUser;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;
public class CashDrawerDisplayView
{

	@FXML
	private BorderPane rootPane;

	@FXML
	private Button cashOutButton;

	@FXML
	private Button removeButton;
	
	@FXML
	private Button cashButon;

	private HBox buttonBarLeft;

	private Button confirmSelectionButton;

	@FXML
	private TableView<CustomerInvoice> invoicesDataList ;

	@FXML
	private TableView<CustomerInvoiceItem> invoiceItemDataList ;

	@FXML
	private GridPane invoiceHeadGrid ;

	private TextField invoiceNumber;

	private BigDecimalField customerRestTopay;

	private BigDecimalField insurranceRestTopay;

	private BigDecimalField amountDiscount;


	private ComboBox<CustomerInvoiceCreatingUser> creatingUser;

	private Button cancelButton;

	@FXML
	private HBox invoiceSearchBox;

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
	@Bundle({ CrudKeys.class, CashDrawer.class ,CustomerInvoice.class , CustomerInvoiceItem.class })
	private ResourceBundle resourceBundle;

	@Inject
	private FXMLLoader fxmlLoader;

	@Inject
	private Locale locale;

	@Inject
	private InvoiceTypeConverter invoiceTypeConverter;

	@FXML
	private GridPane paymentGrid ;

	private ComboBox<PaymentMode> paymentMode;

	private BigDecimalField amount;

	private BigDecimalField receivedAmount;

	private BigDecimalField difference;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		buildInvoiceDataList(viewBuilder);
		buildInvoiceItemDataList(viewBuilder);

		//		      viewBuilder.addMainForm(view, ViewType.DISPLAY, true);
		//		      viewBuilder.addSeparator();
		//		      List<HBox> doubleButtonBar = viewBuilder.addDoubleButtonBar();
		//		      buttonBarLeft = doubleButtonBar.get(0);
		//		      confirmSelectionButton = viewBuilder.addButton(buttonBarLeft, "Entity_select.title", "confirmSelectionButton", resourceBundle);
		//		      HBox buttonBarRight = doubleButtonBar.get(1);
		//		      editButton = viewBuilder.addButton(buttonBarRight, "Entity_edit.title", "editButton", resourceBundle, AwesomeIcon.EDIT);
		//		      removeButton = viewBuilder.addButton(buttonBarRight, "Entity_remove.title", "removeButton", resourceBundle, AwesomeIcon.TRASH_ALT);
		//		      searchButton = viewBuilder.addButton(buttonBarRight, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//		      rootPane = viewBuilder.toBorderPane();
		buildinvoiceHeadGrid();
		buildinvoiceSearchGrid();
		BuildPaymentGrid();
	}
	public void buildInvoiceItemDataList(ViewBuilder viewBuilder){
		viewBuilder.addStringColumn(invoiceItemDataList, "internalPic", "CustomerInvoiceItem_internalPic_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(invoiceItemDataList, "article", "CustomerInvoiceItem_article_description.title", resourceBundle,300d);
		viewBuilder.addBigDecimalColumn(invoiceItemDataList, "purchasedQty", "CustomerInvoiceItem_purchasedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(invoiceItemDataList, "salesPricePU", "CustomerInvoiceItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(invoiceItemDataList, "totalSalesPrice", "CustomerInvoiceItem_totalSalesPrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
	}
	public void buildInvoiceDataList(ViewBuilder viewBuilder){
		viewBuilder.addEnumColumn(invoicesDataList, "invoiceType", "CustomerInvoice_invoiceType_description.title", resourceBundle, invoiceTypeConverter);
		viewBuilder.addStringColumn(invoicesDataList, "invoiceNumber", "CustomerInvoice_invoiceNumber_description.title", resourceBundle);
		viewBuilder.addDateColumn(invoicesDataList, "creationDate", "CustomerInvoice_creationDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addStringColumn(invoicesDataList, "creatingUser", "CustomerInvoice_creatingUser_description.title", resourceBundle);
		viewBuilder.addStringColumn(invoicesDataList, "cashed", "CustomerInvoice_cashed_description.title", resourceBundle);
		// Field not displayed in table
		viewBuilder.addBigDecimalColumn(invoicesDataList, "amountBeforeTax", "CustomerInvoice_amountBeforeTax_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(invoicesDataList, "taxAmount", "CustomerInvoice_taxAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(invoicesDataList, "amountDiscount", "CustomerInvoice_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(invoicesDataList, "amountAfterTax", "CustomerInvoice_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(invoicesDataList, "netToPay", "CustomerInvoice_netToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(invoicesDataList, "customerRestTopay", "CustomerInvoice_customerRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(invoicesDataList, "insurranceRestTopay", "CustomerInvoice_insurranceRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		// Field not displayed in table
		viewBuilder.addBigDecimalColumn(invoicesDataList, "advancePayment", "CustomerInvoice_advancePayment_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(invoicesDataList, "totalRestToPay", "CustomerInvoice_totalRestToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
	}

	public void BuildPaymentGrid(){
		paymentMode = ViewBuilderUtils.newComboBox(null, "paymentMode", resourceBundle, PaymentMode.values(), false);
		paymentMode.setPrefHeight(50d);
		paymentMode.setPrefWidth(230d);

		amount = ViewBuilderUtils.newBigDecimalField("amount", NumberType.CURRENCY, locale, false);
		amount.setPrefHeight(50d);
		amount.setPrefWidth(200d);
		amount.setEditable(false);

		receivedAmount = ViewBuilderUtils.newBigDecimalField("receivedAmount", NumberType.CURRENCY, locale, false);
		receivedAmount.setPrefHeight(50d);
		receivedAmount.setPrefWidth(200d);

		difference = ViewBuilderUtils.newBigDecimalField("difference", NumberType.CURRENCY, locale, false);
		difference.setPrefHeight(50d);
		difference.setPrefWidth(200d);
		difference.setEditable(false);

		paymentGrid.addColumn(1, amount,paymentMode,receivedAmount,difference);
	}
	public void buildinvoiceSearchGrid(){
		invoiceNumberToSearch = ViewBuilderUtils.newTextField( "invoiceNumberToSearch", false);
		invoiceNumberToSearch.setPrefHeight(50d);
		invoiceNumberToSearch.setPromptText("Invoice ID");

		openCashDrawer = ViewBuilderUtils.newComboBox(null,"openCashDrawer", false);
		openCashDrawer.setPrefWidth(200d);
		openCashDrawer.setPrefHeight(50d);
		openCashDrawer.setPromptText("Cash Drawer");

		searchButton = ViewBuilderUtils.newButton("Entity_search.text", "ok", resourceBundle, AwesomeIcon.SEARCH_PLUS);
		searchButton.setPrefHeight(50d);

		invoiceSearchBox.getChildren().addAll(invoiceNumberToSearch ,openCashDrawer,searchButton);
	}

	public void buildinvoiceHeadGrid(){
		invoiceNumber = ViewBuilderUtils.newTextField( "invoiceNumber", true);

		creatingUser = ViewBuilderUtils.newComboBox(null,"creatingUser", false);
		creatingUser.setPrefWidth(130d);

		amountDiscount = ViewBuilderUtils.newBigDecimalField("amountDiscount", NumberType.CURRENCY, locale,false);

		customerRestTopay = ViewBuilderUtils.newBigDecimalField( "customerRestTopay", NumberType.CURRENCY, locale,false);

		insurranceRestTopay = ViewBuilderUtils.newBigDecimalField( "insurranceRestTopay", NumberType.CURRENCY, locale,false);


		cancelButton = ViewBuilderUtils.newButton("Entity_cancel.text", "ok", resourceBundle, AwesomeIcon.ASTERISK);

		invoiceHeadGrid.addRow(0,new Label("invoiceNumber"),new Label("Saller"),new Label("discount"),new Label("Customer part"),new Label("Insurrance part"));
		invoiceHeadGrid.addRow(1,invoiceNumber,creatingUser,amountDiscount,customerRestTopay,insurranceRestTopay,cancelButton);
		invoiceHeadGrid.setGridLinesVisible(true);
	}

	public void bind(CashDrawer model)
	{
		view.bind(model);
	}

	public void bindInvoice(CustomerInvoice model){
		invoiceNumber.textProperty().bindBidirectional(model.invoiceNumberProperty());
		creatingUser.valueProperty().bindBidirectional(model.creatingUserProperty());
		amountDiscount.numberProperty().bindBidirectional(model.amountDiscountProperty());
		customerRestTopay.numberProperty().bindBidirectional(model.customerRestTopayProperty());
		insurranceRestTopay.numberProperty().bindBidirectional(model.insurranceRestTopayProperty());
		invoiceItemDataList.itemsProperty().bindBidirectional(model.invoiceItemsProperty());
	}

	public void bindPayment(Payment model) {
		paymentMode.valueProperty().bindBidirectional(model.paymentModeProperty());
		amount.numberProperty().bindBidirectional(model.amountProperty());
		receivedAmount.numberProperty().bindBidirectional(model.receivedAmountProperty());
		difference.numberProperty().bindBidirectional(model.differenceProperty());
	}


	public BorderPane getRootPane()
	{
		return rootPane;
	}

	public Button getCashOutButton()
	{
		return cashOutButton;
	}

	public Button getRemoveButton()
	{
		return removeButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}
	public Button getCashButon()
	{
		return cashButon;
	}
	
	public CashDrawerView getView()
	{
		return view;
	}


	public Button getOpenCashDrawerButton(){
		return openCashDrawerButton ;
	}

	public Button getCloseCashDrawerButton(){
		return closeCashDrawerButton ;
	}

	public TableView<CustomerInvoice> getInvoicesDataList() {
		return invoicesDataList;
	}

	public TableView<CustomerInvoiceItem> getInvoiceItemDataList() {
		return invoiceItemDataList;
	}

	public BigDecimalField getAmount(){
		return amount ;
	}

	public BigDecimalField getReceivedAmount() {
		return receivedAmount;
	}

	public BigDecimalField getDifference() {
		return difference;
	}
}
