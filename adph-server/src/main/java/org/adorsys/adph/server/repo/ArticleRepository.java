package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.Article;

@Repository(forEntity = Article.class)
public interface ArticleRepository extends EntityRepository<Article, Long>
{
   @Modifying
   @Query("delete from Article e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
