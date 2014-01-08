package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.ClientVoucher;

@Repository(forEntity = ClientVoucher.class)
public interface ClientVoucherRepository extends EntityRepository<ClientVoucher, Long>
{
   @Modifying
   @Query("delete from ClientVoucher e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
