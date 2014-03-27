package org.adorsys.adpharma.client.jpa.article;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLotSearchInput;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchDoneEvent;
import org.adorsys.javafx.crud.extensions.events.ModalEntitySearchRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

public class ModalArticleSearchView extends ApplicationModal{

	@FXML
	private VBox rootPane ;

	@FXML
	private TextField articleName;

	@FXML Button cancelButton;
	
	@FXML
	Pagination pagination;

	@FXML
	TableView<Article> dataList;

	@Inject
	FXMLLoader fxmlLoader;

	@Inject
	@Bundle({ CrudKeys.class, Article.class })
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;
	
	@Override
	public VBox getRootPane() {
		return rootPane;
	}

	@PostConstruct
	public void onPostConstruct(){
		FXMLLoaderUtils.load(fxmlLoader, this,resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
//		dataList = viewBuilder.addTable("dataList");
		viewBuilder.addStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle,350d);
		viewBuilder.addStringColumn(dataList, "pic", "Article_pic_description.title", resourceBundle,200d);
		viewBuilder.addStringColumn(dataList, "manufacturer", "Article_manufacturer_description.title", resourceBundle,150d);
		// Field not displayed in table
		viewBuilder.addBigDecimalColumn(dataList, "qtyInStock", "Article_qtyInStock_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "sppu", "Article_sppu_description.title", resourceBundle, NumberType.INTEGER, locale);

	}

	public TextField getArticleName() {
		return articleName;
	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public TableView<Article> getDataList() {
		return dataList;
	}

	public Pagination getPagination() {
		return pagination;
	}

}
