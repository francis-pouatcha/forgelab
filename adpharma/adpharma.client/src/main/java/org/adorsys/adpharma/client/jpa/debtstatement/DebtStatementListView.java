package org.adorsys.adpharma.client.jpa.debtstatement;

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

public class DebtStatementListView
{

   @FXML
   AnchorPane rootPane;

   @FXML
   private Button searchButton;

   @FXML
   private Button createButton;

   @FXML
   private TableView<DebtStatement> dataList;

   @Inject
   private Locale locale;

   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , DebtStatement.class
         , Customer.class
         , Agency.class
   })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "statementNumber", "DebtStatement_statementNumber_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "fullName", "Customer_fullName_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "name", "Agency_name_description.title", resourceBundle);
      viewBuilder.addDateColumn(dataList, "paymentDate", "DebtStatement_paymentDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addBigDecimalColumn(dataList, "initialAmount", "DebtStatement_initialAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "advancePayment", "DebtStatement_advancePayment_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "restAmount", "DebtStatement_restAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      // Field not displayed in table
      viewBuilder.addBigDecimalColumn(dataList, "amountFromVouchers", "DebtStatement_amountFromVouchers_description.title", resourceBundle, NumberType.CURRENCY, locale);
      // Field not displayed in table
      // Field not displayed in table
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

   public TableView<DebtStatement> getDataList()
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
