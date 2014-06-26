package org.adorsys.adpharma.client.jpa.inventory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.inventoryitem.InventoryItem;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCashDrawer;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderCustomer;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderInsurance;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderView;
import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.jpa.section.Section;
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
public class InventoryDisplayView
{


	@FXML
	private BorderPane rootPane;

	@FXML
	private Button closeButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Button ordonnancierButton;
	
	@FXML
	private Button printRepportButton;
	
	@FXML
	private Button printButton;


	@FXML
	private GridPane inventoryItemBar;

	private TextField articleName;

	private TextField internalPic;

	private BigDecimalField asseccedQty;

	private BigDecimalField gapQty;

	private BigDecimalField expectedQty;

	private Button okButton;

	//	private Button confirmSelectionButton;

	@FXML
	private ComboBox<DocumentProcessingState> status;

	//	Rigth grid pane components
	@FXML
	private ComboBox<InventorySection> section;

	//	Rigth grid pane components
	@FXML
	private ComboBox<InventoryRecordingUser> agent;

	@FXML
	private TextField numBon;

	@FXML
	private TextField code;

	@FXML
	private GridPane rigthGrid ;

	//	amounts grid pane components

	@FXML
	private GridPane amountPane;

	private BigDecimalField gapPA;

	private BigDecimalField gap;

	private BigDecimalField discount;

	private BigDecimalField gapPV;

	private BigDecimalField discountRate;

	//	@FXML
	//	private ComboBox<SalesOrderVat> tax;



	@Inject
	private SalesOrderView view;

	@Inject
	@Bundle({ CrudKeys.class, Inventory.class ,InventoryItem.class,Article.class})
	private ResourceBundle resourceBundle;

	@Inject
	private FXMLLoader fxmlLoader;

	@FXML
	private Pagination pagination;

	@FXML
	private TableView<InventoryItem> dataList;

	@Inject
	private Locale locale;

	@FXML
	private ContextMenu datalistContextMenu;


	@Inject
	private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

	private TableColumn<InventoryItem, BigDecimal> asseccedQtyColumn;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addStringColumn(dataList, "internalPic", "InventoryItem_internalPic_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "article", "InventoryItem_article_description.title", resourceBundle,400d);
		viewBuilder.addBigDecimalColumn(dataList, "expectedQty", "InventoryItem_expectedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		asseccedQtyColumn = viewBuilder.addEditableBigDecimalColumn(dataList, "asseccedQtyColumn", "InventoryItem_asseccedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addSimpleNumberColumn(dataList, "gap", "InventoryItem_gap_description.title", resourceBundle);
		viewBuilder.addBigDecimalColumn(dataList, "gapTotalSalePrice", "InventoryItem_gapTotalSalePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "gapTotalPurchasePrice", "InventoryItem_gapTotalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);

		buildOrderItemBar();
		buildAmountPane();
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
		//		violations.addAll(toOneAggreggationFieldValidator.validate(cashDrawer, model.getCashDrawer(), SalesOrder.class, "cashDrawer", resourceBundle));

		return violations;
	}
	public void buildAmountPane(){
		gapPA = ViewBuilderUtils.newBigDecimalField( "amountHT", NumberType.CURRENCY,locale,false);
		gapPA.setEditable(false);

		discount = ViewBuilderUtils.newBigDecimalField( "discount", NumberType.CURRENCY,locale,false);
		discount.setEditable(true);

		gapPV = ViewBuilderUtils.newBigDecimalField( "amountTTC", NumberType.CURRENCY,locale,false);
		gapPV.setEditable(false);

		gap = ViewBuilderUtils.newBigDecimalField( "taxAmount", NumberType.INTEGER,locale,false);
		gap.setEditable(false);

		rigthGrid.add(gapPA, 1, 0);
		rigthGrid.add(gap, 1, 1);
		rigthGrid.add(discount, 1, 2);
		rigthGrid.add(gapPV, 1, 3);

	}


	public void buildOrderItemBar(){
		internalPic = ViewBuilderUtils.newTextField("internalPic", false);
		internalPic.setPromptText("cip");
		internalPic.setTooltip(new Tooltip("cip"));

		articleName = ViewBuilderUtils.newTextField("articleName", false);
		articleName.setPromptText("Designation");
		articleName.setPrefWidth(400d);
		articleName.setTooltip(new Tooltip("Designation article"));

		asseccedQty = ViewBuilderUtils.newBigDecimalField( "realQty", NumberType.INTEGER,locale,false);
		asseccedQty.setTooltip(new Tooltip("Qte Reel"));
		asseccedQty.setPrefWidth(75d);

		expectedQty = ViewBuilderUtils.newBigDecimalField("salesPricePU", NumberType.INTEGER, locale,false);
		expectedQty.setTooltip(new Tooltip("Prix de vente unitaire"));
		expectedQty.setPrefWidth(75d);
		expectedQty.setEditable(false);

		gapQty = ViewBuilderUtils.newBigDecimalField( "totalSalePrice", NumberType.INTEGER, locale,false);
		gapQty.setTooltip(new Tooltip("Total prix de vente "));
		gapQty.setPrefWidth(130d);
		gapQty.setEditable(false);

		okButton = ViewBuilderUtils.newButton("Entity_ok.text", "ok", resourceBundle, AwesomeIcon.ARROW_DOWN);
		inventoryItemBar.addRow(0,new Label("CipM"),new Label("Designation"),new Label("Stock M"),new Label("Stock R"),new Label("Ecart"));
		inventoryItemBar.addRow(1,internalPic,articleName,expectedQty,asseccedQty,gapQty,okButton);
		//		saleOrderItemBar.getChildren().addAll(
		//				internalPic,articleName,orderedQty,salesPricePU,totalSalePrice,okButton);

	}

	public void bind(Inventory model)
	{
		gapPA.numberProperty().bindBidirectional(model.gapPurchaseAmountProperty());
		gapPV.numberProperty().bindBidirectional(model.gapSaleAmountProperty());
		section.valueProperty().bindBidirectional(model.sectionProperty());
		status.valueProperty().bindBidirectional(model.inventoryStatusProperty());
		agent.valueProperty().bindBidirectional(model.recordingUserProperty());
		dataList.itemsProperty().bindBidirectional(model.inventoryItemsProperty());
		inventoryItemBar.visibleProperty().bind(model.inventoryStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
		closeButton.disableProperty().bind(model.inventoryStatusProperty().isEqualTo(DocumentProcessingState.CLOSED));
		code.textProperty().bindBidirectional(model.inventoryNumberProperty());
		asseccedQtyColumn.editableProperty().bind(model.inventoryStatusProperty().isNotEqualTo(DocumentProcessingState.CLOSED));
		//		view.bind(model);

	}

	public void bind(InventoryItem item){
		internalPic.textProperty().bindBidirectional(item.internalPicProperty());
		articleName.textProperty().bindBidirectional(item.getArticle().articleNameProperty());
		asseccedQty.numberProperty().bindBidirectional(item.asseccedQtyProperty());
		expectedQty.numberProperty().bindBidirectional(item.expectedQtyProperty());
		//		item.gapProperty().bind(gapQty.numberProperty());
		//		realQty.numberProperty().addListener(new ChangeListener<BigDecimal>() {
		//			@Override
		//			public void changed(ObservableValue<? extends BigDecimal> obs,
		//					BigDecimal oldVal, BigDecimal newVal) {
		//				if(newVal==null){
		//					realQty.setNumber(BigDecimal.ZERO);
		//				}
		//				gapQty.setNumber(realQty.getNumber().multiply(expectedQty.getNumber()));
		//			}});
//		asseccedQty.numberProperty().addListener(new ChangeListener<BigDecimal>() {
//			@Override
//			public void changed(ObservableValue<? extends BigDecimal> obs,
//					BigDecimal oldVal, BigDecimal newVal) {
//				if(newVal==null){
//					gapQty.setNumber(BigDecimal.ZERO);
//				}else {
//					gapQty.setNumber(expectedQty.getNumber().subtract(newVal));
//				} 
//			}});
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

	//	public ComboBox<SalesOrderVat> getTax() {
	//		return tax;
	//	}

	public TableView<InventoryItem> getDataList() {
		return dataList;
	}


	public GridPane getSaleOrderItemBar() {
		return inventoryItemBar;
	}


	public TextField getArticleName() {
		return articleName;
	}


	public TextField getInternalPic() {
		return internalPic;
	}


	public BigDecimalField getAsseccedQty() {
		return asseccedQty;
	}


	public BigDecimalField getTotalSalePrice() {
		return gapQty;
	}


	public BigDecimalField getSalesPricePU() {
		return expectedQty;
	}


	public Button getOkButton() {
		return okButton;
	}
	
	public Button getPrintRepportButton() {
		return printRepportButton;
	}
	
	public Button getPrintButton() {
		return printButton;
	}



	public ComboBox<DocumentProcessingState> getStatus() {
		return status;
	}


	public ComboBox<InventorySection> getSection() {
		return section;
	}


	public BigDecimalField getDiscountRate() {
		return discountRate;
	}



	public TextField getNumBon() {
		return numBon;
	}


	public TextField getCode() {
		return code;
	}


	public GridPane getAmountPane() {
		return amountPane;
	}


	public BigDecimalField getAmountHT() {
		return gapPA;
	}


	public BigDecimalField getTaxAmount() {
		return gap;
	}


	public BigDecimalField getDiscount() {
		return discount;
	}


	public BigDecimalField getAmountTTC() {
		return gapPV;
	}


	public ContextMenu getDatalistContextMenu() {
		return datalistContextMenu;
	}
	//
	//	public MenuItem getDeleteSOIMenu() {
	//		return deleteSOIMenu;
	//	}
	//
	//	public MenuItem getEditSOIMenu() {
	//		return editSOIMenu;
	//	}


	public TableColumn<InventoryItem, BigDecimal> getAsseccedQtyColumn() {
		return asseccedQtyColumn;
	}
}
