package org.adorsys.adpharma.client.jpa.customervoucher;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class CustomerVoucherRecordingUserListCell extends AbstractToStringListCell<CustomerVoucherRecordingUser>
{

   @Override
   protected String getToString(CustomerVoucherRecordingUser item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
