package org.adorsys.adpharma.client.jpa.article;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.beans.property.SimpleStringProperty;

import org.adorsys.adpharma.client.jpa.section.Section;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;

import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;

import java.math.BigDecimal;
import java.util.Calendar;

import org.adorsys.adpharma.client.jpa.salesmargin.SalesMargin;
import org.adorsys.adpharma.client.jpa.packagingmode.PackagingMode;

import javafx.beans.property.SimpleLongProperty;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotSearchInput;

public class ArticleListView
{

	@FXML
	BorderPane rootPane;
	
	@FXML
	HBox searchBar;

	@FXML
	private Button editButton;
	
	@FXML
	private Button deleteButton ;
	
	@FXML
	private Button printButton ;
	
	@FXML
	private Button sectionChangeBtn ;
	
	@FXML
	private Button vatChangeBtn ;

	@FXML
	private Button createButton;
	
	@FXML
	private Button searchButton;

	@FXML
	private TableView<Article> dataList;
	
	@FXML
	private TableView<ArticleDetails> dataListDetails;

	@Inject
	private Locale locale;
	
	private ComboBox<ArticleSection> section ;
	
	private ComboBox<ArticleVat> vat ;

	private TextField articleName ;
	
	private TextField mainPic ;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, Article.class
		, Agency.class
		, ArticleDetails.class
		, Delivery.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	private FXMLLoader fxmlLoader ;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//      dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "pic", "Article_pic_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle,300d);
		viewBuilder.addBigDecimalColumn(dataList, "qtyInStock", "Article_qtyInStock_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "sppu", "Article_sppu_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "pppu", "Article_pppu_description.title", resourceBundle, NumberType.CURRENCY, locale);
//		viewBuilder.addStringColumn(dataList, "manufacturer", "Article_manufacturer_description.title", resourceBundle);
		// Field not displayed in table
		viewBuilder.addBigDecimalColumn(dataList, "minStockQty", "Article_minStockQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "maxStockQty", "Article_maxStockQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addStringColumn(dataList, "section", "Article_section_description.title", resourceBundle,250d);
		viewBuilder.addStringColumn(dataList, "vat", "Article_vat_description.title", resourceBundle);

		viewBuilder.addDateColumn(dataList, "lastStockEntry", "Article_lastStockEntry_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addDateColumn(dataList, "lastOutOfStock", "Article_lastOutOfStock_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
        // Field not displayed in table
        //viewBuilder.addStringColumn(dataList, "clearanceConfig", "Article_clearanceConfig_description.title", resourceBundle);
		
		// Data List article details
		ViewBuilderUtils.newStringColumn(dataListDetails, "internalPic", "ArticleDetails_internalpic_description.title", resourceBundle,200d);
		viewBuilder.addStringColumn(dataListDetails, "mainPic", "ArticleDetails_mainpic_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(dataListDetails, "articleName", "ArticleDetails_articleName_description.title", resourceBundle,300d);
		ViewBuilderUtils.newStringColumn(dataListDetails, "supplier", "ArticleDetails_supplier_description.title", resourceBundle,300d);
		viewBuilder.addDateColumn(dataListDetails, "deliveryDate", "ArticleDetails_deliveryDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addBigDecimalColumn(dataListDetails, "purchasePricePU", "ArticleDetails_purchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//pagination = viewBuilder.addPagination();
//		viewBuilder.addSeparator();
//
//		HBox buttonBar = viewBuilder.addButtonBar();
//		createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
//		editButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
//		rootPane = viewBuilder.toAnchorPane();
		buildsearchBar();
	}
	
	public void buildsearchBar(){
		section =ViewBuilderUtils.newComboBox(null,"section", false);
		section.setPromptText("TOUS LES RAYONS ");
		section.setPrefHeight(30d);
		section.setPrefWidth(300d);
		
		vat =ViewBuilderUtils.newComboBox(null,"vat", false);
		vat.setPromptText("TAXE ");
		vat.setPrefHeight(30d);
		vat.setPrefWidth(100d);

		articleName =ViewBuilderUtils.newTextField("articleName", false);
		articleName.setPromptText("Article Name");
		articleName.setPrefHeight(30d);
		articleName.setPrefWidth(300d);
		
		mainPic =ViewBuilderUtils.newTextField("mainPic", false);
		mainPic.setPromptText("mainPic");
		mainPic.setPrefHeight(30d);

		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(30d);
		searchBar.getChildren().addAll(mainPic,articleName,section,vat,searchButton);
	}

	public void bind(ArticleSearchInput searchInput)
	{

		mainPic.textProperty().bindBidirectional(searchInput.getEntity().picProperty());
		articleName.textProperty().bindBidirectional(searchInput.getEntity().articleNameProperty());
		section.valueProperty().bindBidirectional(searchInput.getEntity().sectionProperty());
		vat.valueProperty().bindBidirectional(searchInput.getEntity().vatProperty());
	}

	public Button getCreateButton()
	{
		return createButton;
	}
	
	public Button getDeleteButton()
	{
		return deleteButton;
	}
	
	public Button getPrintButton()
	{
		return printButton;
	}
	
	

	public Button getEditButton()
	{
		return editButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}
	
	public TableView<Article> getDataList()
	{
		return dataList;
	}
	
	public TableView<ArticleDetails> getDataListDetails() {
		return dataListDetails;
	}

	public BorderPane getRootPane()
	{
		return rootPane;
	}

	public Pagination getPagination()
	{
		return pagination;
	}
	
	public TextField getArticleName()
	{
		return articleName;
	}
	public TextField getMainPic()
	{
		return mainPic;
	}
	public ComboBox<ArticleSection> getSection()
	{
		return section;
	}

	public HBox getSearchBar() {
		return searchBar;
	}

	public Locale getLocale() {
		return locale;
	}

	public ComboBox<ArticleVat> getVat() {
		return vat;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}

	public Button getSectionChangeBtn() {
		return sectionChangeBtn;
	}

	public Button getVatChangeBtn() {
		return vatChangeBtn;
	}
	
	

}
