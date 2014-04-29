package org.adorsys.adpharma.client.jpa.documenttype;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class DocumentTypeConverter extends StringConverter<DocumentType>
{

   @Inject
   @Bundle({ DocumentType.class })
   private ResourceBundle bundle;

   @Override
   public DocumentType fromString(String name)
   {
      return DocumentType.valueOf(name);
   }

   @Override
   public String toString(DocumentType object)
   {
      if (object == null)
         return null;
      return bundle.getString("DocumentType_" + object.name()
            + "_description.title");
   }
}
