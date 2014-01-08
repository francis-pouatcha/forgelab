package org.adorsys.adph.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adph.server.jpa.Payment;

@Repository(forEntity = Payment.class)
public interface PaymentRepository extends EntityRepository<Payment, Long>
{
   @Modifying
   @Query("delete from Payment e WHERE e.id=?1")
   public void removeJPQL(Long id);

}
