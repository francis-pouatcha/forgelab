package org.adorsys.adpharma.client.jpa.article;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class ArticleIntialScreenController extends InitialScreenController<Article>
{
   @Override
   public Article newEntity()
   {
      return new Article();
   }
}
