package org.adorsys.adpharma.client.jpa.articlelot;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.article.ArticleAgency;
import org.adorsys.adpharma.client.jpa.article.ArticleSearchInput;
import org.adorsys.adpharma.client.jpa.article.ArticleSection;
import org.adorsys.adpharma.client.jpa.article.StockValueArticleSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoicePrintTemplate;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

@Singleton
public class StockValueSearchView extends AbstractForm<StockValueArticleSearchInput>{
	private ComboBox<ArticleAgency> agency;
	private ComboBox<ArticleSection> section;
	private CheckBox groupByArticle;
	private CheckBox stockValueRepport;
	private CheckBox breakArticleRepport;
	private CheckBox beloThresholdArticleRepport;
	
	

	@Inject
	@Bundle({ CrudKeys.class, CustomerInvoicePrintTemplate.class ,Article.class})
	private ResourceBundle resourceBundle;

	@Inject
	private Locale locale;

	@Inject
	private CalendarTextFieldValidator calendarTextFieldValidator ;

	@Inject
	private ToOneAggreggationFieldValidator aggreggationFieldValidator ;




	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();
		agency = viewBuilder.addComboBox("Article_agency_description.title", "agency", resourceBundle);
		section = viewBuilder.addComboBox("Article_section_description.title", "section", resourceBundle);
		groupByArticle = viewBuilder.addCheckBox("Entity_empty.text", "groupByArticle", resourceBundle);
		groupByArticle.setText(resourceBundle.getString("Article_groupByCip_description.title"));
		
		stockValueRepport = viewBuilder.addCheckBox("Entity_empty.text", "stockValueRepport", resourceBundle);
		stockValueRepport.setText("Valorisation du stock");
		
		breakArticleRepport = viewBuilder.addCheckBox("Entity_empty.text", "breakArticleRepport", resourceBundle);
		breakArticleRepport.setText("Articles en rupture");
		
		beloThresholdArticleRepport = viewBuilder.addCheckBox("Entity_empty.text", "beloThresholdArticleRepport", resourceBundle);
		beloThresholdArticleRepport.setText("Articles à seuil critique");
        gridRows = viewBuilder.toRows();
	}

	public void addValidators()
	{
		//		loginName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Login>(textInputControlValidator, loginName, Login.class, "loginName", resourceBundle));
		//		fullName.focusedProperty().addListener(new TextInputControlFoccusChangedListener<Login>(textInputControlValidator, fullName, Login.class, "fullName", resourceBundle));
	}

	public Set<ConstraintViolation<StockValueArticleSearchInput>> validate(StockValueArticleSearchInput model)
	{
		Set<ConstraintViolation<StockValueArticleSearchInput>> violations = new HashSet<ConstraintViolation<StockValueArticleSearchInput>>();

		return violations;
	}
	
	public void bindStockValues(StockValueArticleSearchInput model) {
		
	}
	
	@Override
	public void bind(StockValueArticleSearchInput model) {
		agency.valueProperty().bindBidirectional(model.getEntity().agencyProperty());
		section.valueProperty().bindBidirectional(model.sectionProperty());
		groupByArticle.selectedProperty().bindBidirectional(model.groupByArticleProperty());
		stockValueRepport.selectedProperty().bindBidirectional(model.stockValueRepportProperty());
		breakArticleRepport.selectedProperty().bindBidirectional(model.breakArticleRepportProperty());
		beloThresholdArticleRepport.selectedProperty().bindBidirectional(model.beloThresholdArticleRepportProperty());
		
	}

	public ComboBox<ArticleAgency> getAgency() {
		return agency;
	}

	public void setAgency(ComboBox<ArticleAgency> agency) {
		this.agency = agency;
	}

	public ComboBox<ArticleSection> getSection() {
		return section;
	}

	public void setSection(ComboBox<ArticleSection> section) {
		this.section = section;
	}

	public CheckBox getGroupByArticle() {
		return groupByArticle;
	}

	public void setGroupByArticle(CheckBox groupByArticle) {
		this.groupByArticle = groupByArticle;
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

	public CalendarTextFieldValidator getCalendarTextFieldValidator() {
		return calendarTextFieldValidator;
	}

	public void setCalendarTextFieldValidator(
			CalendarTextFieldValidator calendarTextFieldValidator) {
		this.calendarTextFieldValidator = calendarTextFieldValidator;
	}

	public ToOneAggreggationFieldValidator getAggreggationFieldValidator() {
		return aggreggationFieldValidator;
	}

	public void setAggreggationFieldValidator(
			ToOneAggreggationFieldValidator aggreggationFieldValidator) {
		this.aggreggationFieldValidator = aggreggationFieldValidator;
	}
	
	public CheckBox getBeloThresholdArticleRepport() {
		return beloThresholdArticleRepport;
	}
	
	public CheckBox getBreakArticleRepport() {
		return breakArticleRepport;
	}
	
	public CheckBox getStockValueRepport() {
		return stockValueRepport;
	}


	
}
