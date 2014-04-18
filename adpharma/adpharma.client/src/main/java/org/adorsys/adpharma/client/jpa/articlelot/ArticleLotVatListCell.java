package org.adorsys.adpharma.client.jpa.articlelot;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.vat.VAT;

public class ArticleLotVatListCell extends AbstractToStringListCell<ArticleLotVat>
{

   @Override
   protected String getToString(ArticleLotVat item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "name");
   }

}
