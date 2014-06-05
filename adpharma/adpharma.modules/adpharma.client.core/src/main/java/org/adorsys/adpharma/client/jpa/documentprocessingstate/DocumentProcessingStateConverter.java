package org.adorsys.adpharma.client.jpa.documentprocessingstate;

import java.util.ResourceBundle;

import javafx.util.StringConverter;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javafx.crud.extensions.locale.Bundle;

@Singleton
public class DocumentProcessingStateConverter extends StringConverter<DocumentProcessingState>
{

   @Inject
   @Bundle({ DocumentProcessingState.class })
   private ResourceBundle bundle;

   @Override
   public DocumentProcessingState fromString(String name)
   {
      return DocumentProcessingState.valueOf(name);
   }

   @Override
   public String toString(DocumentProcessingState object)
   {
      if (object == null)
         return null;
      return bundle.getString("DocumentProcessingState_" + object.name()
            + "_description.title");
   }
}
