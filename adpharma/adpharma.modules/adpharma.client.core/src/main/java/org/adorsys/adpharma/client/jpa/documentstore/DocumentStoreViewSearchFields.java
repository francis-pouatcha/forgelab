package org.adorsys.adpharma.client.jpa.documentstore;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.documenttype.DocumentType;
import org.adorsys.adpharma.client.jpa.documenttype.DocumentTypeConverter;
import org.adorsys.adpharma.client.jpa.documenttype.DocumentTypeListCellFatory;
import org.adorsys.javafx.crud.extensions.locale.Bundle;
import org.adorsys.javafx.crud.extensions.locale.CrudKeys;
import org.adorsys.javafx.crud.extensions.view.AbstractForm;
import org.adorsys.javafx.crud.extensions.view.LazyViewBuilder;

public class DocumentStoreViewSearchFields extends AbstractForm<DocumentStore>
{

   private TextField documentNumber;

   private TextField documentLocation;

   @Inject
   @Bundle({ CrudKeys.class, DocumentStore.class })
   private ResourceBundle resourceBundle;

   @Inject
   @Bundle(DocumentType.class)
   private ResourceBundle documentTypeBundle;

   @Inject
   private DocumentTypeConverter documentTypeConverter;

   @Inject
   private DocumentTypeListCellFatory documentTypeListCellFatory;

   @Inject
   private Locale locale;

   @PostConstruct
   public void postConstruct()
   {
      LazyViewBuilder viewBuilder = new LazyViewBuilder();
      documentNumber = viewBuilder.addTextField("DocumentStore_documentNumber_description.title", "documentNumber", resourceBundle);
      documentLocation = viewBuilder.addTextField("DocumentStore_documentLocation_description.title", "documentLocation", resourceBundle);

      gridRows = viewBuilder.toRows();
   }

   public void bind(DocumentStore model)
   {
      documentNumber.textProperty().bindBidirectional(model.documentNumberProperty());
      documentLocation.textProperty().bindBidirectional(model.documentLocationProperty());

   }

   public TextField getDocumentNumber()
   {
      return documentNumber;
   }

   public TextField getDocumentLocation()
   {
      return documentLocation;
   }
}
