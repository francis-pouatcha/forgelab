package org.adorsys.adpharma.client.jpa.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;

import jfxtras.scene.layout.VBox;

import org.adorsys.adpharma.client.events.DataMenuItem;
import org.adorsys.adpharma.client.jpa.accessroleenum.AccessRoleEnum;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.currency.Currency;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.employer.Employer;
import org.adorsys.adpharma.client.jpa.productdetailconfig.ProductDetailConfig;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.javafx.crud.extensions.events.MenuItemAddRequestedEvent;
import org.adorsys.javafx.crud.extensions.events.MenuItemRemoveRequestedEvent;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.RolesEvent;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.controlsfx.dialog.Dialogs;

public class DataLoadController {

	@Inject
	private ErrorMessageDialog errorMessageDialog;

	@Inject
	@Bundle({CrudKeys.class, DataLoadController.class})
	private ResourceBundle resourceBundle;
	
	@Inject
	private CompanyLoader companyLoader;
	
	@Inject
	private AgencyLoader agencyLoader;
	
	@Inject
	private SectionLoader sectionLoader;
	
	@Inject
	private ProductFamilyLoader productFamilyLoader;
	
	@Inject
	private ArticleLoader articleLoader;

	@Inject
	private ProductDetailConfigLoader productDetailConfigLoader;
	
	@Inject
	private SupplierLoader supplierLoader;
	
	@Inject
	private VATLoader vatLoader;
	
	@Inject
	private CurrencyLoader currencyLoader;
	
	@Inject
	private DeliveryLoader deliveryLoader;

	@Inject
	private LoginLoader loginLoader;
	
	@Inject
	private EmployerLoader employerLoader;
	
	@Inject
	private CustomerCategoryLoader customerCategoryLoader;
	
	@Inject
	private CustomerLoader customerLoader;
	
	private MenuItem loaderMenuItem;

	@Inject
	@MenuItemAddRequestedEvent
	private Event<DataMenuItem> loaderMenuItemAddEvent;
	@Inject
	@MenuItemRemoveRequestedEvent
	private Event<DataMenuItem> loaderMenuItemRemoveEvent;
	
	private HSSFWorkbook workbook;
	
	private DataMap dataMap = new DataMap();
	
	ProgressIndicator pi = new ProgressIndicator();
	Label progressLabel1 = new Label();
	Label progressLabel2 = new Label();

	Stage dialog = new Stage();

	@PostConstruct
	public void postConstruct() {
		
		dialog.initModality(Modality.APPLICATION_MODAL);
		
		loaderMenuItem = new MenuItem(resourceBundle.getString("DataLoadController_menuitem.title"));
		loaderMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle(resourceBundle.getString("DataLoadController_select_data_file.title"));
				fileChooser.getExtensionFilters().add(new ExtensionFilter(resourceBundle.getString("DataLoadController_excel_file.title"), "*.xls"));
				String userDirName = System.getProperty("user.dir");
				File userDir = new File(userDirName);
				if(!userDir.exists()){
					userDir = new File("test").getParentFile();
				}
				fileChooser.setInitialDirectory(userDir);// working directory
				File selectedFile = fileChooser.showOpenDialog(dialog);
				if(selectedFile!=null){
					try {
						FileInputStream dataStream = FileUtils.openInputStream(selectedFile);
						workbook = new HSSFWorkbook(dataStream);
					}catch(IOException ioe){
						throw new IllegalStateException(ioe);
					}
					openProgressbar();
					companyLoader.setWorkbook(workbook).setProgressLabel(progressLabel1).setProgressText(resourceBundle.getString("DataLoadController_Loading_Companies.title")).start();
				}
			}
		});
		
		errorMessageDialog.getOkButton().setOnAction(
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						errorMessageDialog.getDetailText().setText("");
						errorMessageDialog.closeDialog();
					}
				});
		errorMessageDialog.getTitleText().setText(
				resourceBundle.getString("Entity_create_error.title"));
		
		companyLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});
		
		companyLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				CompanyLoader s = (CompanyLoader) event.getSource();
				List<Company> companies = s.getValue();
				s.reset();
				event.consume();
				dataMap.setCompanies(companies);
				pi.setProgress(0.1);
				// Agency Loader
				agencyLoader.setDataMap(dataMap).setWorkbook(workbook).setProgressLabel(progressLabel1).setProgressText(resourceBundle.getString("DataLoadController_Loading_Agencies.title")).start();
			}});

		agencyLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				AgencyLoader s = (AgencyLoader) event.getSource();
				List<Agency> agencies = s.getValue();
				s.reset();
				event.consume();
				dataMap.setAgencies(agencies);
				// Section Loader
				pi.setProgress(0.2);
				loginLoader.setDataMap(dataMap).setWorkbook(workbook).setProgressLabel(progressLabel1).setProgressText(resourceBundle.getString("DataLoadController_Loading_Logins.title")).start();
				sectionLoader.setDataMap(dataMap).setWorkbook(workbook).setProgressLabel(progressLabel2).setProgressText(resourceBundle.getString("DataLoadController_Loading_Sections.title")).start();
				
			}});
		agencyLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});

		sectionLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				SectionLoader s = (SectionLoader) event.getSource();
				List<Section> sections = s.getValue();
				s.reset();
				event.consume();
				dataMap.setSections(sections);
				pi.setProgress(0.2);
				// Product Family Loader
				productFamilyLoader.setWorkbook(workbook).setProgressLabel(progressLabel2).setProgressText(resourceBundle.getString("DataLoadController_Loading_ProductFamilies.title")).start();
			}});
		sectionLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});

		loginLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				LoginLoader s = (LoginLoader) event.getSource();
				s.reset();
				event.consume();
				// Employers
				employerLoader.setWorkbook(workbook).setProgressLabel(progressLabel1).setProgressText(resourceBundle.getString("DataLoadController_Loading_Employers.title")).start();
			}});
		loginLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});
		
		employerLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				EmployerLoader s = (EmployerLoader) event.getSource();
				List<Employer> employers = s.getValue();
				s.reset();
				event.consume();
				dataMap.setEmployers(employers);
				// CustomerCategoryLoader
				customerCategoryLoader.setWorkbook(workbook).setProgressLabel(progressLabel1).setProgressText(resourceBundle.getString("DataLoadController_Loading_CustomerCategories.title")).start();
			}});
		employerLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});

		customerCategoryLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				CustomerCategoryLoader s = (CustomerCategoryLoader) event.getSource();
				List<CustomerCategory> customerCategories = s.getValue();
				s.reset();
				event.consume();
				dataMap.setCustomerCategories(customerCategories);
				// CustomerCategoryLoader
				customerLoader.setDataMap(dataMap).setWorkbook(workbook).setProgressLabel(progressLabel1).setProgressText(resourceBundle.getString("DataLoadController_Loading_Customers.title")).start();
			}});
		customerCategoryLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});

		customerLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				CustomerLoader s = (CustomerLoader) event.getSource();
//				List<Customer> customers = s.getValue();
				s.reset();
				event.consume();
//				dataMap.setCustomers(customerCategories);
				progressLabel1.setText(resourceBundle.getString("DataLoadController_Loading_Done.title"));
			}});
		customerLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});
		
		productFamilyLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				ProductFamilyLoader s = (ProductFamilyLoader) event.getSource();
				List<ProductFamily> productFamilies = s.getValue();
				s.reset();
				event.consume();
				dataMap.setProductFamilies(productFamilies);
				pi.setProgress(0.3);
				// VAT Loader
				vatLoader.setWorkbook(workbook).setProgressLabel(progressLabel2).setProgressText(resourceBundle.getString("DataLoadController_Loading_VAT.title")).start();
			}});
		productFamilyLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});

		vatLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				VATLoader s = (VATLoader) event.getSource();
				List<VAT> vats = s.getValue();
				s.reset();
				event.consume();
				dataMap.setVats(vats);
				// Article Loader
				articleLoader.setDataMap(dataMap).setWorkbook(workbook).setProgressLabel(progressLabel2).setProgressText(resourceBundle.getString("DataLoadController_Loading_Articles.title")).start();
			}});
		vatLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});

		articleLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				ArticleLoader s = (ArticleLoader) event.getSource();
				List<Article> articles = s.getValue();
				s.reset();
				event.consume();
				dataMap.setArticles(articles);
				pi.setProgress(0.4);
				// productDetailConfigLoader Loader
//				supplierLoader.setWorkbook(workbook).setProgressLabel(progressLabel2).setProgressText(resourceBundle.getString("DataLoadController_Loading_Suppliers.title")).start();

				productDetailConfigLoader.setWorkbook(workbook).setProgressLabel(progressLabel2).setProgressText(resourceBundle.getString("DataLoadController_Loading_ProductDetailConfig.title")).start();
			}});
		articleLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});

		productDetailConfigLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				ProductDetailConfigLoader s = (ProductDetailConfigLoader) event.getSource();
				List<ProductDetailConfig> pdc = s.getValue();
				s.reset();
				event.consume();
//				dataMap.setArticles(articles);
				pi.setProgress(0.4);
				// Supplier Loader
				supplierLoader.setWorkbook(workbook).setProgressLabel(progressLabel2).setProgressText(resourceBundle.getString("DataLoadController_Loading_Suppliers.title")).start();
			}});
		productDetailConfigLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});
		
		supplierLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				SupplierLoader s = (SupplierLoader) event.getSource();
				List<Supplier> suppliers = s.getValue();
				s.reset();
				event.consume();
				dataMap.setSuppliers(suppliers);
				// Currency Loader
				deliveryLoader.setDataMap(dataMap).setWorkbook(workbook).setProgressLabel(progressLabel2).setProgressText(resourceBundle.getString("DataLoadController_Loading_Deliveries.title")).start();

//				currencyLoader.setWorkbook(workbook).setProgressLabel(progressLabel2).setProgressText(resourceBundle.getString("DataLoadController_Loading_Currencies.title")).start();
			}});
		supplierLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});

		currencyLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				CurrencyLoader s = (CurrencyLoader) event.getSource();
				List<Currency> currencies = s.getValue();
				s.reset();
				event.consume();
				dataMap.setCurrencies(currencies);
				pi.setProgress(0.75);
				// Delivery Loader
				deliveryLoader.setDataMap(dataMap).setWorkbook(workbook).setProgressLabel(progressLabel2).setProgressText(resourceBundle.getString("DataLoadController_Loading_Deliveries.title")).start();
			}});
		currencyLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});
	
		deliveryLoader.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				DeliveryLoader s = (DeliveryLoader) event.getSource();
				List<Delivery> deliveries = s.getValue();
				s.reset();
				event.consume();
				pi.setProgress(1);
				closeProgressbar();
				dataMap.setDeliveries(deliveries);
				progressLabel2.setText(resourceBundle.getString("DataLoadController_Loading_Done.title"));
			}});
		deliveryLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});
	}

	public void handleRolesEvent(@Observes(notifyObserver=Reception.ALWAYS) @RolesEvent Set<String> roles){
		if(roles.contains(AccessRoleEnum.MANAGER.name())){
			loaderMenuItemAddEvent.fire(new DataMenuItem(loaderMenuItem));
		} else {
			loaderMenuItemRemoveEvent.fire(new DataMenuItem(loaderMenuItem));
		}
	}

	private void handleFailure(WorkerStateEvent event) {
		Service<?> s = (Service<?>) event.getSource();
		Throwable exception = s.getException();
		Dialogs.create().showException(exception);
		s.reset();
		event.consume();
		String message = exception.getMessage();

		if (!StringUtils.isBlank(message)) {
			Text detailText = errorMessageDialog
					.getDetailText();
			String text = detailText.getText();
			if (StringUtils.isBlank(text)) {
				text = message;
			} else {
				text = text + "\n" + message;
			}
			errorMessageDialog.getDetailText()
					.setText(text);
		}

		closeProgressbar();
		errorMessageDialog.display();
	}
	
	private void openProgressbar(){
		VBox vBox = new VBox();
		vBox.getChildren().add(pi);
		pi.setPrefWidth(100);
		pi.setPrefHeight(100);
		vBox.add(progressLabel1);
		vBox.add(progressLabel2);
		// Stage
		Scene scene = new Scene(vBox);
		scene.getStylesheets().add("/styles/application.css");
		dialog.setScene(scene);
		dialog.setWidth(500);
		dialog.setHeight(170);
		dialog.show();
	}
	private void closeProgressbar(){
		dialog.close();
	}
}
