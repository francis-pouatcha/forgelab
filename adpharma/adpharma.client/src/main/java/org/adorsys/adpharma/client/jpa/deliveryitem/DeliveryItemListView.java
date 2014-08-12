package org.adorsys.adpharma.client.jpa.deliveryitem;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class DeliveryItemListView
{

	@FXML
	AnchorPane rootPane;

	@FXML
	private Button searchButton;

	@FXML
	private TextField deliveryNumber;


	@FXML
	private Button createButton;

	@FXML
	private TableView<DeliveryItem> dataList;

	@Inject
	private Locale locale;

	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, DeliveryItem.class
		, Article.class,Delivery.class
	})
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		ViewBuilder viewBuilder = new ViewBuilder();
		dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "internalPic", "DeliveryItem_internalPic_description.title", resourceBundle);
		//		viewBuilder.addStringColumn(dataList, "mainPic", "DeliveryItem_mainPic_description.title", resourceBundle);
		//      viewBuilder.addStringColumn(dataList, "secondaryPic", "DeliveryItem_secondaryPic_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "delivery.supplier", "Delivery_supplier_description.title", resourceBundle,250d);
		viewBuilder.addStringColumn(dataList, "delivery.deliveryNumber", "Delivery_deliveryNumber_description.title", resourceBundle,100d);

		viewBuilder.addStringColumn(dataList, "articleName", "DeliveryItem_articleName_description.title", resourceBundle,250d);
		//      viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle);
		//      viewBuilder.addDateColumn(dataList, "expirationDate", "DeliveryItem_expirationDate_description.title", resourceBundle, "dd-MM-yyyy", locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "qtyOrdered", "DeliveryItem_qtyOrdered_description.title", resourceBundle, NumberType.INTEGER, locale);
		//		viewBuilder.addBigDecimalColumn(dataList, "freeQuantity", "DeliveryItem_freeQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "stockQuantity", "DeliveryItem_stockQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "DeliveryItem_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addBigDecimalColumn(dataList, "purchasePricePU", "DeliveryItem_purchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addDateColumn(dataList, "delivery.recordingDate", "DeliveryItem_creationDate_description.title", resourceBundle, "dd-MM-yyyy", locale);

		//		viewBuilder.addBigDecimalColumn(dataList, "totalPurchasePrice", "DeliveryItem_totalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
		pagination = viewBuilder.addPagination();
//		deliveryNumber = ViewBuilderUtils.newTextField("deliveryNumber", false);
		viewBuilder.addSeparator();

		HBox buttonBar = viewBuilder.addButtonBar();
//		viewBuilder.addControl(buttonBar,new Label("Numero de la Livraison : "));
//		viewBuilder.addControl(buttonBar, deliveryNumber);
		searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		rootPane = viewBuilder.toAnchorPane();
	}

	public Button getCreateButton()
	{
		return createButton;
	}

	public Button getSearchButton()
	{
		return searchButton;
	}

	public TableView<DeliveryItem> getDataList()
	{
		return dataList;
	}

	public AnchorPane getRootPane()
	{
		return rootPane;
	}

	public Pagination getPagination()
	{
		return pagination;
	}

	public TextField getDeliveryNumber(){
		return deliveryNumber;
	}

}
