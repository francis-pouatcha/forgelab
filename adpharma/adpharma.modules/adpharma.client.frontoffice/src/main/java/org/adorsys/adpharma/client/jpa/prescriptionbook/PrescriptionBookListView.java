package org.adorsys.adpharma.client.jpa.prescriptionbook;

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
import org.adorsys.adpharma.client.jpa.hospital.Hospital;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.prescriber.Prescriber;
import org.adorsys.adpharma.client.jpa.salesorder.SalesOrder;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

import de.jensd.fx.fontawesome.AwesomeIcon;

public class PrescriptionBookListView
{

   @FXML
   AnchorPane rootPane;

   @FXML
   private Button searchButton;

   @FXML
   private Button createButton;

   @FXML
   private TableView<PrescriptionBook> dataList;

   @Inject
   private Locale locale;

   private Pagination pagination;

   @Inject
   @Bundle({ CrudKeys.class
         , PrescriptionBook.class
         , Prescriber.class
         , Hospital.class
         , Agency.class
         , Login.class
         , SalesOrder.class
   })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "prescriber", "PrescriptionBook_prescriber_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "hospital", "PrescriptionBook_hospital_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "agency", "PrescriptionBook_agency_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "recordingAgent", "PrescriptionBook_recordingAgent_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "prescriptionNumber", "PrescriptionBook_prescriptionNumber_description.title", resourceBundle);
      viewBuilder.addStringColumn(dataList, "salesOrder", "PrescriptionBook_salesOrder_description.title", resourceBundle);
      viewBuilder.addDateColumn(dataList, "prescriptionDate", "PrescriptionBook_prescriptionDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
      viewBuilder.addDateColumn(dataList, "recordingDate", "PrescriptionBook_recordingDate_description.title", resourceBundle, "dd-MM-yyyy HH:mm", locale);
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

   public TableView<PrescriptionBook> getDataList()
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
