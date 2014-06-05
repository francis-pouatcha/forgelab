package org.adorsys.adpharma.client.jpa.documentarchive;

import org.adorsys.javafx.crud.extensions.model.PropertyReader;
import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;

public class DocumentArchiveRecordingUserListCell extends AbstractToStringListCell<DocumentArchiveRecordingUser>
{

   @Override
   protected String getToString(DocumentArchiveRecordingUser item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "loginName", "gender");
   }

}
