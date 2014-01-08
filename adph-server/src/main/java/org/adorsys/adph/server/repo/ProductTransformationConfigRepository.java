package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.ProductTransformationConfig;

@Repository(forEntity = ProductTransformationConfig.class)
public interface ProductTransformationConfigRepository extends EntityRepository<ProductTransformationConfig, Long>
{
   @Modifying
   @Query("delete from ProductTransformationConfig e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
