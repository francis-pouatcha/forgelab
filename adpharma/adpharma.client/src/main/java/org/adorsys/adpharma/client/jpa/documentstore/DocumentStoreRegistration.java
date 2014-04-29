package org.adorsys.adpharma.client.jpa.documentstore;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.javaext.description.Description;
import org.adorsys.javafx.crud.extensions.DomainComponentController;
import org.adorsys.javafx.crud.extensions.DomainComponentRegistration;
import org.adorsys.javafx.crud.extensions.cdiextention.Eager;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.adpharma.client.jpa.documentstore.DocumentStore;

/**
 * Mark this component eager to enable registration of this component with the component list.
 * 
 * @author francis
 *
 */
@Eager
@Singleton
public class DocumentStoreRegistration extends DomainComponentRegistration
{

   @Inject
   @Bundle(DocumentStore.class)
   private ResourceBundle resourceBundle;

   @Override
   protected Class<?> getComponentClass()
   {
      return DocumentStore.class;
   }

   @Override
   protected Class<? extends DomainComponentController> getControllerClass()
   {
      return DocumentStoreController.class;
   }

   @Override
   protected String getComponentI18nName()
   {
      Description annotation = DocumentStore.class.getAnnotation(Description.class);
      if (annotation != null)
         return resourceBundle.getString(annotation.value() + ".title");
      return DocumentStore.class.getName();
   }

   @Override
   public String getComponentPermission()
   {
      return "org.adorsys.adpharma.server.jpa.DocumentStore";
   }

}
