package org.adorsys.adpharma.client.jpa.stockmovement;

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

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminal;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminalConverter;
import org.adorsys.adpharma.client.jpa.stockmovementterminal.StockMovementTerminalListCellFatory;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementType;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementTypeConverter;
import org.adorsys.adpharma.client.jpa.stockmovementtype.StockMovementTypeListCellFatory;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class StockMovementListView
{

	@FXML
	BorderPane rootPane;

	@FXML
	HBox searchBar;

	@FXML
	private Button searchButton;

	@FXML
	private Button createButton;

	private ComboBox<StockMovementTerminal> origin ;

	private ComboBox<StockMovementTerminal> destination ;

	private ComboBox<StockMovementType> type ;

	private TextField raison ;
	private TextField cipm ;

	@FXML
	private TableView<StockMovement> dataList;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, StockMovement.class,StockMovementTerminal.class,StockMovementType.class
		, Article.class
		, Agency.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	private StockMovementTypeConverter stockMovementTypeConverter;

	@Inject
	private StockMovementTerminalConverter stockMovementTerminalConverter;
	@Inject
	private StockMovementTerminalListCellFatory movementDestinationListCellFatory;
	@Inject
	private StockMovementTerminalListCellFatory movementOriginListCellFatory;
	@Inject
	private StockMovementTypeListCellFatory movementTypeListCellFatory;
	@Inject
	@Bundle(StockMovementType.class)
	private ResourceBundle movementTypeBundle;
	@Inject
	@Bundle(StockMovementTerminal.class)
	private ResourceBundle movementDestinationBundle;
	@Inject
	@Bundle(StockMovementTerminal.class)
	private ResourceBundle movementOriginBundle;

	@Inject
	FXMLLoader fxmlLoader;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//		dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "internalPic", "StockMovement_internalPic_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(dataList, "article", "StockMovement_article_description.title", resourceBundle,300d);
		viewBuilder.addBigDecimalColumn(dataList, "movedQty", "StockMovement_movedQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addEnumColumn(dataList, "movementType", "StockMovement_movementType_description.title", resourceBundle, stockMovementTypeConverter);
		ViewBuilderUtils.newStringColumn(dataList, "raison", "StockMovement_raison_description.title", resourceBundle,150d);

		ViewBuilderUtils.newStringColumn(dataList, "creatingUser", "StockMovement_creatingUser_description.title", resourceBundle,150d);
		viewBuilder.addDateColumn(dataList, "creationDate", "StockMovement_creationDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
		viewBuilder.addEnumColumn(dataList, "movementOrigin", "StockMovement_movementOrigin_description.title", resourceBundle, stockMovementTerminalConverter);
		viewBuilder.addEnumColumn(dataList, "movementDestination", "StockMovement_movementDestination_description.title", resourceBundle, stockMovementTerminalConverter);
		//		viewBuilder.addStringColumn(dataList, "agency", "StockMovement_agency_description.title", resourceBundle);
		//		viewBuilder.addBigDecimalColumn(dataList, "initialQty", "StockMovement_initialQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "finalQty", "StockMovement_finalQty_description.title", resourceBundle, NumberType.INTEGER, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "totalPurchasingPrice", "StockMovement_totalPurchasingPrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "totalDiscount", "StockMovement_totalDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "totalSalesPrice", "StockMovement_totalSalesPrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//      pagination = viewBuilder.addPagination();
		//      viewBuilder.addSeparator();

		//      HBox buttonBar = viewBuilder.addButtonBar();
		//      createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//      searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//      rootPane = viewBuilder.toAnchorPane();
		buildsearchBar();

		ComboBoxInitializer.initialize(type, stockMovementTypeConverter, movementTypeListCellFatory, movementTypeBundle);
		ComboBoxInitializer.initialize(origin, stockMovementTerminalConverter, movementOriginListCellFatory, movementOriginBundle);
		ComboBoxInitializer.initialize(destination, stockMovementTerminalConverter, movementDestinationListCellFatory, movementDestinationBundle);

	}

	public void buildsearchBar(){
		origin =ViewBuilderUtils.newComboBox(null,"origin", resourceBundle,StockMovementTerminal.valuesWithNull(),false);
		origin.setPromptText("ORIGINES");
		origin.setPrefHeight(40d);
		origin.setPrefWidth(150d);

		destination =ViewBuilderUtils.newComboBox(null,"destination", resourceBundle,StockMovementTerminal.valuesWithNull(),false);
		destination.setPromptText("DESTINATIONS ");
		destination.setPrefHeight(40d);
		destination.setPrefWidth(165d);

		raison =ViewBuilderUtils.newTextField("raison", false);
		raison.setPromptText("Raison");
		raison.setPrefHeight(40d);
		raison.setPrefWidth(200d);
		
		cipm =ViewBuilderUtils.newTextField("cipm", false);
		cipm.setPromptText("CIPM");
		cipm.setPrefHeight(40d);
		cipm.setPrefWidth(150d);

		type =ViewBuilderUtils.newComboBox(null,"ALL", resourceBundle,StockMovementType.valuesWithNull(),false);
		type.setPromptText("TYPES");
		type.setPrefHeight(40d);
		type.setPrefWidth(150d);

		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(40d);
		searchBar.getChildren().addAll(cipm,raison,origin,destination,type,searchButton);
	}

	public void bind(StockMovementSearchInput searchInput)
	{
		origin.valueProperty().bindBidirectional(searchInput.getEntity().movementOriginProperty());
		raison.textProperty().bindBidirectional(searchInput.getEntity().raisonProperty());
		destination.valueProperty().bindBidirectional(searchInput.getEntity().movementDestinationProperty());
		type.valueProperty().bindBidirectional(searchInput.getEntity().movementTypeProperty());
		cipm.textProperty().bindBidirectional(searchInput.getEntity().internalPicProperty());
	}

	public Button getCreateButton()
	{
		return createButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public TableView<StockMovement> getDataList()
	{
		return dataList;
	}

	public ComboBox<StockMovementTerminal> getOrigin()
	{
		return origin;
	}

	public ComboBox<StockMovementTerminal> getDestination()
	{
		return destination;
	}

	public ComboBox<StockMovementType> getType()
	{
		return type;
	}

	public TextField getRaison()
	{
		return raison;
	}

	public BorderPane getRootPane()
	{
		return rootPane;
	}

	public Pagination getPagination()
	{
		return pagination;
	}

}
