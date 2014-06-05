package org.adorsys.adpharma.client.jpa.articlelot;

import org.adorsys.javafx.crud.extensions.view.AbstractToStringListCell;
import org.adorsys.javafx.crud.extensions.model.PropertyReader;

import org.adorsys.adpharma.client.jpa.agency.Agency;

public class ArticleLotAgencyListCell extends AbstractToStringListCell<ArticleLotAgency>
{

   @Override
   protected String getToString(ArticleLotAgency item)
   {
      if (item == null)
      {
         return "";
      }
      return PropertyReader.buildToString(item, "agencyNumber", "name");
   }

}
