package org.adorsys.adpharma.client.jpa.productdetailconfig;

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
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class ProductDetailConfigListView
{

	@FXML
	BorderPane rootPane;

	@FXML
	HBox searchBar;

	@FXML
	private Button searchButton;

	@FXML
	private Button createButton;

	@FXML
	private Button editButton;

	@FXML
	private TableView<ProductDetailConfig> dataList;

	private TextField articleOriginName ;

	private TextField articleTargetName ;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, ProductDetailConfig.class
		, Article.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	private FXMLLoader fxmlLoader;


	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this,resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//		dataList = viewBuilder.addTable("dataList");
		ViewBuilderUtils.newStringColumn(dataList, "source", "ProductDetailConfig_source_description.title", resourceBundle,400d);
		ViewBuilderUtils.newStringColumn(dataList, "target", "ProductDetailConfig_target_description.title", resourceBundle,400d);
		viewBuilder.addBigDecimalColumn(dataList, "targetQuantity", "ProductDetailConfig_targetQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "salesPrice", "ProductDetailConfig_salesPrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
		// Field not displayed in table
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

		articleOriginName =ViewBuilderUtils.newTextField("articleOriginName", false);
		articleOriginName.setPromptText("Article Origin Name");
		articleOriginName.setPrefHeight(40d);
		articleOriginName.setPrefWidth(300d);

		articleTargetName =ViewBuilderUtils.newTextField("articleTargetName", false);
		articleTargetName.setPromptText("Article Target Name");
		articleTargetName.setPrefHeight(40d);
		articleTargetName.setPrefWidth(300d);


		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(40d);
		searchBar.getChildren().addAll(articleOriginName,articleTargetName,searchButton);
	}

	public Button getCreateButton()
	{
		return createButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public Button getEditButton()
	{
		return editButton;
	}

	public TableView<ProductDetailConfig> getDataList()
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

	public TextField getArticleOriginName(){
		return articleOriginName ;
	}

	public TextField getArticleTargetName(){
		return articleTargetName ;
	}

}
