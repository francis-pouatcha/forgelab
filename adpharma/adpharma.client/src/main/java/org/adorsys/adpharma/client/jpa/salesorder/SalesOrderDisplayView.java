package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
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
	private Button removeButton;

	@FXML
	private Button ordonnancierButton;

	private HBox buttonBarLeft;

	@FXML
	private HBox saleOrderItemBar;

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

	private BigDecimalField coverageRate;

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
		dataList = viewBuilder.addTable("dataList");
		viewBuilder.addBigDecimalColumn(dataList, "orderedQty", "SalesOrderItem_orderedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "returnedQty", "SalesOrderItem_returnedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "deliveredQty", "SalesOrderItem_deliveredQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "SalesOrderItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalSalePrice", "SalesOrderItem_totalSalePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addStringColumn(dataList, "internalPic", "SalesOrderItem_internalPic_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle);
		
		buildOrderItemBar();
		buildAmountPane();
		buildInsurranceGrid();
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
		
		discountRate = ViewBuilderUtils.newBigDecimalField( "discountRate", NumberType.PERCENTAGE,locale,false);

		amountPane.add(amountHT, 1, 0);
		amountPane.add(taxAmount, 1, 1);
		amountPane.add(discount, 1, 2);
		amountPane.add(amountTTC, 1, 3);
		amountPane.add(discountRate, 1, 5);

	}
	
	public void buildInsurranceGrid(){
		coverageRate = ViewBuilderUtils.newBigDecimalField( "discountRate", NumberType.PERCENTAGE,locale,false);
		coverageRate.setEditable(false);
		rigthGrid.add(coverageRate, 1, 1);

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

		saleOrderItemBar.getChildren().addAll(
				internalPic,articleName,orderedQty,salesPricePU,totalSalePrice,okButton);

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
		coverageRate.numberProperty().bindBidirectional(model.getInsurance().coverageRateProperty());
		clientcategorie.textProperty().bindBidirectional(model.getCustomer().getCustomerCategory().nameProperty());
		
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

	public Button getRemoveButton()
	{
		return removeButton;
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

	public void setClientButton(Button clientButton) {
		this.clientButton = clientButton;
	}

	public Button getInsurreurButton() {
		return insurreurButton;
	}

	public void setInsurreurButton(Button insurreurButton) {
		this.insurreurButton = insurreurButton;
	}

	public HBox getButtonBarLeft() {
		return buttonBarLeft;
	}

	public void setButtonBarLeft(HBox buttonBarLeft) {
		this.buttonBarLeft = buttonBarLeft;
	}

	public HBox getSaleOrderItemBar() {
		return saleOrderItemBar;
	}

	public void setSaleOrderItemBar(HBox saleOrderItemBar) {
		this.saleOrderItemBar = saleOrderItemBar;
	}

	public TextField getArticleName() {
		return articleName;
	}

	public void setArticleName(TextField articleName) {
		this.articleName = articleName;
	}

	public TextField getInternalPic() {
		return internalPic;
	}

	public void setInternalPic(TextField internalPic) {
		this.internalPic = internalPic;
	}

	public BigDecimalField getOrderedQty() {
		return orderedQty;
	}

	public void setOrderedQty(BigDecimalField orderedQty) {
		this.orderedQty = orderedQty;
	}

	public BigDecimalField getTotalSalePrice() {
		return totalSalePrice;
	}

	public void setTotalSalePrice(BigDecimalField totalSalePrice) {
		this.totalSalePrice = totalSalePrice;
	}

	public BigDecimalField getSalesPricePU() {
		return salesPricePU;
	}

	public void setSalesPricePU(BigDecimalField salesPricePU) {
		this.salesPricePU = salesPricePU;
	}

	public Button getOkButton() {
		return okButton;
	}

	public void setOkButton(Button okButton) {
		this.okButton = okButton;
	}

	public ComboBox<SalesOrderCustomer> getClient() {
		return client;
	}

	public void setClient(ComboBox<SalesOrderCustomer> client) {
		this.client = client;
	}

	public ComboBox<SalesOrderCashDrawer> getCashDrawer() {
		return cashDrawer;
	}

	public void setCashDrawer(ComboBox<SalesOrderCashDrawer> cashDrawer) {
		this.cashDrawer = cashDrawer;
	}

	public TextField getClientPhone() {
		return clientPhone;
	}

	public void setClientPhone(TextField clientPhone) {
		this.clientPhone = clientPhone;
	}

	public TextField getClientAdresse() {
		return clientAdresse;
	}

	public void setClientAdresse(TextField clientAdresse) {
		this.clientAdresse = clientAdresse;
	}

	public TextField getClientcategorie() {
		return clientcategorie;
	}

	public void setClientcategorie(TextField clientcategorie) {
		this.clientcategorie = clientcategorie;
	}

	public ComboBox<SalesOrderInsurance> getInsurrer() {
		return insurrer;
	}

	public void setInsurrer(ComboBox<SalesOrderInsurance> insurrer) {
		this.insurrer = insurrer;
	}

	public BigDecimalField getCoverageRate() {
		return coverageRate;
	}



	public TextField getNumBon() {
		return numBon;
	}

	public void setNumBon(TextField numBon) {
		this.numBon = numBon;
	}

	public TextField getNumcmd() {
		return numcmd;
	}

	public void setNumcmd(TextField numcmd) {
		this.numcmd = numcmd;
	}

	public GridPane getAmountPane() {
		return amountPane;
	}

	public void setAmountPane(GridPane amountPane) {
		this.amountPane = amountPane;
	}

	public BigDecimalField getAmountHT() {
		return amountHT;
	}

	public void setAmountHT(BigDecimalField amountHT) {
		this.amountHT = amountHT;
	}

	public BigDecimalField getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimalField taxAmount) {
		this.taxAmount = taxAmount;
	}

	public BigDecimalField getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimalField discount) {
		this.discount = discount;
	}

	public BigDecimalField getAmountTTC() {
		return amountTTC;
	}

	public void setAmountTTC(BigDecimalField amountTTC) {
		this.amountTTC = amountTTC;
	}

	public BigDecimalField getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimalField discountRate) {
		this.discountRate = discountRate;
	}

	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}

	public void setFxmlLoader(FXMLLoader fxmlLoader) {
		this.fxmlLoader = fxmlLoader;
	}

	public Pagination getPagination() {
		return pagination;
	}

	
	
	
}
