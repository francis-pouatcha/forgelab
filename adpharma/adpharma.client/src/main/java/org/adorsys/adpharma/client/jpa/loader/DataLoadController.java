package org.adorsys.adpharma.client.jpa.loader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.concurrent.Service;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.currency.Currency;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.employer.Employer;
import org.adorsys.adpharma.client.jpa.productfamily.ProductFamily;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.adpharma.client.jpa.vat.VAT;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.login.LoginSucceededEvent;
import org.adorsys.javafx.crud.extensions.view.ErrorMessageDialog;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DataLoadController {

	@Inject
	private ErrorMessageDialog errorMessageDialog;

	@Inject
	@Bundle(CrudKeys.class)
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
	private SupplierLoader supplierLoader;
	
	@Inject
	private VATLoader vatLoader;
	
	@Inject
	private CurrencyLoader currencyLoader;
	
	@Inject
	private DeliveryLoader deliveryLoader;
	
	@Inject
	private LoaderCheck loaderCheck;
	
	@Inject
	private LoginLoader loginLoader;
	
	@Inject
	private EmployerLoader employerLoader;
	
	@Inject
	private CustomerCategoryLoader customerCategoryLoader;
	
	@Inject
	private CustomerLoader customerLoader;
	
	private HSSFWorkbook workbook;
	
	private DataMap dataMap = new DataMap();

	@PostConstruct
	public void postConstruct() {
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

		loaderCheck.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});
		
		loaderCheck.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent event) {
				LoaderCheck s = (LoaderCheck) event.getSource();
				Long value = s.getValue();
				s.reset();
				event.consume();
				if(value<=0l){
					try {
						Properties properties = new Properties();
						InputStream propStream = DataLoadController.class.getResourceAsStream(DataLoadController.class.getSimpleName()+".properties");
						if(propStream==null) return;
						properties.load(propStream);

						String dataFile = properties.getProperty("data_file");
						if(dataFile==null)return;
						InputStream dataStream = DataLoadController.class.getResourceAsStream(dataFile);
						if(dataStream==null) return;
						
						if(dataMap.getCompanies()!=null) return;
						workbook = new HSSFWorkbook(dataStream);
					}catch(IOException ioe){
						throw new IllegalStateException(ioe);
					}
					companyLoader.setWorkbook(workbook).start();
				}
			}});
		
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
				// Agency Loader
				agencyLoader.setDataMap(dataMap).setWorkbook(workbook).start();
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
				loginLoader.setDataMap(dataMap).setWorkbook(workbook).start();
				sectionLoader.setDataMap(dataMap).setWorkbook(workbook).start();
				
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
				// Product Family Loader
				productFamilyLoader.setWorkbook(workbook).start();
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
				employerLoader.setWorkbook(workbook).start();
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
				customerCategoryLoader.setWorkbook(workbook).start();
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
				customerLoader.setDataMap(dataMap).setWorkbook(workbook).start();
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
				// Article Loader
				articleLoader.setDataMap(dataMap).setWorkbook(workbook).start();
			}});
		productFamilyLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
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
				// Supplier Loader
				supplierLoader.setWorkbook(workbook).start();
			}});
		articleLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
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
				// VAT Loader
				vatLoader.setWorkbook(workbook).start();
			}});
		supplierLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
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
				// Currency Loader
				currencyLoader.setWorkbook(workbook).start();
			}});
		vatLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
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
				// Delivery Loader
				deliveryLoader.setDataMap(dataMap).setWorkbook(workbook).start();
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
				dataMap.setDeliveries(deliveries);
			}});
		deliveryLoader.setOnFailed(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				handleFailure(event);
			}
		});
	}

	public void handleLoginSucceededEvent(
			@Observes(notifyObserver = Reception.ALWAYS) @LoginSucceededEvent String loginName) throws IOException 
	{
		if(!"manager".equals(loginName)) return;
		
		loaderCheck.start();
	}

	private void handleFailure(WorkerStateEvent event) {
		Service<?> s = (Service<?>) event.getSource();
		Throwable exception = s.getException();
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

		errorMessageDialog.display();
	}
}
