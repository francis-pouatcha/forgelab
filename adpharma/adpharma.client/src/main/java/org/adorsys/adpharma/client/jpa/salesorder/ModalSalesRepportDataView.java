package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldValidator;
import org.adorsys.javafx.crud.extensions.view.ApplicationModal;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;
@Singleton
public class ModalSalesRepportDataView extends ApplicationModal{

	private AnchorPane rootPane;

	private CalendarTextField beginDate;

	private CalendarTextField endDate;

	private CheckBox check;
	
	private TextField articleName ;
	
	private CheckBox printXls;
	
	private CheckBox taxableSalesOnly;
	
	private CheckBox nonTaxableSalesOnly;
	
	private CheckBox twentyOverHeightySalesOnly;
	
	private CheckBox twentyOverHeightyInQty;
	
	private CheckBox perVendorAndDiscount;
	
	private TextField pic ;

	private Button saveButton;

	private Button resetButton;

	@Inject
	private Locale locale ;

	@Inject
	private CalendarTextFieldValidator calendarControlValidator;

	@Inject
	@Bundle({ CrudKeys.class, SalesOrder.class,Article.class })
	private ResourceBundle resourceBundle;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder lazyviewBuilder = new LazyViewBuilder();
		beginDate = lazyviewBuilder.addCalendarTextField("SalesOrder_repport_fromDate_description.title", "internalPic", resourceBundle,"dd-MM-yyyy HH:mm",locale);
		endDate = lazyviewBuilder.addCalendarTextField("SalesOrder_repport_toDate_description.title", "internalPic", resourceBundle,"dd-MM-yyyy HH:mm",locale);
		
		pic = lazyviewBuilder.addTextField("Article_pic_description.title", "pic", resourceBundle) ;
		articleName = lazyviewBuilder.addTextField("Article_articleName_description.title", "pic", resourceBundle) ;
		
		taxableSalesOnly = lazyviewBuilder.addCheckBox("Entity_empty.text", "taxableSalesOnly", resourceBundle);
		taxableSalesOnly.setText("Taxable Uniquement ?");
		
		nonTaxableSalesOnly = lazyviewBuilder.addCheckBox("Entity_empty.text", "nonTaxableSalesOnly", resourceBundle);
		nonTaxableSalesOnly.setText("Non Taxable Uniquement ?");
		
		twentyOverHeightySalesOnly = lazyviewBuilder.addCheckBox("Entity_empty.text", "twentyOverHeightySalesOnly", resourceBundle);
		twentyOverHeightySalesOnly.setText("20/80 Uniquement ?");
		
		twentyOverHeightyInQty = lazyviewBuilder.addCheckBox("Entity_empty.text", "twentyOverHeightyInQty", resourceBundle);
		twentyOverHeightyInQty.setText("20/80 en Quantite?");
		
		check = lazyviewBuilder.addCheckBox("Entity_empty.text", "check", resourceBundle);
		check.setText("Resultat Groupé Par cip ?");
		
		perVendorAndDiscount = lazyviewBuilder.addCheckBox("Entity_empty.text", "perVendorAndDiscount", resourceBundle);
		perVendorAndDiscount.setText("Remises par vendeurs");
		
		printXls= lazyviewBuilder.addCheckBox("Entity_empty.text", "printXls", resourceBundle);
		printXls.setText("Imprimer en Excel");
		
		

		ViewBuilder viewBuilder = new ViewBuilder();
		viewBuilder.addRows(lazyviewBuilder.toRows(), ViewType.CREATE, false);
		viewBuilder.addSeparator();
		HBox buttonBar = viewBuilder.addButtonBar();
		saveButton = viewBuilder.addButton(buttonBar, "Entity_save.title", "saveButton", resourceBundle, AwesomeIcon.SAVE);
		resetButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "resetButton", resourceBundle, AwesomeIcon.REFRESH);
		rootPane = viewBuilder.toAnchorPane();
		rootPane.setPrefWidth(500);
	}



	public void bind(PeriodicalDataSearchInput model)
	{
		beginDate.calendarProperty().bindBidirectional(model.beginDateProperty());
		endDate.calendarProperty().bindBidirectional(model.endDateProperty());
		check.selectedProperty().bindBidirectional(model.checkProperty());
		pic.textProperty().bindBidirectional(model.picProperty());
		articleName.textProperty().bindBidirectional(model.articleNameProperty());
		taxableSalesOnly.selectedProperty().bindBidirectional(model.taxableSalesOnlyProperty());
		nonTaxableSalesOnly.selectedProperty().bindBidirectional(model.nonTaxableSalesOnlyProperty());
		twentyOverHeightySalesOnly.selectedProperty().bindBidirectional(model.twentyOverHeightySalesOnlyProperty());
		twentyOverHeightyInQty.selectedProperty().bindBidirectional(model.twentyOverHeightyInQtyProperty());
		perVendorAndDiscount.selectedProperty().bindBidirectional(model.perVendorAndDiscountProperty());
		pic.textProperty().bindBidirectional(model.picProperty());
		printXls.selectedProperty().bindBidirectional(model.printXlsProperty());
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



	public CalendarTextField getBeginDate() {
		return beginDate;
	}



	public CalendarTextField getEndDate() {
		return endDate;
	}



	public CheckBox getCheck() {
		return check;
	}
	
	public CheckBox getPrintXls() {
		return printXls;
	}



	public CheckBox getTaxableSalesOnly() {
		return taxableSalesOnly;
	}



	public CheckBox getNonTaxableSalesOnly() {
		return nonTaxableSalesOnly;
	}



	public CheckBox getTwentyOverHeightySalesOnly() {
		return twentyOverHeightySalesOnly;
	}
	
	public CheckBox getTwentyOverHeightyInQty() {
		return twentyOverHeightyInQty;
	}



	public CalendarTextFieldValidator getCalendarControlValidator() {
		return calendarControlValidator;
	}

	public CheckBox getPerVendorAndDiscount() {
		return perVendorAndDiscount;
	}


	public ResourceBundle getResourceBundle() {
		return resourceBundle;
	}



}
