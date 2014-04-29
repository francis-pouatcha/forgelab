package org.adorsys.adpharma.client.jpa.payment;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customerinvoice.CustomerInvoice;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

public class PaymentInvoicesSelection extends AbstractSelection<Payment, CustomerInvoice>
{

   /*
    * Dialog activation.
    */
   private Button selectButton;

   /*
    * Selection popup. This popup offers two lists. One for the display of the list of actual assocciation entities and
    * one for the display of the list of target entities.
    */
   private VBox rootNode;
   private Button closeButton;
   private Button addButton;
   private Button removeButton;

   private TableView<CustomerInvoice> targetDataList;
   private Pagination targetPagination;

   private TableView<CustomerInvoice> assocDataList;
   private Pagination assocPagination;

   @Inject
   @Bundle({ CrudKeys.class
         , Payment.class
         , CustomerInvoice.class
         , Customer.class
         , Login.class
         , Agency.class
         , SalesOrder.class
   })
   private ResourceBundle resourceBundle;

   @Inject
   private InvoiceTypeConverter invoiceTypeConverter;

   Stage dialog;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder lazyViewBuilder = new LazyViewBuilder();
      selectButton = lazyViewBuilder.addButton(
            "Payment_invoices_description.title", "Entity_select.title",
            "selectButton", resourceBundle);
      gridRows = lazyViewBuilder.toRows();

      ViewBuilder viewBuilder = new ViewBuilder();
      viewBuilder.addTitlePane("Payment_invoices_description.title", resourceBundle);
      targetDataList = viewBuilder.addTable("targetDataList");
      viewBuilder.addEnumColumn(targetDataList, "invoiceType", "CustomerInvoice_invoiceType_description.title", resourceBundle, invoiceTypeConverter);
      viewBuilder.addStringColumn(targetDataList, "invoiceNumber", "CustomerInvoice_invoiceNumber_description.title", resourceBundle);
      viewBuilder.addDateColumn(targetDataList, "creationDate", "CustomerInvoice_creationDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addStringColumn(targetDataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(targetDataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(targetDataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(targetDataList, "fullName", "Login_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(targetDataList, "name", "Agency_name_description.title", resourceBundle);
      viewBuilder.addStringColumn(targetDataList, "soNumber", "SalesOrder_soNumber_description.title", resourceBundle);
      // Field not displayed in table
      viewBuilder.addBigDecimalColumn(targetDataList, "amountBeforeTax", "CustomerInvoice_amountBeforeTax_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(targetDataList, "taxAmount", "CustomerInvoice_taxAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(targetDataList, "amountDiscount", "CustomerInvoice_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(targetDataList, "amountAfterTax", "CustomerInvoice_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(targetDataList, "netToPay", "CustomerInvoice_netToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(targetDataList, "customerRestTopay", "CustomerInvoice_customerRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(targetDataList, "insurranceRestTopay", "CustomerInvoice_insurranceRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      // Field not displayed in table
      viewBuilder.addBigDecimalColumn(targetDataList, "advancePayment", "CustomerInvoice_advancePayment_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(targetDataList, "totalRestToPay", "CustomerInvoice_totalRestToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      targetPagination = viewBuilder.addPagination();
      viewBuilder.addSeparator();
      HBox selectionButtonBar = viewBuilder.addButtonBar();
      addButton = viewBuilder.addButton(selectionButtonBar,
            "Entity_add.title", "addButton", resourceBundle);
      removeButton = viewBuilder.addButton(selectionButtonBar,
            "Entity_remove.title", "removeButton", resourceBundle);
      assocDataList = viewBuilder.addTable("assocDataList");
      viewBuilder.addEnumColumn(assocDataList, "invoiceType", "CustomerInvoice_invoiceType_description.title", resourceBundle, invoiceTypeConverter);
      viewBuilder.addStringColumn(assocDataList, "invoiceNumber", "CustomerInvoice_invoiceNumber_description.title", resourceBundle);
      viewBuilder.addDateColumn(assocDataList, "creationDate", "CustomerInvoice_creationDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addStringColumn(assocDataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(assocDataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(assocDataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(assocDataList, "fullName", "Login_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(assocDataList, "name", "Agency_name_description.title", resourceBundle);
      viewBuilder.addStringColumn(assocDataList, "soNumber", "SalesOrder_soNumber_description.title", resourceBundle);
      // Field not displayed in table
      viewBuilder.addBigDecimalColumn(assocDataList, "amountBeforeTax", "CustomerInvoice_amountBeforeTax_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(assocDataList, "taxAmount", "CustomerInvoice_taxAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(assocDataList, "amountDiscount", "CustomerInvoice_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(assocDataList, "amountAfterTax", "CustomerInvoice_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(assocDataList, "netToPay", "CustomerInvoice_netToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(assocDataList, "customerRestTopay", "CustomerInvoice_customerRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(assocDataList, "insurranceRestTopay", "CustomerInvoice_insurranceRestTopay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      // Field not displayed in table
      viewBuilder.addBigDecimalColumn(assocDataList, "advancePayment", "CustomerInvoice_advancePayment_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(assocDataList, "totalRestToPay", "CustomerInvoice_totalRestToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      assocPagination = viewBuilder.addPagination();
      HBox buttonBar = viewBuilder.addButtonBar();
      closeButton = viewBuilder.addButton(buttonBar, "Window_close.title", "closeButton", resourceBundle);
      rootNode = new VBox();
      rootNode.getChildren().add(viewBuilder.toAnchorPane());
   }

   public void closeDialog()
   {
      if (dialog != null)
         dialog.close();
   }

   public void display()
   {
      if (dialog == null)
      {
         dialog = new Stage();
         dialog.initModality(Modality.WINDOW_MODAL);
         // Stage
         Scene scene = new Scene(rootNode);
         scene.getStylesheets().add("/styles/application.css");
         dialog.setScene(scene);
         dialog.setTitle(resourceBundle.getString("Payment_invoices_description.title"));
      }
      dialog.show();
   }

   public void bind(Payment model)
   {
   }

   public Button getAddButton()
   {
      return addButton;
   }

   public Button getRemoveButton()
   {
      return removeButton;
   }

   public Button getSelectButton()
   {
      return selectButton;
   }

   public Button getCloseButton()
   {
      return closeButton;
   }

   public TableView<CustomerInvoice> getTargetDataList()
   {
      return targetDataList;
   }

   public TableView<CustomerInvoice> getAssocDataList()
   {
      return assocDataList;
   }

   public Pagination getTargetPagination()
   {
      return targetPagination;
   }

   public Pagination getAssocPagination()
   {
      return assocPagination;
   }
}
