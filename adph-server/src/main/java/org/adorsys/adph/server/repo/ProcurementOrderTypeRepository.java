package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.ProcurementOrderType;

@Repository(forEntity = ProcurementOrderType.class)
public interface ProcurementOrderTypeRepository extends EntityRepository<ProcurementOrderType, Long>
{
   @Modifying
   @Query("delete from ProcurementOrderType e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
