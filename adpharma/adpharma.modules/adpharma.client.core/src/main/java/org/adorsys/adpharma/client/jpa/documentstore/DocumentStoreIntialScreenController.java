package org.adorsys.adpharma.client.jpa.documentstore;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class DocumentStoreIntialScreenController extends InitialScreenController<DocumentStore>
{
   @Override
   public DocumentStore newEntity()
   {
      return new DocumentStore();
   }
}
