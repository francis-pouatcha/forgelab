package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractToManyAssociation;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import org.adorsys.javafx.crud.extensions.view.ComboBoxInitializer;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.Locale;
import jfxtras.scene.control.CalendarTextField;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCustomerForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCustomerSelection;
import org.adorsys.javafx.crud.extensions.ViewModel;
import org.adorsys.javafx.crud.extensions.validation.ToOneAggreggationFieldValidator;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInsuranceForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInsuranceSelection;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCreatingUserForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCreatingUserSelection;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceAgencyForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceAgencySelection;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSalesOrderForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSalesOrderSelection;
import javafx.scene.control.CheckBox;
import javafx.util.converter.BooleanStringConverter;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.control.BigDecimalField;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInvoiceItemsForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInvoiceItemsSelection;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoicePaymentsForm;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCustomer;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceInsurance;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceCreatingUser;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceAgency;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoiceSalesOrder;

import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.debtstatement.DebtStatement;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.insurrance.Insurrance;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;

public class DebtStatementInvoicesForm extends AbstractToManyAssociation<DebtStatement, CustomerInvoice>
{

   private TableView<CustomerInvoice> dataList;
   private Pagination pagination;

   @Inject
   private InvoiceTypeConverter invoiceTypeConverter;

   @Inject
   @Bundle({ CrudKeys.class
         , CustomerInvoice.class
         , DebtStatement.class
         , Customer.class
         , Insurrance.class
         , Login.class
         , Agency.class
         , SalesOrder.class
   })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addEnumColumn(dataList, "invoiceType", "CustomerInvoice_invoiceType_description.title", resourceBundle, invoiceTypeConverter);
      viewBuilder.addStringColumn(dataList, "invoiceNumber", "CustomerInvoice_invoiceNumber_description.title", resourceBundle);
      viewBuilder.addDateColumn(dataList, "creationDate", "CustomerInvoice_creationDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "fullName", "Login_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "name", "Agency_name_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "soNumber", "SalesOrder_soNumber_description.title", resourceBundle);
      // Field not displayed in table
      viewBuilder.addBigDecimalColumn(dataList, "amountBeforeTax", "CustomerInvoice_amountBeforeTax_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "amountVAT", "CustomerInvoice_amountVAT_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "amountDiscount", "CustomerInvoice_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "amountAfterTax", "CustomerInvoice_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "netToPay", "CustomerInvoice_netToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "customerRestTopay", "CustomerInvoice_customerRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "insurranceRestTopay", "CustomerInvoice_insurranceRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      // Field not displayed in table
      viewBuilder.addBigDecimalColumn(dataList, "advancePayment", "CustomerInvoice_advancePayment_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalRestToPay", "CustomerInvoice_totalRestToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      pagination = viewBuilder.addPagination();

      gridRows = viewBuilder.toRows();
   }

   public void bind(DebtStatement model)
   {
   }

   public TableView<CustomerInvoice> getDataList()
   {
      return dataList;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
