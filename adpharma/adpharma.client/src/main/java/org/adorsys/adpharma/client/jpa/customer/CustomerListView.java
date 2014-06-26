package org.adorsys.adpharma.client.jpa.customer;

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

import org.adorsys.adpharma.client.jpa.gender.Gender;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrderSearchInput;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Calendar;

import javafx.beans.property.SimpleBooleanProperty;

import java.math.BigDecimal;

import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.employer.Employer;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.customertype.CustomerType;
import org.adorsys.adpharma.client.jpa.customer.Customer;

public class CustomerListView
{

	@FXML
	BorderPane rootPane;

	@FXML
	private Button searchButton;

	@FXML
	private Button createButton;

	@FXML
	private Button editButton;

	@FXML
	HBox searchBar;

	private TextField customerName ;

	private ComboBox<CustomerCustomerCategory> category ;

	@FXML
	private TableView<Customer> dataList;

	@Inject
	private Locale locale;

	@FXML
	private Pagination pagination;

	@Inject
	@Bundle({ CrudKeys.class
		, Customer.class
	})
	private ResourceBundle resourceBundle;

	@Inject
	private FXMLLoader fxmlLoader;

	@PostConstruct
	public void postConstruct()
	{
		FXMLLoaderUtils.load(fxmlLoader, this, resourceBundle);
		ViewBuilder viewBuilder = new ViewBuilder();
		//      dataList = viewBuilder.addTable("dataList");
		ViewBuilderUtils.newStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle,300d);
		//		viewBuilder.addDateColumn(dataList, "birthDate", "Customer_birthDate_description.title", resourceBundle, "dd-MM-yyyy", locale);
		viewBuilder.addStringColumn(dataList, "mobile", "Customer_mobile_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "serialNumber", "Customer_serialNumber_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "email", "Customer_email_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "employer", "Customer_employer_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "customerCategory", "Customer_customerCategory_description.title", resourceBundle);
		viewBuilder.addStringColumn(dataList, "totalDebt", "Customer_totalDebt_description.title", resourceBundle);

		// Field not displayed in table
		// Field not displayed in table
		//      pagination = viewBuilder.addPagination();
		//      viewBuilder.addSeparator();
		//
		//      HBox buttonBar = viewBuilder.addButtonBar();
		//      createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
		//      searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		//      rootPane = viewBuilder.toAnchorPane();
		buildsearchBar();
	}

	public void bind(CustomerSearchInput searchInput)
	{

		customerName.textProperty().bindBidirectional(searchInput.getEntity().fullNameProperty());
		category.valueProperty().bindBidirectional(searchInput.getEntity().customerCategoryProperty());
	}

	public void buildsearchBar(){
		customerName =ViewBuilderUtils.newTextField("customerName", false);
		customerName.setPromptText("customer Name");
		customerName.setPrefWidth(300d);
		customerName.setPrefHeight(40d);


		category =ViewBuilderUtils.newComboBox(null, "category", false);
		category.setPromptText("ALL CATEGORIE");
		category.setPrefWidth(200d);
		category.setPrefHeight(40d);

		searchButton =ViewBuilderUtils.newButton("Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
		searchButton.setPrefHeight(40d);
		searchBar.getChildren().addAll(customerName,category,searchButton);
	}

	public TextField getCustomerName()
	{
		return customerName;
	}

	public ComboBox<CustomerCustomerCategory> getCategory()
	{
		return category;
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

	public TableView<Customer> getDataList()
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

}
