package org.adorsys.adpharma.server.repo;

import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.Section;
import org.adorsys.adpharma.server.jpa.VAT;

@Repository(forEntity = Article.class)
public interface ArticleRepository extends EntityRepository<Article, Long>
{
	@Query("SELECT a FROM Article AS a WHERE a.pic = ?1 AND a.section = ?2 ")
	public List<Article> findBySectionAndPic(String pic ,Section section) ;
	
	@Query("SELECT a FROM Article AS a WHERE a.qtyInStock > a.maxStockQty ")
	public List<Article> findArticleMaxStockRepport() ;
	
	@Query("SELECT a FROM Article AS a WHERE a.qtyInStock < a.minStockQty ")
	public List<Article> findArticleMinStockRepport() ;
	
	
}
