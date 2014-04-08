package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

@Singleton
public class SalesOrderDisplayView
{

	@FXML
	private BorderPane rootPane;

	@FXML
	private Button closeButton;

	@FXML
	private Button clientButton;

	@FXML
	private Button insurreurButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Button ordonnancierButton;

	private HBox buttonBarLeft;

	@FXML
	private GridPane saleOrderItemBar;

	//	sale order items bar components
	private TextField articleName;

	private TextField internalPic;

	private BigDecimalField orderedQty;

	private BigDecimalField totalSalePrice;

	private BigDecimalField salesPricePU;

	private Button okButton;

	//	private Button confirmSelectionButton;

	//	left grid pane components
	@FXML
	private ComboBox<SalesOrderCustomer> client;

	@FXML
	private ComboBox<SalesOrderCashDrawer> cashDrawer;

	@FXML
	private TextField clientPhone;

	@FXML
	private TextField clientAdresse;

	@FXML
	private TextField clientcategorie;

	//	Rigth grid pane components
	@FXML
	private ComboBox<SalesOrderInsurance> insurrer;

	@FXML
	private TextField numBon;

	@FXML
	private TextField numcmd;

	@FXML
	private GridPane rigthGrid ;

	//	amounts grid pane components

	@FXML
	private GridPane amountPane;

	private BigDecimalField amountHT;

	private BigDecimalField taxAmount;

	private BigDecimalField discount;

	private BigDecimalField amountTTC;

	private BigDecimalField discountRate;

	@FXML
	private ComboBox<SalesOrderVat> tax;



	@Inject
	private SalesOrderView view;

	@Inject
	@Bundle({ CrudKeys.class, SalesOrder.class ,SalesOrderItem.class,Article.class})
	private ResourceBundle resourceBundle;

	@Inject
	private FXMLLoader fxmlLoader;

	@FXML
	private Pagination pagination;

	@FXML
	private TableView<SalesOrderItem> dataList;

	@Inject
	private Locale locale;

	@FXML
	private ContextMenu datalistContextMenu;

	@FXML
	private MenuItem deleteSOIMenu;

	@FXML
	private MenuItem editSOIMenu;

	@Inject
	private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;



	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
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

		//	   defined datalist columns
		//		dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "internalPic", "SalesOrderItem_internalPic_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(dataList, "article", "SalesOrderItem_article_description.title", resourceBundle,400d);
		viewBuilder.addBigDecimalColumn(dataList, "orderedQty", "SalesOrderItem_orderedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "SalesOrderItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalSalePrice", "SalesOrderItem_totalSalePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);

		buildOrderItemBar();
		buildAmountPane();
		buildInsurranceGrid();
	}

	public void addValidators()
	{
		// no active validator
		// no active validator
		// no active validator
	}

	public Set<ConstraintViolation<SalesOrder>> validate(SalesOrder model)
	{
		Set<ConstraintViolation<SalesOrder>> violations = new HashSet<ConstraintViolation<SalesOrder>>();
		violations.addAll(toOneAggreggationFieldValidator.validate(client, model.getCustomer(), SalesOrder.class, "customer", resourceBundle));
//		violations.addAll(toOneAggreggationFieldValidator.validate(cashDrawer, model.getCashDrawer(), SalesOrder.class, "cashDrawer", resourceBundle));

		return violations;
	}
	public void buildAmountPane(){
		amountHT = ViewBuilderUtils.newBigDecimalField( "amountHT", NumberType.CURRENCY,locale,false);
		amountHT.setEditable(false);

		discount = ViewBuilderUtils.newBigDecimalField( "discount", NumberType.CURRENCY,locale,false);
		discount.setEditable(false);

		amountTTC = ViewBuilderUtils.newBigDecimalField( "amountTTC", NumberType.CURRENCY,locale,false);
		amountTTC.setEditable(false);

		taxAmount = ViewBuilderUtils.newBigDecimalField( "taxAmount", NumberType.CURRENCY,locale,false);
		taxAmount.setEditable(false);

		amountPane.add(amountHT, 1, 0);
		amountPane.add(taxAmount, 1, 1);
		amountPane.add(discount, 1, 2);
		amountPane.add(amountTTC, 1, 3);

	}

	public void buildInsurranceGrid(){
		discountRate = ViewBuilderUtils.newBigDecimalField( "discountRate", NumberType.PERCENTAGE,locale,false);
		discountRate.setEditable(true);
		rigthGrid.add(discountRate, 1, 3);

	}
	public void buildOrderItemBar(){
		internalPic = ViewBuilderUtils.newTextField( "internalPic", false);
		internalPic.setPromptText("cip");
		internalPic.setTooltip(new Tooltip("cip"));

		articleName = ViewBuilderUtils.newTextField("articleName", false);
		articleName.setPromptText("Designation");
		articleName.setPrefWidth(400d);
		articleName.setTooltip(new Tooltip("Designation article"));

		orderedQty = ViewBuilderUtils.newBigDecimalField( "orderedQty", NumberType.INTEGER,locale,false);
		orderedQty.setTooltip(new Tooltip("Quantite"));
		orderedQty.setPrefWidth(75d);

		salesPricePU = ViewBuilderUtils.newBigDecimalField("salesPricePU", NumberType.CURRENCY, locale,false);
		salesPricePU.setTooltip(new Tooltip("Prix de vente unitaire"));
		salesPricePU.setPrefWidth(130d);
		salesPricePU.setEditable(false);

		totalSalePrice = ViewBuilderUtils.newBigDecimalField( "totalSalePrice", NumberType.CURRENCY, locale,false);
		totalSalePrice.setTooltip(new Tooltip("Total prix de vente "));
		totalSalePrice.setPrefWidth(130d);
		totalSalePrice.setEditable(false);

		okButton = ViewBuilderUtils.newButton("Entity_ok.text", "ok", resourceBundle, AwesomeIcon.ARROW_DOWN);
		saleOrderItemBar.addRow(0,new Label("CipM"),new Label("Designation"),new Label("Qte"),new Label("Prix U"),new Label("PrixTT"));
		saleOrderItemBar.addRow(1,internalPic,articleName,orderedQty,salesPricePU,totalSalePrice,okButton);
		//		saleOrderItemBar.getChildren().addAll(
		//				internalPic,articleName,orderedQty,salesPricePU,totalSalePrice,okButton);

	}

	public void bind(SalesOrder model)
	{
		amountHT.numberProperty().bindBidirectional(model.amountBeforeTaxProperty());
		taxAmount.numberProperty().bindBidirectional(model.amountVATProperty());
		discount.numberProperty().bindBidirectional(model.amountDiscountProperty());
		amountTTC.numberProperty().bindBidirectional(model.amountAfterTaxProperty());
		tax.valueProperty().bindBidirectional(model.vatProperty());
		insurrer.valueProperty().bindBidirectional(model.insuranceProperty());
		client.valueProperty().bindBidirectional(model.customerProperty());
		numcmd.textProperty().bindBidirectional(model.soNumberProperty());
		clientPhone.textProperty().bindBidirectional(model.getCustomer().mobileProperty());
		clientAdresse.textProperty().bindBidirectional(model.getCustomer().faxProperty());
		insurrer.valueProperty().bindBidirectional(model.insuranceProperty());
		clientcategorie.textProperty().bindBidirectional(model.getCustomer().getCustomerCategory().nameProperty());
		dataList.itemsProperty().bindBidirectional(model.salesOrderItemsProperty());
		cashDrawer.valueProperty().bindBidirectional(model.cashDrawerProperty());


		//		view.bind(model);
	}

	public void bind(SalesOrderItem soi){
		internalPic.textProperty().bindBidirectional(soi.internalPicProperty());
		articleName.textProperty().bindBidirectional(soi.getArticle().articleNameProperty());
		orderedQty.numberProperty().bindBidirectional(soi.orderedQtyProperty());
		salesPricePU.numberProperty().bindBidirectional(soi.salesPricePUProperty());
		totalSalePrice.numberProperty().bindBidirectional(soi.totalSalePriceProperty());
	}

	public BorderPane getRootPane()
	{
		return rootPane;
	}

	public Button getCloseButton()
	{
		return closeButton;
	}

	public Button getCancelButton()
	{
		return cancelButton;
	}

	public Button getOrdonnancierButton()
	{
		return ordonnancierButton;
	}

	public SalesOrderView getView()
	{
		return view;
	}

	//	public Button getConfirmSelectionButton()
	//	{
	//		return confirmSelectionButton;
	//	}

	public ComboBox<SalesOrderVat> getTax() {
		return tax;
	}

	public TableView<SalesOrderItem> getDataList() {
		return dataList;
	}

	public Button getClientButton() {
		return clientButton;
	}


	public Button getInsurreurButton() {
		return insurreurButton;
	}


	public HBox getButtonBarLeft() {
		return buttonBarLeft;
	}


	public GridPane getSaleOrderItemBar() {
		return saleOrderItemBar;
	}


	public TextField getArticleName() {
		return articleName;
	}


	public TextField getInternalPic() {
		return internalPic;
	}


	public BigDecimalField getOrderedQty() {
		return orderedQty;
	}


	public BigDecimalField getTotalSalePrice() {
		return totalSalePrice;
	}


	public BigDecimalField getSalesPricePU() {
		return salesPricePU;
	}


	public Button getOkButton() {
		return okButton;
	}


	public ComboBox<SalesOrderCustomer> getClient() {
		return client;
	}


	public ComboBox<SalesOrderCashDrawer> getCashDrawer() {
		return cashDrawer;
	}


	public TextField getClientPhone() {
		return clientPhone;
	}


	public TextField getClientAdresse() {
		return clientAdresse;
	}


	public TextField getClientcategorie() {
		return clientcategorie;
	}


	public ComboBox<SalesOrderInsurance> getInsurrer() {
		return insurrer;
	}


	public BigDecimalField getDiscountRate() {
		return discountRate;
	}



	public TextField getNumBon() {
		return numBon;
	}


	public TextField getNumcmd() {
		return numcmd;
	}


	public GridPane getAmountPane() {
		return amountPane;
	}


	public BigDecimalField getAmountHT() {
		return amountHT;
	}


	public BigDecimalField getTaxAmount() {
		return taxAmount;
	}


	public BigDecimalField getDiscount() {
		return discount;
	}


	public BigDecimalField getAmountTTC() {
		return amountTTC;
	}


	public ContextMenu getDatalistContextMenu() {
		return datalistContextMenu;
	}

	public MenuItem getDeleteSOIMenu() {
		return deleteSOIMenu;
	}

	public MenuItem getEditSOIMenu() {
		return editSOIMenu;
	}




}
