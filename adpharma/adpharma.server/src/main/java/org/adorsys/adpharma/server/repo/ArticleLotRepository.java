package org.adorsys.adpharma.server.repo;

import java.math.BigDecimal;
import java.util.List;

import org.adorsys.adpharma.server.jpa.Article;
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
	
	@Query(	"SELECT e FROM ArticleLot AS e WHERE e.stockQuantity > ?1 AND e.article = ?2  ")
    QueryResult<ArticleLot> articleLotByArticleOrderByCreationDate(BigDecimal stockQuantity  , Article article);

	
	@Query(	"SELECT e FROM ArticleLot AS e WHERE LOWER(e.articleName) LIKE LOWER(?1) AND e.stockQuantity > ?2")
    QueryResult<ArticleLot> findByArticleNameLikeAndAndStockUpperThan(String articleName, BigDecimal stock);

	@Query(	"SELECT e FROM ArticleLot AS e WHERE LOWER(e.articleName) LIKE LOWER(?1) AND e.stockQuantity <= ?2")
    QueryResult<ArticleLot> findByArticleNameLikeAndAndStockEqual(String articleName, BigDecimal stock);

	@Query(	"SELECT e FROM ArticleLot AS e WHERE e.article = ?1  AND e.stockQuantity < ?2")
    QueryResult<ArticleLot> findByArticleAndStockQuantityLessThan(Article article,BigDecimal lessThan);

	@Query(	"SELECT e FROM ArticleLot AS e WHERE e.article.qtyInStock < ?1")
    QueryResult<ArticleLot> findBreakArticleLot(BigDecimal lessThan);
	
	@Query(	"SELECT e FROM ArticleLot AS e WHERE e.article.qtyInStock < e.article.minStockQty")
    QueryResult<ArticleLot> findBelowThresholdArticleLot();


}
