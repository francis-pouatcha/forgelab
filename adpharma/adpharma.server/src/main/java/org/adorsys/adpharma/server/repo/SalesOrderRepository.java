package org.adorsys.adpharma.server.repo;

import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SalesOrder.class)
public abstract class SalesOrderRepository extends AdpharmaEntityRepository<SalesOrder, Long>
{


}
 