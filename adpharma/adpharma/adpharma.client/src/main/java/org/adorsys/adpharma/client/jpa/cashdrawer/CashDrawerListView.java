package org.adorsys.adpharma.client.jpa.cashdrawer;

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

import javafx.beans.property.SimpleStringProperty;
import org.adorsys.adpharma.client.jpa.login.Login;
import javafx.beans.property.SimpleObjectProperty;
import org.adorsys.adpharma.client.jpa.agency.Agency;
import java.util.Calendar;
import java.math.BigDecimal;
import javafx.beans.property.SimpleBooleanProperty;
import org.adorsys.adpharma.client.jpa.cashdrawer.CashDrawer;

public class CashDrawerListView
{

   @FXML
   AnchorPane rootPane;

   @FXML
   private Button searchButton;

   @FXML
   private Button createButton;

   @FXML
   private TableView<CashDrawer> dataList;

   @Inject
   private Locale locale;

   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , CashDrawer.class
         , Agency.class
   })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "cashDrawerNumber", "CashDrawer_cashDrawerNumber_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "name", "Agency_name_description.title", resourceBundle);
      viewBuilder.addDateColumn(dataList, "openingDate", "CashDrawer_openingDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addDateColumn(dataList, "closingDate", "CashDrawer_closingDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addBigDecimalColumn(dataList, "initialAmount", "CashDrawer_initialAmount_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalCashIn", "CashDrawer_totalCashIn_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalCashOut", "CashDrawer_totalCashOut_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalCash", "CashDrawer_totalCash_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalCheck", "CashDrawer_totalCheck_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalCreditCard", "CashDrawer_totalCreditCard_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalCompanyVoucher", "CashDrawer_totalCompanyVoucher_description.title", resourceBundle, NumberType.CURRENCY, locale);
      viewBuilder.addBigDecimalColumn(dataList, "totalClientVoucher", "CashDrawer_totalClientVoucher_description.title", resourceBundle, NumberType.CURRENCY, locale);
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

   public TableView<CashDrawer> getDataList()
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
