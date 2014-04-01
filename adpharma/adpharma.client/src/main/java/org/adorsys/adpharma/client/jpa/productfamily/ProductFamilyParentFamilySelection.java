package org.adorsys.adpharma.client.jpa.productfamily;

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

import org.adorsys.javaext.format.NumberType;
import org.adorsys.javafx.crud.extensions.ViewType;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;
import org.adorsys.javafx.crud.extensions.view.ViewBuilder;

public class ProductFamilyParentFamilySelection extends AbstractSelection<ProductFamily, ProductFamily>
{

   /*
    * Selection popup
    */
   private VBox rootNode;
   private Button cancelButton;
   private Button closeButton;

   /*
    * Dialog activation.
    */
   private Button selectButton;

   private TableView<ProductFamily> dataList;
   private Pagination pagination;

   Stage dialog;

   @Inject
   @Bundle({ CrudKeys.class
         , ProductFamily.class
         , ProductFamily.class
   })
   private ResourceBundle resourceBundle;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder lazyViewBuilder = new LazyViewBuilder();
      selectButton = lazyViewBuilder.addButton(
            "ProductFamily_parentFamily_description.title", "Entity_select.title",
            "selectButton", resourceBundle);
      gridRows = lazyViewBuilder.toRows();

      ViewBuilder viewBuilder = new ViewBuilder();
      dataList = viewBuilder.addTable("dataList");
      viewBuilder.addStringColumn(dataList, "name", "ProductFamily_name_description.title", resourceBundle);
      pagination = viewBuilder.addPagination();
      viewBuilder.addSeparator();
      HBox buttonBar = viewBuilder.addButtonBar();
      closeButton = viewBuilder.addButton(buttonBar, "Window_close.title", "closeButton", resourceBundle);
      cancelButton = viewBuilder.addButton(buttonBar, "Entity_cancel.title", "cancelButton", resourceBundle);
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
         dialog.setTitle(resourceBundle.getString("ProductFamily_parentFamily_description.title"));
      }
      dialog.show();
   }

   public void bind(ProductFamily model)
   {
   }

   public Button getSelectButton()
   {
      return selectButton;
   }

   public Button getParentFamily()
   {
      return selectButton; // select button required to mark invalid field.
   }

   public Button getCancelButton()
   {
      return cancelButton;
   }

   public TableView<ProductFamily> getDataList()
   {
      return dataList;
   }

   public ButtonBase getCloseButton()
   {
      return closeButton;
   }

   public Pagination getPagination()
   {
      return pagination;
   }
}
