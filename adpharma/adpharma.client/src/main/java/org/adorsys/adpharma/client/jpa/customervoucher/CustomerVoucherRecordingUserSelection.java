package org.adorsys.adpharma.client.jpa.customervoucher;

import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.javafx.crud.extensions.view.AbstractSelection;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.GridRow;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;

public class CustomerVoucherRecordingUserSelection extends AbstractSelection<CustomerVoucher, Login>
{

   private ComboBox<CustomerVoucherRecordingUser> recordingUser;

   @Inject
   @Bundle({ CrudKeys.class, Login.class, CustomerVoucher.class })
   private ResourceBundle resourceBundle;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();

      recordingUser = viewBuilder.addComboBox("CustomerVoucher_recordingUser_description.title", "recordingUser", resourceBundle, false);

      recordingUser.setCellFactory(new Callback<ListView<CustomerVoucherRecordingUser>, ListCell<CustomerVoucherRecordingUser>>()
      {
         @Override
         public ListCell<CustomerVoucherRecordingUser> call(ListView<CustomerVoucherRecordingUser> listView)
         {
            return new CustomerVoucherRecordingUserListCell();
         }
      });
      recordingUser.setButtonCell(new CustomerVoucherRecordingUserListCell());

      gridRows = viewBuilder.toRows();
   }

   public void bind(CustomerVoucher model)
   {
      recordingUser.valueProperty().bindBidirectional(model.recordingUserProperty());
   }

   public ComboBox<CustomerVoucherRecordingUser> getRecordingUser()
   {
      return recordingUser;
   }
}
