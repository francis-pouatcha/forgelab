package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.delivery.DeliverySearchInput;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ArticleLotListView
{

	@FXML
	BorderPane rootPane;

	@FXML
	HBox searchBar;

	private TextField internalPic ;

	private TextField articleName ;

	private Button searchButton;

	@FXML
	private Button createButton;

	@FXML
	private Button moveButton;

	@FXML
	private Button detailsButton;

	@FXML
	private TableView<ArticleLot> dataList;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, ArticleLot.class
		, Article.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	private FXMLLoader fxmlLoader ;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//		dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "mainPic", "ArticleLot_mainPic_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "internalPic", "ArticleLot_internalPic_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "agency", "ArticleLot_agency_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(dataList, "article.pic", "Article_article_description.title", resourceBundle,200d);
		viewBuilder.addDateColumn(dataList, "expirationDate", "ArticleLot_expirationDate_description.title", resourceBundle, "dd-MM-yyyy", locale);
		viewBuilder.addBigDecimalColumn(dataList, "stockQuantity", "ArticleLot_stockQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "ArticleLot_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "purchasePricePU", "ArticleLot_purchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalPurchasePrice", "ArticleLot_totalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "totalSalePrice", "ArticleLot_totalSalePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		pagination = viewBuilder.addPagination();
		//		viewBuilder.addSeparator();
		//
		//		HBox buttonBar = viewBuilder.addButtonBar();
		//		createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//		searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//		rootPane = viewBuilder.toAnchorPane();
		buildsearchBar();
	}

	public void buildsearchBar(){
		internalPic =ViewBuilderUtils.newTextField("internalPic", false);
		internalPic.setPromptText("internal Pic");
		internalPic.setPrefHeight(40d);

		articleName =ViewBuilderUtils.newTextField("articleName", false);
		articleName.setPromptText("Article Name");
		articleName.setPrefHeight(40d);
		articleName.setPrefWidth(400d);

		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(40d);
		searchBar.getChildren().addAll(internalPic,articleName,searchButton);
	}

	public void bind(ArticleLotSearchInput searchInput)
	{

		internalPic.textProperty().bindBidirectional(searchInput.getEntity().internalPicProperty());
		articleName.textProperty().bindBidirectional(searchInput.getEntity().articleNameProperty());
	}

	public Button getCreateButton()
	{
		return createButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public Button getMoveButton()
	{
		return moveButton;
	}

	public Button getDetailsButton()
	{
		return detailsButton;
	}

	public TableView<ArticleLot> getDataList()
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

	public TextField getInternalPic()
	{
		return internalPic;
	}

	public TextField getArticleName()
	{
		return articleName;
	}

}
