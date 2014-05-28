package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.events.ReturnSalesEvent;
import org.adorsys.adpharma.server.jpa.CustomerInvoice;
import org.adorsys.adpharma.server.jpa.CustomerInvoice_;
import org.adorsys.adpharma.server.jpa.CustomerVoucher;
import org.adorsys.adpharma.server.jpa.CustomerVoucher_;
import org.adorsys.adpharma.server.jpa.InvoiceType;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Payment;
import org.adorsys.adpharma.server.jpa.PaymentItem;
import org.adorsys.adpharma.server.jpa.PaymentMode;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.repo.CustomerVoucherRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.utils.SequenceGenerator;

@Stateless
public class CustomerVoucherEJB
{

	@Inject
	private CustomerVoucherRepository repository;

	@Inject
	private CustomerMerger customerMerger;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private CustomerInvoiceMerger customerInvoiceMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@Inject
	private SecurityUtil securityUtil;

	@Inject
	private CustomerInvoiceEJB customerInvoiceEJB ;

	@Inject
	private PaymentEJB paymentEJB;

	public CustomerVoucher create(CustomerVoucher entity)
	{
		CustomerVoucher save = repository.save(attach(entity)); 
		save.setVoucherNumber((SequenceGenerator.CUSTOMER_VOUCHER_SEQUENCE_PREFIXE+save.getId()));
		return repository.save(attach(entity));
	}

	public List<CustomerVoucher> findByPaiementId(Long PaiementId){
		ArrayList<CustomerVoucher> usedVouchers = new ArrayList<CustomerVoucher>() ;
		Payment payment = paymentEJB.findById(PaiementId);
		if(payment!=null){
			Set<PaymentItem> paymentItems = payment.getPaymentItems();
			for (PaymentItem paymentItem : paymentItems) {
				if(PaymentMode.VOUCHER.equals(paymentItem.getPaymentMode())){
					CustomerVoucher voucher = new CustomerVoucher();
					voucher.setVoucherNumber(paymentItem.getDocumentNumber());
					List<CustomerVoucher> found = findBy(voucher, 0, 1, new SingularAttribute[]{CustomerVoucher_.voucherNumber});
					if(!found.isEmpty())
						usedVouchers.add(found.iterator().next());
				}
			}
		}
		return usedVouchers;
	}

	public CustomerVoucher deleteById(Long id)
	{
		CustomerVoucher entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public CustomerVoucher update(CustomerVoucher entity)
	{
		return repository.save(attach(entity));
	}

	public CustomerVoucher findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<CustomerVoucher> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<CustomerVoucher> findBy(CustomerVoucher entity, int start, int max, SingularAttribute<CustomerVoucher, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CustomerVoucher entity, SingularAttribute<CustomerVoucher, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<CustomerVoucher> findByLike(CustomerVoucher entity, int start, int max, SingularAttribute<CustomerVoucher, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CustomerVoucher entity, SingularAttribute<CustomerVoucher, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private CustomerVoucher attach(CustomerVoucher entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCustomerInvoice(customerInvoiceMerger.bindAggregated(entity.getCustomerInvoice()));

		// aggregated
		entity.setCustomer(customerMerger.bindAggregated(entity.getCustomer()));

		// aggregated
		entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

		// aggregated
		entity.setRecordingUser(loginMerger.bindAggregated(entity.getRecordingUser()));

		return entity;
	}

	public CustomerVoucher findBySalesOrder(SalesOrder salesOrder){
		List<CustomerVoucher> found = repository.findBySalesOrder(salesOrder);
		if(!found.isEmpty())
			return found.iterator().next();
		return null ;
	}

	@SuppressWarnings("unchecked")
	public void processPaymentClosed(@Observes @DocumentProcessedEvent Payment payment){
		Set<PaymentItem> paymentItems = payment.getPaymentItems();
		for (PaymentItem paymentItem : paymentItems) {
			if(PaymentMode.VOUCHER.equals(paymentItem.getPaymentMode())){
				String documentNumber = paymentItem.getDocumentNumber();
				CustomerVoucher customerVoucher = new CustomerVoucher();
				customerVoucher.setVoucherNumber(documentNumber);
				List<CustomerVoucher> found = findBy(customerVoucher, 0, 1, new SingularAttribute[]{CustomerVoucher_.voucherNumber});
				if(found.isEmpty()) throw new IllegalStateException("Unknown customer voucher.");
				customerVoucher = found.iterator().next();
				customerVoucher.setAmountUsed((customerVoucher.getAmountUsed().add(paymentItem.getAmount())).negate());
				customerVoucher.setModifiedDate(new Date());
				customerVoucher.setRecordingUser(securityUtil.getConnectedUser());
				customerVoucher.setRestAmount(customerVoucher.getAmount().subtract(customerVoucher.getAmountUsed()));
				if(customerVoucher.getRestAmount().compareTo(BigDecimal.ZERO)<=0)
					customerVoucher.setSettled(Boolean.TRUE);
				update(customerVoucher);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void HandleSalesReturn(@Observes @ReturnSalesEvent CustomerInvoice customerInvoice){
		if(InvoiceType.VOUCHER.equals(customerInvoice.getInvoiceType())){
			Login login = securityUtil.getConnectedUser();
			BigDecimal customerAmount = customerInvoice.getCustomerRestTopay().negate();
			BigDecimal InsurranceAmount = customerInvoice.getInsurranceRestTopay().negate();

			CustomerVoucher customerVoucher = new CustomerVoucher();
			customerVoucher.setAgency(customerInvoice.getAgency());
			customerVoucher.setAmount(customerAmount);
			customerVoucher.setCustomer(customerInvoice.getCustomer());
			customerVoucher.setRestAmount(customerAmount);
			customerVoucher.setCustomerInvoice(customerInvoice);
			customerVoucher.setRecordingUser(login);
			create(customerVoucher);

			if(customerInvoice.getInsurance()!=null){
				CustomerVoucher insurranceVoucher = new CustomerVoucher();
				insurranceVoucher.setAgency(customerInvoice.getAgency());
				insurranceVoucher.setAmount(InsurranceAmount);
				insurranceVoucher.setCustomer(customerInvoice.getInsurance().getInsurer());
				insurranceVoucher.setRestAmount(InsurranceAmount);
				insurranceVoucher.setCustomerInvoice(customerInvoice);
				insurranceVoucher.setRecordingUser(login);
				create(insurranceVoucher);
			}

		}
	}


}
