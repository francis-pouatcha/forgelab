package org.adorsys.adpharma.server.repo;

import java.util.List;

import org.adorsys.adpharma.server.jpa.CustomerVoucher;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CustomerVoucher.class)
public interface CustomerVoucherRepository extends EntityRepository<CustomerVoucher, Long>
{
	@Query("SELECT v FROM CustomerVoucher AS v WHERE v.customerInvoice.salesOrder = ?1")
	public  List<CustomerVoucher>   findBySalesOrder(SalesOrder salesOrder) ;
}
