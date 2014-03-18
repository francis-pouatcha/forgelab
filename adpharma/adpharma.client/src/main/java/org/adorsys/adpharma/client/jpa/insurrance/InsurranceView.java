package org.adorsys.adpharma.client.jpa.insurrance;

import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import java.util.Calendar;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import java.math.BigDecimal;

import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldValidator;
import org.adorsys.javafx.crud.extensions.validation.CalendarTextFieldFoccusChangedListener;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;

import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;

public class InsurranceView extends AbstractForm<Insurrance>
{

   private BigDecimalField coverageRate;

   private CalendarTextField beginDate;

   private CalendarTextField endDate;

   @Inject
   private InsurranceCustomerForm insurranceCustomerForm;
   @Inject
   private InsurranceCustomerSelection insurranceCustomerSelection;

   @Inject
   private InsurranceInsurerForm insurranceInsurerForm;
   @Inject
   private InsurranceInsurerSelection insurranceInsurerSelection;

   @Inject
   @Bundle({ CrudKeys.class, Insurrance.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @Inject
   private CalendarTextFieldValidator calendarTextFieldValidator;
   @Inject
   private ToOneAggreggationFieldValidator toOneAggreggationFieldValidator;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      coverageRate = viewBuilder.addBigDecimalField("Insurrance_coverageRate_description.title", "coverageRate", resourceBundle, NumberType.PERCENTAGE, locale);
      beginDate = viewBuilder.addCalendarTextField("Insurrance_beginDate_description.title", "beginDate", resourceBundle, "dd-MM-yyyy", locale);
      endDate = viewBuilder.addCalendarTextField("Insurrance_endDate_description.title", "endDate", resourceBundle, "dd-MM-yyyy", locale);
      viewBuilder.addTitlePane("Insurrance_customer_description.title", resourceBundle);
      viewBuilder.addSubForm("Insurrance_customer_description.title", "customer", resourceBundle, insurranceCustomerForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Insurrance_customer_description.title", "customer", resourceBundle, insurranceCustomerSelection, ViewModel.READ_WRITE);
      viewBuilder.addTitlePane("Insurrance_insurer_description.title", resourceBundle);
      viewBuilder.addSubForm("Insurrance_insurer_description.title", "insurer", resourceBundle, insurranceInsurerForm, ViewModel.READ_ONLY);
      viewBuilder.addSubForm("Insurrance_insurer_description.title", "insurer", resourceBundle, insurranceInsurerSelection, ViewModel.READ_WRITE);

      gridRows = viewBuilder.toRows();
   }

   public void addValidators()
   {
      beginDate.calendarProperty().addListener(new CalendarTextFieldFoccusChangedListener<Insurrance>(calendarTextFieldValidator, beginDate, Insurrance.class, "beginDate", resourceBundle));
      endDate.calendarProperty().addListener(new CalendarTextFieldFoccusChangedListener<Insurrance>(calendarTextFieldValidator, endDate, Insurrance.class, "endDate", resourceBundle));
      // no active validator
      // no active validator
   }

   public Set<ConstraintViolation<Insurrance>> validate(Insurrance model)
   {
      Set<ConstraintViolation<Insurrance>> violations = new HashSet<ConstraintViolation<Insurrance>>();
      violations.addAll(calendarTextFieldValidator.validate(beginDate, Insurrance.class, "beginDate", resourceBundle));
      violations.addAll(calendarTextFieldValidator.validate(endDate, Insurrance.class, "endDate", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(insurranceCustomerSelection.getCustomer(), model.getCustomer(), Insurrance.class, "customer", resourceBundle));
      violations.addAll(toOneAggreggationFieldValidator.validate(insurranceInsurerSelection.getInsurer(), model.getInsurer(), Insurrance.class, "insurer", resourceBundle));
      return violations;
   }

   public void bind(Insurrance model)
   {
      coverageRate.numberProperty().bindBidirectional(model.coverageRateProperty());
      beginDate.calendarProperty().bindBidirectional(model.beginDateProperty());
      endDate.calendarProperty().bindBidirectional(model.endDateProperty());
      insurranceCustomerForm.bind(model);
      insurranceCustomerSelection.bind(model);
      insurranceInsurerForm.bind(model);
      insurranceInsurerSelection.bind(model);
   }

   public BigDecimalField getCoverageRate()
   {
      return coverageRate;
   }

   public CalendarTextField getBeginDate()
   {
      return beginDate;
   }

   public CalendarTextField getEndDate()
   {
      return endDate;
   }

   public InsurranceCustomerForm getInsurranceCustomerForm()
   {
      return insurranceCustomerForm;
   }

   public InsurranceCustomerSelection getInsurranceCustomerSelection()
   {
      return insurranceCustomerSelection;
   }

   public InsurranceInsurerForm getInsurranceInsurerForm()
   {
      return insurranceInsurerForm;
   }

   public InsurranceInsurerSelection getInsurranceInsurerSelection()
   {
      return insurranceInsurerSelection;
   }
}
