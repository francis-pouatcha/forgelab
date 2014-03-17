package org.adorsys.adpharma.server.repo;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.adorsys.adpharma.server.jpa.PaymentCustomerInvoiceAssoc;

@Repository(forEntity = PaymentCustomerInvoiceAssoc.class)
public interface PaymentCustomerInvoiceAssocRepository extends EntityRepository<PaymentCustomerInvoiceAssoc, Long>
{
}
