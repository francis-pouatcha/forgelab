package org.adorsys.adpharma.client.jpa.articlelot;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class ArticleLotIntialScreenController extends InitialScreenController<ArticleLot>
{
   @Override
   public ArticleLot newEntity()
   {
      return new ArticleLot();
   }
}
