package org.adorsys.adpharma.server.repo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;

@Repository(forEntity = SalesOrderItem.class)
public interface SalesOrderItemRepository extends EntityRepository<SalesOrderItem, Long>
{
	@Query("SELECT SUM(s.purchasePricePU * s.orderedQty) FROM SalesOrderItem AS s WHERE s.salesOrder.cashDrawer = ?1 AND s.salesOrder.cashed = ?2 ")
	public  BigDecimal getPurchasePriceValueByCashdrawer(CashDrawer cashDrawer , Boolean casheed) ;
	
	@Query("SELECT c FROM SalesOrderItem as c WHERE c.recordDate >= ?1 AND  c.recordDate <= ?2 AND c.salesOrder.cashed = ?3 ")
	public List<SalesOrderItem> findPreparationDataItem(Date fromDate,Date toDate,Boolean cashed);
	
}

