package org.adorsys.adpharma.client.jpa.customer;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.FXMLLoaderUtils;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilderUtils;
@Singleton
public class ModalCustomerSearchView extends ApplicationModal{
	@FXML
	private VBox rootPane ;

	@FXML
	private TextField customerName;

	@FXML Button cancelButton;

	@FXML
	Pagination pagination;

	@FXML
	TableView<Customer> dataList;

	@Inject
	FXMLLoader fxmlLoader;

	@Inject
	@Bundle({ CrudKeys.class
		, Customer.class
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

		viewBuilder.addStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle);
		viewBuilder.addDateColumn(dataList, "birthDate", "Customer_birthDate_description.title", resourceBundle, "dd-MM-yyyy", locale);
		viewBuilder.addStringColumn(dataList, "landLinePhone", "Customer_landLinePhone_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "mobile", "Customer_mobile_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "fax", "Customer_fax_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "email", "Customer_email_description.title", resourceBundle);
	}

	public TextField getCustomerName() {
		return customerName;

	}

	public Button getCancelButton() {
		return cancelButton;
	}

	public TableView<Customer> getDataList() {
		return dataList;
	}

	public Pagination getPagination() {
		return pagination;
	}
}
