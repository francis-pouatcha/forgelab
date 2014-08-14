package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;

public class ModalArticleLotSearchView extends ApplicationModal{
	@FXML
	private VBox rootPane ;

	@FXML
	private TextField articleName;

	@FXML Button cancelButton;
	
	@FXML
	private CheckBox onlyBreackButton ;
	
	@FXML
	private Button searchButton ;

	@FXML
	Pagination pagination;

	@FXML
	TableView<ArticleLot> dataList;

	@Inject
	FXMLLoader fxmlLoader;

	@Inject
	@Bundle({ CrudKeys.class
		, ArticleLot.class
		, Article.class
	})
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

		//		viewBuilder.addStringColumn(dataList, "mainPic", "ArticleLot_mainPic_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "internalPic", "ArticleLot_internalPic_description.title", resourceBundle,120d);
		//		viewBuilder.addStringColumn(dataList, "secondaryPic", "ArticleLot_secondaryPic_description.title", resourceBundle);
		//	      viewBuilder.addStringColumn(dataList, "articleName", "ArticleLot_articleName_description.title", resourceBundle);
		ViewBuilderUtils.newStringColumn(dataList, "articleName", "Article_articleName_description.title", resourceBundle,300d);
		//		viewBuilder.addDateColumn(dataList, "expirationDate", "ArticleLot_expirationDate_description.title", resourceBundle, "dd-MM-yyyy", locale);
		viewBuilder.addDateColumn(dataList, "creationDate", "ArticleLot_creationDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale,160d);
		viewBuilder.addBigDecimalColumn(dataList, "stockQuantity", "ArticleLot_stockQuantity_description.title", resourceBundle, NumberType.INTEGER, locale);
		viewBuilder.addBigDecimalColumn(dataList, "salesPricePU", "ArticleLot_salesPricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		viewBuilder.addStringColumn(dataList, "article.section", "Article_section_description.title", resourceBundle,300d);
		viewBuilder.addStringColumn(dataList, "article.clearanceConfig", "Article_clearanceConfig_description.title", resourceBundle);
		//	      viewBuilder.addBigDecimalColumn(dataList, "purchasePricePU", "ArticleLot_purchasePricePU_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//	      viewBuilder.addBigDecimalColumn(dataList, "totalPurchasePrice", "ArticleLot_totalPurchasePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);
		//	      viewBuilder.addBigDecimalColumn(dataList, "totalSalePrice", "ArticleLot_totalSalePrice_description.title", resourceBundle, NumberType.CURRENCY, locale);

	
	}

	public TextField getArticleName() {
		return articleName;

	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public TableView<ArticleLot> getDataList() {
		return dataList;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public CheckBox getOnlyBreackButton() {
		return onlyBreackButton;
	}

	public void setOnlyBreackButton(CheckBox onlyBreackButton) {
		this.onlyBreackButton = onlyBreackButton;
	}

	public FXMLLoader getFxmlLoader() {
		return fxmlLoader;
	}

	public void setFxmlLoader(FXMLLoader fxmlLoader) {
		this.fxmlLoader = fxmlLoader;
	}

	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setRootPane(VBox rootPane) {
		this.rootPane = rootPane;
	}

	public void setArticleName(TextField articleName) {
		this.articleName = articleName;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public void setDataList(TableView<ArticleLot> dataList) {
		this.dataList = dataList;
	}

	public Button getSearchButton() {
		return searchButton;
	}
	
	
}
