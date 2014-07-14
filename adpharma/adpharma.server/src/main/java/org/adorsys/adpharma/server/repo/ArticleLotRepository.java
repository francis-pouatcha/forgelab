package org.adorsys.adpharma.server.repo;

import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.Section;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = ArticleLot.class)
public interface  ArticleLotRepository extends EntityRepository<ArticleLot, Long>
{
	@Query("select e from ArticleLot e where e.mainPic = ?1 AND  e.article.section = ?2")
    QueryResult<ArticleLot> findByPicAndSection(String pic , Section section);
}
