package org.adorsys.adpharma.server.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adpharma.server.jpa.CustomerInvoiceItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CustomerInvoiceItem.class)
public interface CustomerInvoiceItemRepository extends EntityRepository<CustomerInvoiceItem, Long>
{
	@Query("SELECT c FROM CustomerInvoiceItem as c WHERE c.invoice.creationDate >= ?1 AND c.invoice.creationDate <= ?2 AND c.invoice.cashed = ?3")
	public List<CustomerInvoiceItem> findPreparationDataItem(Date fromDate,Date toDate,Boolean cashed);
}
