package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.List;
import java.util.ResourceBundle;

import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceCustomerForm;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceCustomerSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceInsurerForm;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceInsurerSelection;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.adpharma.client.jpa.insurrance.InsurranceCustomer;

import javafx.beans.property.ObjectProperty;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;

public class CustomerInvoiceInsuranceForm extends AbstractToOneAssociation<CustomerInvoice, Insurrance>
{

   private BigDecimalField coverageRate;

   private CalendarTextField beginDate;

   private CalendarTextField endDate;

   @Inject
   @Bundle({ CrudKeys.class, Insurrance.class })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      coverageRate = viewBuilder.addBigDecimalField("Insurrance_coverageRate_description.title", "coverageRate", resourceBundle, NumberType.PERCENTAGE, locale);
      beginDate = viewBuilder.addCalendarTextField("Insurrance_beginDate_description.title", "beginDate", resourceBundle, "dd-MM-yyyy", locale);
      endDate = viewBuilder.addCalendarTextField("Insurrance_endDate_description.title", "endDate", resourceBundle, "dd-MM-yyyy", locale);

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerInvoice model)
   {
      coverageRate.numberProperty().bindBidirectional(model.getInsurance().coverageRateProperty());
      beginDate.calendarProperty().bindBidirectional(model.getInsurance().beginDateProperty());
      endDate.calendarProperty().bindBidirectional(model.getInsurance().endDateProperty());
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
}
