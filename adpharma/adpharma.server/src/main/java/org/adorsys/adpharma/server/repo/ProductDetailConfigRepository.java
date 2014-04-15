package org.adorsys.adpharma.server.repo;

import java.util.List;

import org.adorsys.adpharma.server.jpa.ProductDetailConfig;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = ProductDetailConfig.class)
public interface ProductDetailConfigRepository extends EntityRepository<ProductDetailConfig, Long>
{
	@Query("SELECT p FROM  ProductDetailConfig p WHERE LOWER(p.source) LIKE LOWER(?1) OR LOWER(p.target) LIKE LOWER(?2)")
	public List<ProductDetailConfig> findByOriginAndTArgetNameLike(String sourceName ,String targetName);
}
