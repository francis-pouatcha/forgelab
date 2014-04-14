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
import org.adorsys.adpharma.client.jpa.article.Article;
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
	private Button createButton;
	
	@FXML
	private Button searchButton;

	@FXML
	private TableView<Article> dataList;

	@Inject
	private Locale locale;
	
	private ComboBox<ArticleSection> section ;

	private TextField articleName ;
	
	private TextField mainPic ;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, Article.class
		, Agency.class
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
		ViewBuilderUtils.newStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle,250d);
		viewBuilder.addStringColumn(dataList, "section", "Article_section_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "manufacturer", "Article_manufacturer_description.title", resourceBundle);
		// Field not displayed in table
		viewBuilder.addBigDecimalColumn(dataList, "qtyInStock", "Article_qtyInStock_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "sppu", "Article_sppu_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "pppu", "Article_pppu_description.title", resourceBundle, NumberType.CURRENCY, locale);

		viewBuilder.addDateColumn(dataList, "lastStockEntry", "Article_lastStockEntry_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addDateColumn(dataList, "lastOutOfStock", "Article_lastOutOfStock_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		// Field not displayed in table
		viewBuilder.addStringColumn(dataList, "agency", "Article_agency_description.title", resourceBundle);
//		pagination = viewBuilder.addPagination();
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
		section.setPromptText("ALL SECTION ");
		section.setPrefHeight(40d);
		section.setPrefWidth(150d);

		articleName =ViewBuilderUtils.newTextField("articleName", false);
		articleName.setPromptText("Article Name");
		articleName.setPrefHeight(40d);
		articleName.setPrefWidth(400d);
		
		mainPic =ViewBuilderUtils.newTextField("mainPic", false);
		mainPic.setPromptText("mainPic");
		mainPic.setPrefHeight(40d);

		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(40d);
		searchBar.getChildren().addAll(mainPic,articleName,section,searchButton);
	}

	public void bind(ArticleSearchInput searchInput)
	{

		mainPic.textProperty().bindBidirectional(searchInput.getEntity().picProperty());
		articleName.textProperty().bindBidirectional(searchInput.getEntity().articleNameProperty());
		section.valueProperty().bindBidirectional(searchInput.getEntity().sectionProperty());
	}

	public Button getCreateButton()
	{
		return createButton;
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
	
	

}
