package org.adorsys.adpharma.server.repo;

import java.math.BigDecimal;

import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SalesOrder.class)
public abstract class SalesOrderRepository extends AdpharmaEntityRepository<SalesOrder, Long>
{

	@Query("SELECT SUM((s.amountAfterTax * s.insurance.coverageRate)/100) FROM SalesOrder AS s WHERE s.insurance <> NULL AND s.cashDrawer = ?1 ")
	public abstract BigDecimal getInsurranceSalesByCashDrawer(CashDrawer cashDrawer) ;
}
 