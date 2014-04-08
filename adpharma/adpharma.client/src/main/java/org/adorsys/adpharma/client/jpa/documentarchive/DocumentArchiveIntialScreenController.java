package org.adorsys.adpharma.client.jpa.documentarchive;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class DocumentArchiveIntialScreenController extends InitialScreenController<DocumentArchive>
{
   @Override
   public DocumentArchive newEntity()
   {
      return new DocumentArchive();
   }
}
