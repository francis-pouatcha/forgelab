package org.adorsys.adpharma.client.jpa.documentstore;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.login.Login;

public class DocumentStoreRecordingUserListCell extends AbstractToStringListCell<DocumentStoreRecordingUser>
{

   @Override
   protected String getToString(DocumentStoreRecordingUser item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
