package org.adorsys.adpharma.server.repo;

import java.math.BigDecimal;

import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.jpa.DocumentProcessingState;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SalesOrder.class)
public abstract class SalesOrderRepository extends AdpharmaEntityRepository<SalesOrder, Long>
{

	@Query("SELECT SUM((s.amountAfterTax * s.insurance.coverageRate)/100) FROM SalesOrder AS s WHERE s.insurance <> NULL AND s.cashDrawer = ?1 AND s.salesOrderStatus = ?2  ")
	public abstract BigDecimal getInsurranceSalesByCashDrawer(CashDrawer cashDrawer,DocumentProcessingState state) ;
	
	@Query("SELECT SUM(s.amountDiscount) FROM SalesOrder AS s WHERE s.cashDrawer = ?1 AND s.cashed = ?2 ")
	public abstract BigDecimal getDiscountByCashdrawer(CashDrawer cashDrawer , Boolean casheed) ;

	@Query("SELECT SUM(s.amountDiscount) FROM SalesOrder AS s")
	public abstract BigDecimal getDiscountByCashdrawer2() ;
}
 