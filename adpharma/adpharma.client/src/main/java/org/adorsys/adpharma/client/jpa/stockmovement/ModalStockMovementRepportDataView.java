package org.adorsys.adpharma.client.jpa.stockmovement;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.validation.ConstraintViolation;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.article.Article;
import org.adorsys.adpharma.client.jpa.articlelot.ArticleLot;
import org.adorsys.adpharma.client.jpa.salesorder.PeriodicalDataSearchInput;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldValidator;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;
@Singleton
public class ModalStockMovementRepportDataView extends ApplicationModal{

	private AnchorPane rootPane;

	private CalendarTextField beginDate;

	private CalendarTextField endDate;

	private TextField pic ;
	
	private TextField internalPic ;
	
	
	private ComboBox<Article> article;
	
	private Button saveButton;

	private Button resetButton;
	
	private Button clearButton;

	@Inject
	private Locale locale ;

	@Inject
	private CalendarTextFieldValidator calendarControlValidator;

	@Inject
	@Bundle({ CrudKeys.class, SalesOrder.class,Article.class,ArticleLot.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder lazyviewBuilder = new LazyViewBuilder();
		beginDate = lazyviewBuilder.addCalendarTextField("SalesOrder_repport_fromDate_description.title", "internalPic", resourceBundle,"dd-MM-yyyy HH:mm",locale);
		endDate = lazyviewBuilder.addCalendarTextField("SalesOrder_repport_toDate_description.title", "internalPic", resourceBundle,"dd-MM-yyyy HH:mm",locale);
		pic = lazyviewBuilder.addTextField("Article_pic_description.title", "pic", resourceBundle) ;
		internalPic = lazyviewBuilder.addTextField("ArticleLot_internalPic_description.title", "pic", resourceBundle) ;
		
		article = lazyviewBuilder.addComboBox("Article_articleName_description.title", "article", resourceBundle);
		article.setPrefHeight(40d);
		article.setPrefWidth(300d);
		
		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lazyviewBuilder.toRows(), ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		resetButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		clearButton = viewBuilder.addButton(buttonBar, "Entity_clear.title", "clearButton", resourceBundle, AwesomeIcon.ERASER);
		rootPane = viewBuilder.toAnchorPane();
		rootPane.setPrefWidth(500);
	}



	public void bind(PeriodicalDataSearchInput model)
	{
		beginDate.calendarProperty().bindBidirectional(model.beginDateProperty());
		endDate.calendarProperty().bindBidirectional(model.endDateProperty());
		pic.textProperty().bindBidirectional(model.picProperty());
		article.valueProperty().bindBidirectional(model.articleProperty());
		internalPic.textProperty().bindBidirectional(model.internalPicProperty());
		
	}

	public void addValidators()
	{
		// no active validator
		// no active validator
	}

	public Set<ConstraintViolation<PeriodicalDataSearchInput>> validate(PeriodicalDataSearchInput model)
	{
		Set<ConstraintViolation<PeriodicalDataSearchInput>> violations = new HashSet<ConstraintViolation<PeriodicalDataSearchInput>>();
		violations.addAll(calendarControlValidator.validate(beginDate, PeriodicalDataSearchInput.class, "beginDate", resourceBundle));
		violations.addAll(calendarControlValidator.validate(endDate, PeriodicalDataSearchInput.class, "endDate", resourceBundle));
		return violations;
	}

	@Override
	public Pane getRootPane() {
		return rootPane;
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public Button getResetButton() {
		return resetButton;
	}

	public Locale getLocale() {
		return locale;
	}

	public ComboBox<Article> getArticle() {
		return article;
	}


	public CalendarTextField getBeginDate() {
		return beginDate;
	}



	public CalendarTextField getEndDate() {
		return endDate;
	}


   public Button getClearButton() {
	return clearButton;
  }
	


	public CalendarTextFieldValidator getCalendarControlValidator() {
		return calendarControlValidator;
	}



	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}



}
