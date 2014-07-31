package org.adorsys.adpharma.server.repo;

import java.util.List;

import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.DebtStatement;
import org.adorsys.adpharma.server.jpa.DebtStatementCustomerInvoiceAssoc;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = DebtStatementCustomerInvoiceAssoc.class)
public interface DebtStatementCustomerInvoiceAssocRepository extends EntityRepository<DebtStatementCustomerInvoiceAssoc, Long>
{
	@Query("select d.target from DebtStatementCustomerInvoiceAssoc as d where d.source = ?1 ")
	public List<CustomerInvoice> findCustomerInvoiceBySource(DebtStatement source);
}
