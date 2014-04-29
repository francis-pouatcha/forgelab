package org.adorsys.adpharma.client.jpa.loader;

import java.util.List;

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

public class DataMap {

	private List<Company> companies;
	private List<Agency> agencies;
	private List<Section> sections;
	private List<ProductFamily> productFamilies;
	private List<Article> articles;
	private List<Supplier> suppliers;
	private List<VAT> vats;
	private List<Currency> currencies;
	private List<Delivery> deliveries;
	private List<Employer> employers;
	private List<CustomerCategory> customerCategories;

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public List<Agency> getAgencies() {
		return agencies;
	}

	public void setAgencies(List<Agency> agencies) {
		this.agencies = agencies;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public List<ProductFamily> getProductFamilies() {
		return productFamilies;
	}

	public void setProductFamilies(List<ProductFamily> productFamilies) {
		this.productFamilies = productFamilies;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public List<VAT> getVats() {
		return vats;
	}

	public void setVats(List<VAT> vats) {
		this.vats = vats;
	}

	public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}

	public List<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(List<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public List<Employer> getEmployers() {
		return employers;
	}

	public void setEmployers(List<Employer> employers) {
		this.employers = employers;
	}

	public List<CustomerCategory> getCustomerCategories() {
		return customerCategories;
	}

	public void setCustomerCategories(List<CustomerCategory> customerCategories) {
		this.customerCategories = customerCategories;
	}
}
