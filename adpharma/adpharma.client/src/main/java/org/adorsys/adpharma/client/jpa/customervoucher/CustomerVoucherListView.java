package org.adorsys.adpharma.client.jpa.customervoucher;

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
import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class CustomerVoucherListView
{

   @FXML
   AnchorPane rootPane;

   @FXML
   private Button searchButton;

   @FXML
   private Button createButton;
   
   @FXML
   private Button cancleButton;
   
   @FXML
   private Button unCancelButton;

   @FXML
   private TableView<CustomerVoucher> dataList;

   @Inject
   private Locale locale;

   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , CustomerVoucher.class
         , Customer.class
         , Agency.class
   })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "voucherNumber", "CustomerVoucher_voucherNumber_description.title", resourceBundle);
      viewBuilder.addBigDecimalColumn(dataList, "amount", "CustomerVoucher_amount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "amountUsed", "CustomerVoucher_amountUsed_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "restAmount", "CustomerVoucher_restAmount_description.title", resourceBundle, NumberType.INTEGER, locale);
      viewBuilder.addStringColumn(dataList, "customer", "CustomerVoucher_customer_description.title", resourceBundle);
		viewBuilder.addDateColumn(dataList, "modifiedDate", "CustomerVoucher_modifiedDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      // Field not displayed in table
      // Field not displayed in table
      viewBuilder.addStringColumn(dataList, "canceled", "CustomerVoucher_canceled_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "settled", "CustomerVoucher_settled_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "voucherPrinted", "CustomerVoucher_voucherPrinted_description.title", resourceBundle);
      // Field not displayed in table
      // Field not displayed in table
      pagination = viewBuilder.addPagination();
      viewBuilder.addSeparator();

      HBox buttonBar = viewBuilder.addButtonBar();
      createButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "createButton", resourceBundle, AwesomeIcon.SAVE);
      cancleButton = viewBuilder.addButton(buttonBar, "Entity_create.title", "cancleButton", resourceBundle, AwesomeIcon.TRASH_ALT);
      cancleButton.setText("Anuller l'avoir");
      
      unCancelButton= viewBuilder.addButton(buttonBar, "Entity_create.title", "unCancelButton", resourceBundle, AwesomeIcon.EDIT);
      unCancelButton.setText("Retablir l'avoir");
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

   public TableView<CustomerVoucher> getDataList()
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

public Button getCancleButton() {
	return cancleButton;
}

public void setCancleButton(Button cancleButton) {
	this.cancleButton = cancleButton;
}

public Button getUnCancelButton() {
	return unCancelButton;
}

public void setUnCancelButton(Button unCancelButton) {
	this.unCancelButton = unCancelButton;
}

}
