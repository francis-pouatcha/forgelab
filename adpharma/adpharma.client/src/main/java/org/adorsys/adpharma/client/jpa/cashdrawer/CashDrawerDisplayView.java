package org.adorsys.adpharma.client.jpa.cashdrawer;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItem;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeListCellFatory;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderRestToPay;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSalesAgent;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.salesordertype.SalesOrderTypeConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
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

	@FXML
	private MenuItem removePaymentMenuItem;

	private HBox buttonBarLeft;

	private Button confirmSelectionButton;

	@FXML
	private TableView<SalesOrder> salesOrderDataList ;

	@FXML
	private TableView<SalesOrderItem> invoiceItemDataList ;

	@FXML
	private TableView<PaymentItem> paymentItemDataList ;

	//	@FXML
	//	private GridPane invoiceHeadGrid ;

	private TextField invoiceNumber;

	private BigDecimalField customerRestTopay;

	private BigDecimalField insurranceRestTopay;

	private BigDecimalField amountDiscount;


	private ComboBox<SalesOrderSalesAgent> creatingUser;

	private Button cancelButton;

	@FXML
	private HBox invoiceSearchBox;

	private TextField invoiceNumberToSearch ;

	private ComboBox<CashDrawer> openCashDrawer; 


	private Button searchButton;

	private Button searchPayementButton;

	@Inject
	private CashDrawerView view;

	@FXML
	private Button openCashDrawerButton;

	private Button closeCashDrawerButton;

	@Inject
	@Bundle({ CrudKeys.class, CashDrawer.class ,CustomerInvoice.class , CustomerInvoiceItem.class, SalesOrder.class })
	private ResourceBundle resourceBundle;

	@Inject
	private FXMLLoader fxmlLoader;

	@Inject
	private Locale locale;

	@Inject
	private SalesOrderTypeConverter salesOrderTypeConverter;

	@FXML
	private GridPane paymentGrid ;

	private ComboBox<PaymentMode> paymentMode;

	private BigDecimalField amount;

	private BigDecimalField receivedAmount;
	private BigDecimalField difference;

	private TextField docNumber ;

	@Inject
	@Bundle(PaymentMode.class)
	private ResourceBundle paymentModeBundle;
	@Inject
	private PaymentModeConverter paymentModeConverter;

	@Inject
	private PaymentModeListCellFatory paymentModeListCellFatory;

	@Inject
	@Bundle({ Payment.class, PaymentItem.class, CrudKeys.class, Customer.class })
	private ResourceBundle paymentResourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		buildSalesOrderDataList(viewBuilder);
		buildInvoiceItemDataList(viewBuilder);
		buildPaymentItemDataList(viewBuilder);

		buildinvoiceHeadGrid();
		buildinvoiceSearchGrid();
		BuildPaymentGrid();

	}
	public void buildInvoiceItemDataList(ViewBuilder viewBuilder){
		//		viewBuilder.addStringColumn(invoiceItemDataList, "internalPic", "CustomerInvoiceItem_internalPic_description.title", resourceBundle,100d);
		ViewBuilderUtils.newStringColumn(invoiceItemDataList, "article", "CustomerInvoiceItem_article_description.title", resourceBundle ,250d);
		viewBuilder.addBigDecimalColumn(invoiceItemDataList, "orderedQty", "CustomerInvoiceItem_purchasedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(invoiceItemDataList, "salesPricePU", "CustomerInvoiceItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(invoiceItemDataList, "totalSalePrice", "CustomerInvoiceItem_totalSalesPrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
	}
	public void buildPaymentItemDataList(ViewBuilder viewBuilder){
		viewBuilder.addEnumColumn(paymentItemDataList, "paymentMode", "PaymentItem_paymentMode_description.title", paymentResourceBundle, paymentModeConverter);
		viewBuilder.addBigDecimalColumn(paymentItemDataList, "amount", "PaymentItem_amount_description.title", paymentResourceBundle, NumberType.CURRENCY, locale);
		//	    viewBuilder.addBigDecimalColumn(paymentItemDataList, "receivedAmount", "PaymentItem_receivedAmount_description.title", paymentResourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addStringColumn(paymentItemDataList, "documentNumber", "PaymentItem_documentNumber_description.title", paymentResourceBundle);
		viewBuilder.addStringColumn(paymentItemDataList, "paidBy", "PaymentItem_paidBy_description.title", paymentResourceBundle,300d);
	}

	public void buildSalesOrderDataList(ViewBuilder viewBuilder){
		//		viewBuilder.addEnumColumn(salesOrderDataList, "salesOrderType", "CustomerInvoice_invoiceType_description.title", resourceBundle, salesOrderTypeConverter);
		viewBuilder.addStringColumn(salesOrderDataList, "soNumber", "CustomerInvoice_invoiceNumber_description.title", resourceBundle);
		//		viewBuilder.addDateColumn(salesOrderDataList, "creationDate", "CustomerInvoice_creationDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addStringColumn(salesOrderDataList, "salesAgent", "CustomerInvoice_creatingUser_description.title", resourceBundle,100d);
		viewBuilder.addStringColumn(salesOrderDataList, "customer", "CustomerInvoice_customer_description.title", resourceBundle,150d);
		// Field not displayed in table
		//		viewBuilder.addBigDecimalColumn(salesOrderDataList, "amountBeforeTax", "CustomerInvoice_amountBeforeTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(salesOrderDataList, "amountVAT", "CustomerInvoice_taxAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(salesOrderDataList, "amountDiscount", "CustomerInvoice_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addStringColumn(salesOrderDataList, "insurance", "CustomerInvoice_insurance_description.title", resourceBundle,250d);
		viewBuilder.addBigDecimalColumn(salesOrderDataList, "amountDiscount", "CustomerInvoice_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(salesOrderDataList, "customerRestTopay", "CustomerInvoice_customerRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(salesOrderDataList, "insurranceRestTopay", "CustomerInvoice_insurranceRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
		// Field not displayed in table
		viewBuilder.addBigDecimalColumn(salesOrderDataList, "amountAfterTax", "CustomerInvoice_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(salesOrderDataList, "netToPay","CustomerInvoice_netToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
	}

	public void BuildPaymentGrid(){
		//		paymentMode = ViewBuilderUtils.newComboBox(null, "paymentMode", resourceBundle, PaymentMode.values(), false);
		Font font = new Font("latin", 20D);
		paymentMode = ViewBuilderUtils.newComboBox("Payment_paymentMode_description.title", "paymentMode", paymentResourceBundle, PaymentMode.values(), false);
		paymentMode.setPrefHeight(30d);
		paymentMode.setPrefWidth(230d);
		ComboBoxInitializer.initialize(paymentMode, paymentModeConverter, paymentModeListCellFatory, paymentModeBundle);
		paymentMode.setValue(PaymentMode.CASH);

		amount = ViewBuilderUtils.newBigDecimalField("amount", NumberType.CURRENCY, locale, false);
		amount.setPrefHeight(30d);
		amount.setPrefWidth(200d);
		amount.setFont(font);
		amount.setEditable(false);
		amount.getStyleClass().add("green-text");

		receivedAmount = ViewBuilderUtils.newBigDecimalField("receivedAmount", NumberType.INTEGER, locale, false);
		receivedAmount.setPrefHeight(30d);
		receivedAmount.setFont(font);
		receivedAmount.setPrefWidth(200d);
		receivedAmount.getStyleClass().add("blue-text");

		difference = ViewBuilderUtils.newBigDecimalField("difference", NumberType.INTEGER, locale, false);
		difference.setPrefHeight(30d);
		difference.setPrefWidth(200d);
		difference.setEditable(false);
		difference.setFont(font);
		difference.getStyleClass().add("red-text");


		docNumber = ViewBuilderUtils.newTextField("docNumber", false);
		docNumber.setPromptText("Doc Number");
		docNumber.setPrefHeight(30d);
		docNumber.setPrefWidth(200d);
		docNumber.setFont(font);

		paymentGrid.addColumn(1, amount,paymentMode,receivedAmount,difference, docNumber);
	}
	public void buildinvoiceSearchGrid(){
		invoiceNumberToSearch = ViewBuilderUtils.newTextField( "invoiceNumberToSearch", false);
		invoiceNumberToSearch.setPrefHeight(25d);
		invoiceNumberToSearch.setPromptText("Invoice ID");

		openCashDrawer = ViewBuilderUtils.newComboBox(null,"openCashDrawer", false);
		openCashDrawer.setPrefWidth(200d);
		openCashDrawer.setPrefHeight(25d);
		openCashDrawer.setPromptText("Cash Drawer");

		searchButton = ViewBuilderUtils.newButton("Entity_search.text", "ok", resourceBundle, AwesomeIcon.SEARCH_PLUS);
		searchButton.setPrefHeight(25d);
		searchButton.setPrefWidth(150d);

		searchPayementButton = ViewBuilderUtils.newButton("Entity_search.text", "ok", resourceBundle, AwesomeIcon.SEARCH_PLUS);
		searchPayementButton.setPrefHeight(25d);
		searchPayementButton.setPrefWidth(180d);
		searchPayementButton.setText("Liste des Payements");

		closeCashDrawerButton = ViewBuilderUtils.newButton("Entity_search.text", "ok", resourceBundle, AwesomeIcon.EJECT);
		closeCashDrawerButton.setPrefHeight(25d);
		closeCashDrawerButton.setPrefWidth(100d);
		closeCashDrawerButton.setText("Fermer");


		cancelButton = ViewBuilderUtils.newButton("Entity_cancel.text", "ok", resourceBundle, AwesomeIcon.ASTERISK);
		cancelButton.setPrefWidth(100d);
		HBox hBox = new HBox();
		hBox.setSpacing(5d);
		hBox.getChildren().addAll(cancelButton,closeCashDrawerButton,searchPayementButton);
		invoiceSearchBox.setMargin(hBox, new Insets(0, 0, 0, 100));

		invoiceSearchBox.getChildren().addAll(invoiceNumberToSearch ,openCashDrawer,searchButton,hBox);
	}

	public void buildinvoiceHeadGrid(){
		//		invoiceNumber = ViewBuilderUtils.newTextField( "invoiceNumber", false);
		//		invoiceNumber.setEditable(false);
		//
		//		//		creatingUser = ViewBuilderUtils.newComboBox(null,"creatingUser", false);
		//		//		creatingUser.setPrefWidth(200d);
		//
		//		amountDiscount = ViewBuilderUtils.newBigDecimalField("amountDiscount", NumberType.CURRENCY, locale,false);
		//		amountDiscount.setEditable(false);
		//
		//		customerRestTopay = ViewBuilderUtils.newBigDecimalField( "customerRestTopay", NumberType.CURRENCY, locale,false);
		//		customerRestTopay.setEditable(false);
		//
		//		insurranceRestTopay = ViewBuilderUtils.newBigDecimalField( "insurranceRestTopay", NumberType.CURRENCY, locale,false);
		//		insurranceRestTopay.setEditable(false);
		//
		//		cancelButton = ViewBuilderUtils.newButton("Entity_cancel.text", "ok", resourceBundle, AwesomeIcon.ASTERISK);
		//
		//		invoiceHeadGrid.addRow(0,new Label("invoiceNumber"),new Label("discount"),new Label("Customer part"),new Label("Insurrance part"));
		//		invoiceHeadGrid.addRow(1,invoiceNumber,amountDiscount,customerRestTopay,insurranceRestTopay,cancelButton);
		//		invoiceHeadGrid.setGridLinesVisible(true);
	}

	public void bind(CashDrawer model)
	{
		view.bind(model);
	}

	public void bindInvoice(final SalesOrder model){
		//		invoiceNumber.textProperty().bindBidirectional(model.soNumberProperty());
		//		//		creatingUser.valueProperty().bindBidirectional(model.salesAgentProperty());
		//		amountDiscount.numberProperty().bindBidirectional(model.amountDiscountProperty());
		//		model.amountAfterTaxProperty().addListener(new ChangeListener<BigDecimal>() {
		//			@Override
		//			public void changed(ObservableValue<? extends BigDecimal> source,
		//					BigDecimal oldValue, BigDecimal newValue) {
		//				SalesOrderRestToPay restToPay = new SalesOrderRestToPay(model);
		//				customerRestTopay.setNumber(model.getCustomerRestTopay());
		//				insurranceRestTopay.setNumber(model.getInsurranceRestTopay());
		//			}
		//		});
		invoiceItemDataList.itemsProperty().bindBidirectional(model.salesOrderItemsProperty());
	}

	public void bindPayment(Payment model) {
		paymentItemDataList.itemsProperty().bindBidirectional(model.paymentItemsProperty());
		//		paymentMode.valueProperty().bindBidirectional(model.paymentModeProperty());
		//		amount.numberProperty().bindBidirectional(model.amountProperty());
		//		receivedAmount.numberProperty().bindBidirectional(model.receivedAmountProperty());
		//		difference.numberProperty().bindBidirectional(model.differenceProperty());
		//		docNumber.textProperty().bindBidirectional(model.paymentNumberProperty());
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
	public Button getCancelButton()
	{
		return cancelButton;
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

	public Button getSearchPayementButton(){
		return searchPayementButton ;
	}

	public TableView<SalesOrder> getInvoicesDataList() {
		return salesOrderDataList;
	}

	public TableView<SalesOrderItem> getInvoiceItemDataList() {
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
	public ComboBox<PaymentMode> getPaymentMode() {
		return paymentMode;
	}
	public TextField getDocNumber() {
		return docNumber;
	}
	public MenuItem getRemovePaymentMenuItem() {
		return removePaymentMenuItem;
	}
	public void setDocNumber(TextField docNumber) {
		this.docNumber = docNumber;
	}
	public TableView<PaymentItem> getPaymentItemDataList() {
		return paymentItemDataList;
	}
}
