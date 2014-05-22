package org.adorsys.adpharma.server.repo;

import org.adorsys.adpharma.server.jpa.PaymentItemCustomerInvoiceAssoc;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PaymentItemCustomerInvoiceAssoc.class)
public interface PaymentItemCustomerInvoiceAssocRepository extends EntityRepository<PaymentItemCustomerInvoiceAssoc, Long>
{
}
