package org.adorsys.adpharma.client.jpa.supplierinvoice;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.delivery.Delivery;
import org.adorsys.adpharma.client.jpa.invoicetype.InvoiceTypeConverter;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.supplier.Supplier;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class SupplierInvoiceListView
{

   @FXML
   AnchorPane rootPane;

   @FXML
   private Button searchButton;

   @FXML
   private Button createButton;

   @FXML
   private TableView<SupplierInvoice> dataList;

   @Inject
   private Locale locale;

   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , SupplierInvoice.class
         , Supplier.class
         , Login.class
         , Agency.class
         , Delivery.class
   })
   private ResourceBundle resourceBundle;

   @Inject
   private InvoiceTypeConverter invoiceTypeConverter;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addEnumColumn(dataList, "invoiceType", "SupplierInvoice_invoiceType_description.title", resourceBundle, invoiceTypeConverter);
      viewBuilder.addStringColumn(dataList, "invoiceNumber", "SupplierInvoice_invoiceNumber_description.title", resourceBundle);
      viewBuilder.addDateColumn(dataList, "creationDate", "SupplierInvoice_creationDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addStringColumn(dataList, "supplier", "SupplierInvoice_supplier_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "fullName", "Login_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "agency", "SupplierInvoice_agency_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "delivery", "SupplierInvoice_delivery_description.title", resourceBundle);
      // Field not displayed in table
      viewBuilder.addBigDecimalColumn(dataList, "amountBeforeTax", "SupplierInvoice_amountBeforeTax_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addBigDecimalColumn(dataList, "taxAmount", "SupplierInvoice_taxAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "amountDiscount", "SupplierInvoice_amountDiscount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "amountAfterTax", "SupplierInvoice_amountAfterTax_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "netToPay", "SupplierInvoice_netToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "advancePayment", "SupplierInvoice_advancePayment_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalRestToPay", "SupplierInvoice_totalRestToPay_description.title", resourceBundle, NumberType.CURRENCY, locale);
      pagination = viewBuilder.addPagination();
      viewBuilder.addSeparator();

      HBox buttonBar = viewBuilder.addButtonBar();
      createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
      searchButton = viewBuilder.addButton(buttonBar, "Entity_search.title", "searchButton", resourceBundle, AwesomeIcon.SEARCH);
      rootPane = viewBuilder.toAnchorPane();
   }

   public Button getCreateButton()
   {
      return createButton;
   }

   public Button getSearchButton()
   {
      return searchButton;
   }

   public TableView<SupplierInvoice> getDataList()
   {
      return dataList;
   }

   public AnchorPane getRootPane()
   {
      return rootPane;
   }

   public Pagination getPagination()
   {
      return pagination;
   }

}
