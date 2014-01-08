package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.Supplier;

@Repository(forEntity = Supplier.class)
public interface SupplierRepository extends EntityRepository<Supplier, Long>
{
   @Modifying
   @Query("delete from Supplier e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
