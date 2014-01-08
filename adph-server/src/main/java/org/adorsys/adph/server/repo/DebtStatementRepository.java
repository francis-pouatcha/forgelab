package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.DebtStatement;

@Repository(forEntity = DebtStatement.class)
public interface DebtStatementRepository extends EntityRepository<DebtStatement, Long>
{
   @Modifying
   @Query("delete from DebtStatement e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
