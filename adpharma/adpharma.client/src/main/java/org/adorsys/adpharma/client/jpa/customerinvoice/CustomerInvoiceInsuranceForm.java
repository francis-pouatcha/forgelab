package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import jfxtras.scene.control.CalendarTextField;

import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractToOneAssociation;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

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

   public void update(CustomerInvoiceInsurance data)
   {
      coverageRate.numberProperty().set(data.coverageRateProperty().get());
      beginDate.calendarProperty().set(data.beginDateProperty().get());
      endDate.calendarProperty().set(data.endDateProperty().get());
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
