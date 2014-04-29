package org.adorsys.adpharma.client.jpa.paymentitem;

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

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;
import de.jensd.fx.fontawesome.AwesomeIcon;

import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.math.BigDecimal;
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.payment.Payment;
import org.adorsys.adpharma.client.jpa.paymentitem.PaymentItem;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentModeConverter;

public class PaymentItemListView
{

   @FXML
   AnchorPane rootPane;

   @FXML
   private Button searchButton;

   @FXML
   private Button createButton;

   @FXML
   private TableView<PaymentItem> dataList;

   @Inject
   private Locale locale;

   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , PaymentItem.class
         , Customer.class
   })
   private ResourceBundle resourceBundle;

   @Inject
   private PaymentModeConverter paymentModeConverter;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addEnumColumn(dataList, "paymentMode", "PaymentItem_paymentMode_description.title", resourceBundle, paymentModeConverter);
      viewBuilder.addStringColumn(dataList, "documentNumber", "PaymentItem_documentNumber_description.title", resourceBundle);
      viewBuilder.addBigDecimalColumn(dataList, "amount", "PaymentItem_amount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "receivedAmount", "PaymentItem_receivedAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle);
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

   public TableView<PaymentItem> getDataList()
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
