package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DirectSalesClosedEvent;
import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.jpa.CashDrawer_;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Payment;
import org.adorsys.adpharma.server.jpa.PaymentItem;
import org.adorsys.adpharma.server.jpa.PaymentMode;
import org.adorsys.adpharma.server.repo.CashDrawerRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.apache.commons.lang3.RandomStringUtils;

@Stateless
public class CashDrawerEJB
{
	@Inject
	private CashDrawerRepository repository;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@EJB
	private SecurityUtil securityUtil;
	
	@Inject 
	@DocumentProcessedEvent
	private Event<PaymentItem> paymentItemProcessEvent;

	public CashDrawer create(CashDrawer entity)
	{	
		CashDrawer cashDrawer = attach(entity);
		Login cashier = securityUtil.getConnectedUser();
		cashDrawer.setCashier(cashier);
		cashDrawer.setAgency(cashier.getAgency());
		cashDrawer.setCashDrawerNumber("CD-"+RandomStringUtils.randomAlphanumeric(5));
		cashDrawer.setOpened(true);
		cashDrawer.setOpeningDate(new Date());
		cashDrawer.setTotalCash(cashDrawer.getInitialAmount());
		cashDrawer.setTotalCashIn(BigDecimal.ZERO);
		cashDrawer.setTotalCashOut(BigDecimal.ZERO);
		cashDrawer.setTotalCheck(BigDecimal.ZERO);
		cashDrawer.setTotalClientVoucher(BigDecimal.ZERO);
		cashDrawer.setTotalCompanyVoucher(BigDecimal.ZERO);
		cashDrawer.setTotalCreditCard(BigDecimal.ZERO);
		return repository.save(cashDrawer);
	}

	public CashDrawer deleteById(Long id)
	{
		CashDrawer entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public CashDrawer update(CashDrawer entity)
	{
		return repository.save(attach(entity));
	}

	public CashDrawer findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<CashDrawer> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<CashDrawer> findBy(CashDrawer entity, int start, int max, SingularAttribute<CashDrawer, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CashDrawer entity, SingularAttribute<CashDrawer, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<CashDrawer> findByLike(CashDrawer entity, int start, int max, SingularAttribute<CashDrawer, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CashDrawer entity, SingularAttribute<CashDrawer, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private CashDrawer attach(CashDrawer entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCashier(loginMerger.bindAggregated(entity.getCashier()));

		// aggregated
		entity.setClosedBy(loginMerger.bindAggregated(entity.getClosedBy()));

		// aggregated
		entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

		return entity;
	}

	public void processPaymentClosed(@Observes @DocumentClosedEvent Payment payment){
		CashDrawer cashDrawer = payment.getCashDrawer();
		Set<PaymentItem> paymentItems = payment.getPaymentItems();
		BigDecimal amount = payment.getAmount();
		cashDrawer.setTotalCashIn(amount);
		for (PaymentItem paymentItem : paymentItems) {
			PaymentMode paymentMode = paymentItem.getPaymentMode();
			switch (paymentMode) {
			case CASH:
				BigDecimal totalCash = cashDrawer.getTotalCash()==null?BigDecimal.ZERO:cashDrawer.getTotalCash();
				cashDrawer.setTotalCash(totalCash.add(paymentItem.getAmount()));
				break;
			case CREDIT_CARD:
				BigDecimal totalCreditCard = cashDrawer.getTotalCreditCard()==null?BigDecimal.ZERO:cashDrawer.getTotalCreditCard();
				cashDrawer.setTotalCreditCard(totalCreditCard.add(paymentItem.getAmount()));
				break;
			case CHECK:
				BigDecimal totalCheck = cashDrawer.getTotalCheck()==null?BigDecimal.ZERO:cashDrawer.getTotalCheck();
				cashDrawer.setTotalCheck(totalCheck.add(paymentItem.getAmount()));
				break;
			case VOUCHER:
				BigDecimal totalClientVoucher = cashDrawer.getTotalClientVoucher()==null?BigDecimal.ZERO:cashDrawer.getTotalClientVoucher();
				cashDrawer.setTotalClientVoucher(totalClientVoucher.add(paymentItem.getAmount()));
				break;
			default:
				throw new IllegalStateException("Unknown payment mode: "+paymentMode);
			}
		}
		update(cashDrawer);
	}

	public List<CashDrawer> myOpenDrawers() {
		Login cashier = securityUtil.getConnectedUser();
		CashDrawer cashDrawer = new CashDrawer();
		cashDrawer.setCashier(cashier);
		cashDrawer.setOpened(true);
		return findBy(cashDrawer, 0, -1, new SingularAttribute[]{CashDrawer_.cashier, CashDrawer_.opened});
	}

	public List<CashDrawer> agencyDrawers() {
		Login cashier = securityUtil.getConnectedUser();
		Agency agency = cashier.getAgency();
		CashDrawer cashDrawer = new CashDrawer();
		cashDrawer.setAgency(agency);
		return findBy(cashDrawer, 0, -1, new SingularAttribute[]{CashDrawer_.agency});
	}

	public CashDrawer close(CashDrawer entity) {
		CashDrawer cashDrawer = attach(entity);
		Login cashier = securityUtil.getConnectedUser();
		if(!cashier.equals(cashDrawer.getCashier())) throw new IllegalStateException("Cash drawer can only be closed by owing cashier.");
		cashDrawer.setOpened(false);
		cashDrawer.setClosedBy(cashier);
		cashDrawer.setClosingDate(new Date());
		return update(cashDrawer);
	}
}
