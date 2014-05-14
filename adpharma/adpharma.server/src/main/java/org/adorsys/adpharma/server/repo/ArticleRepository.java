package org.adorsys.adpharma.server.repo;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adpharma.server.jpa.Article;

@Repository(forEntity = Article.class)
public abstract class ArticleRepository extends AdpharmaEntityRepository<Article, Long>
{
}
