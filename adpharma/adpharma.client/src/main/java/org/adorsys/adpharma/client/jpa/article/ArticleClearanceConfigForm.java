package org.adorsys.adpharma.client.jpa.article;

import java.util.List;
import java.util.ResourceBundle;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldFoccusChangedListener;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.BigDecimalFieldFoccusChangedListener;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.clearanceconfig.ClearanceConfig;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingState;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateConverter;
import org.adorsys.adpharma.client.jpa.documentprocessingstate.DocumentProcessingStateListCellFatory;

public class ArticleClearanceConfigForm extends AbstractToOneAssociation<Article, ClearanceConfig>
{

	private CheckBox active;

	private ComboBox<DocumentProcessingState> clearanceState;

	private BigDecimalField discountRate;

	private CalendarTextField startDate;

	private CalendarTextField endDate;

	@Inject
	@Bundle({ CrudKeys.class, ClearanceConfig.class })
	private ResourceBundle resourceBundle;

	@Inject
	@Bundle(DocumentProcessingState.class)
	private ResourceBundle clearanceStateBundle;

	@Inject
	private DocumentProcessingStateConverter clearanceStateConverter;

	@Inject
	private DocumentProcessingStateListCellFatory clearanceStateListCellFatory;

	@Inject
	private Locale locale;

	@PostConstruct
	public void postConstruct()
	{
		LazyViewBuilder viewBuilder = new LazyViewBuilder();
		active = viewBuilder.addCheckBox("ClearanceConfig_active_description.title", "active", resourceBundle);
		clearanceState = viewBuilder.addComboBox("ClearanceConfig_clearanceState_description.title", "clearanceState", resourceBundle, DocumentProcessingState.values());
		discountRate = viewBuilder.addBigDecimalField("ClearanceConfig_discountRate_description.title", "discountRate", resourceBundle, NumberType.PERCENTAGE, locale);
		startDate = viewBuilder.addCalendarTextField("ClearanceConfig_startDate_description.title", "startDate", resourceBundle, "dd-MM-yyyy", locale);
		endDate = viewBuilder.addCalendarTextField("ClearanceConfig_endDate_description.title", "endDate", resourceBundle, "dd-MM-yyyy", locale);

		ComboBoxInitializer.initialize(clearanceState, clearanceStateConverter, clearanceStateListCellFatory, clearanceStateBundle);

		gridRows = viewBuilder.toRows();
	}

	public void bind(Article model)
	{
		active.textProperty().bindBidirectional(model.getClearanceConfig().activeProperty(), new BooleanStringConverter());
		clearanceState.valueProperty().bindBidirectional(model.getClearanceConfig().clearanceStateProperty());
		discountRate.numberProperty().bindBidirectional(model.getClearanceConfig().discountRateProperty());
		startDate.calendarProperty().bindBidirectional(model.getClearanceConfig().startDateProperty());
		endDate.calendarProperty().bindBidirectional(model.getClearanceConfig().endDateProperty());
	}

	public void update(ArticleClearanceConfig data)
	{
		if(data!=null){
			active.textProperty().set(new BooleanStringConverter().toString(data.activeProperty().get()));
			clearanceState.valueProperty().set(data.clearanceStateProperty().get());
			discountRate.numberProperty().set(data.discountRateProperty().get());
			startDate.calendarProperty().set(data.startDateProperty().get());
			endDate.calendarProperty().set(data.endDateProperty().get());
		}
	}

	public CheckBox getActive()
	{
		return active;
	}

	public ComboBox<DocumentProcessingState> getClearanceState()
	{
		return clearanceState;
	}

	public BigDecimalField getDiscountRate()
	{
		return discountRate;
	}

	public CalendarTextField getStartDate()
	{
		return startDate;
	}

	public CalendarTextField getEndDate()
	{
		return endDate;
	}
}
