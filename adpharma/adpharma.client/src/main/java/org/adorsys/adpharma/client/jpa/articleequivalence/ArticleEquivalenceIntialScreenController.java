package org.adorsys.adpharma.client.jpa.articleequivalence;

import org.adorsys.javafx.crud.extensions.InitialScreenController;

public class ArticleEquivalenceIntialScreenController extends InitialScreenController<ArticleEquivalence>
{
   @Override
   public ArticleEquivalence newEntity()
   {
      return new ArticleEquivalence();
   }
}
