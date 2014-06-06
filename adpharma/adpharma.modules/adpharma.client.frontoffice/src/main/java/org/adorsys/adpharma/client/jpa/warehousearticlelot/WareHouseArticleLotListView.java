package org.adorsys.adpharma.client.jpa.warehousearticlelot;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class WareHouseArticleLotListView
{
	

	@FXML
	BorderPane rootPane;

	@FXML
	HBox searchBar;
	
	private TextField internalPic ;

	private TextField articleName ;
	
	private ComboBox<WareHouseArticleLotWareHouse> wareHouse ;
	
	private Button searchButton;

	@FXML
	private Button transferToSaleButton;
	
	@FXML
	private TableView<WareHouseArticleLot> dataList;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, WareHouseArticleLot.class
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
		viewBuilder.addStringColumn(dataList, "mainCip", "WareHouseArticleLot_mainCip_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "secondaryCip", "WareHouseArticleLot_secondaryCip_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "internalCip", "WareHouseArticleLot_internalCip_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(dataList, "articleName", "WareHouseArticleLot_articleName_description.title", resourceBundle,350d);
		ViewBuilderUtils.newStringColumn(dataList, "wareHouse", "WareHouseArticleLot_wareHouse_description.title", resourceBundle,300d);
		viewBuilder.addBigDecimalColumn(dataList, "stockQuantity", "WareHouseArticleLot_stockQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);

		pagination = viewBuilder.addPagination();
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
		
		wareHouse = ViewBuilderUtils.newComboBox(null, "wareHouse", false);
		wareHouse.setPrefHeight(40d);
		wareHouse.setPrefWidth(200d);

		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(40d);
		searchBar.getChildren().addAll(internalPic,articleName,wareHouse,searchButton);
	}

	public void bind(WareHouseArticleLotSearchInput searchInput)
	{
		internalPic.textProperty().bindBidirectional(searchInput.getEntity().internalCipProperty());
		articleName.textProperty().bindBidirectional(searchInput.getEntity().articleNameProperty());
		wareHouse.valueProperty().bindBidirectional(searchInput.getEntity().wareHouseProperty());
	}

	public Button getTransferToSaleButton()
	{
		return transferToSaleButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public TableView<WareHouseArticleLot> getDataList()
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
	
	public ComboBox<WareHouseArticleLotWareHouse> getWareHouse()
	{
		return wareHouse;
	}


}
