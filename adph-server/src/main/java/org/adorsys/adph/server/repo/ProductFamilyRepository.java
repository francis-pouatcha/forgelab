package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.ProductFamily;

@Repository(forEntity = ProductFamily.class)
public interface ProductFamilyRepository extends EntityRepository<ProductFamily, Long>
{
   @Modifying
   @Query("delete from ProductFamily e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
