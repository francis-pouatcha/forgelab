package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.jpa.Agency;
import org.adorsys.adpharma.server.jpa.CashDrawer;
import org.adorsys.adpharma.server.jpa.CashDrawer_;
import org.adorsys.adpharma.server.jpa.Disbursement;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.Payment;
import org.adorsys.adpharma.server.jpa.PaymentItem;
import org.adorsys.adpharma.server.jpa.PaymentMode;
import org.adorsys.adpharma.server.repo.CashDrawerRepository;
import org.adorsys.adpharma.server.repo.SalesOrderItemRepository;
import org.adorsys.adpharma.server.repo.SalesOrderRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.utils.SequenceGenerator;

@Stateless
public class CashDrawerEJB
{
	@Inject
	private CashDrawerRepository repository;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@Inject
	private SecurityUtil securityUtil;

	@Inject 
	@DocumentProcessedEvent
	private Event<PaymentItem> paymentItemProcessEvent;

	public CashDrawer create(CashDrawer entity)
	{    Login connectedUser = securityUtil.getConnectedUser();
	entity.setCashier(connectedUser);
	entity.setAgency(connectedUser.getAgency());
	entity.initAmount();
	CashDrawer save = repository.save(attach(entity));
	save.setCashDrawerNumber(SequenceGenerator.CASHDRAWER_SEQUENCE_PREFIXE+save.getId());
	return repository.save(save);
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
		CashDrawer cashDrawer = attach(entity);
		return repository.findBy(cashDrawer, start, max, attributes);
	}

	public Long countBy(CashDrawer entity, SingularAttribute<CashDrawer, ?>[] attributes)
	{
		CashDrawer cashDrawer = attach(entity);
		return repository.count(cashDrawer, attributes);
	}

	public List<CashDrawer> findByLike(CashDrawer entity, int start, int max, SingularAttribute<CashDrawer, ?>[] attributes)
	{
		CashDrawer cashDrawer = attach(entity);
		return repository.findByLike(cashDrawer, start, max, attributes);
	}

	public Long countByLike(CashDrawer entity, SingularAttribute<CashDrawer, ?>[] attributes)
	{
		CashDrawer cashDrawer = attach(entity);
		return repository.countLike(cashDrawer, attributes);
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
	
	public void processDisbursment(@Observes @DocumentProcessedEvent Disbursement disbursement){
		CashDrawer cashDrawer = disbursement.getCashDrawer();
		BigDecimal amount = disbursement.getAmount();
		BigDecimal cashOut = cashDrawer.getTotalCashOut() !=null ?cashDrawer.getTotalCashOut():BigDecimal.ZERO;
		cashOut = cashOut.add(amount);
		cashDrawer.setTotalCashOut(cashOut);
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

	public List<CashDrawer> agencyOpenDrawers() {
		Login cashier = securityUtil.getConnectedUser();
		Agency agency = cashier.getAgency();
		CashDrawer cashDrawer = new CashDrawer();
		cashDrawer.setOpened(true);
		cashDrawer.setAgency(agency);
		return findBy(cashDrawer, 0, -1, new SingularAttribute[]{CashDrawer_.agency,CashDrawer_.opened});
	}

	public CashDrawer close(CashDrawer entity) {
		Login cashier = securityUtil.getConnectedUser();
		CashDrawer cashDrawer = findById(entity.getId());
		if(!cashier.equals(cashDrawer.getCashier())) throw new IllegalStateException("Cash drawer can only be closed by owing cashier.");
		cashDrawer.setOpened(false);
		cashDrawer.setClosedBy(cashier);
		cashDrawer.setClosingDate(new Date());
		return update(cashDrawer);
	}

	@Inject
	private SalesOrderRepository salesOrderRepository ;
	
	@Inject
	private SalesOrderItemRepository salesOrderItemRepository ;

	public List<CashDrawer> findByOpeningDateBetween(Date startClosingDate, Date endClosingDate, int start, int max){
		List<CashDrawer> cashDrawers = repository.findByOpeningDateBetween(startClosingDate, endClosingDate, start, max);

		for (CashDrawer cashDrawer : cashDrawers) {
			BigDecimal totalDrugVoucher = salesOrderRepository.getInsurranceSalesByCashDrawer(cashDrawer);
			if(totalDrugVoucher!=null)
				cashDrawer.setTotalDrugVoucher(totalDrugVoucher);
			BigDecimal purchasePriceValue = salesOrderItemRepository.getPurchasePriceValueByCashdrawer(cashDrawer,Boolean.TRUE);
			if(purchasePriceValue!=null)
				cashDrawer.setTotalCompanyVoucher(purchasePriceValue);
		}

		return cashDrawers ;
	}

	public Long countByOpeningDateBetween(Date startClosingDate, Date endClosingDate){
		return repository.countByOpeningDateBetween(startClosingDate, endClosingDate);
	}

	/**
	 * Listens to payments and updates the cash drawer.
	 * 
	 * @param payment
	 */
	public void processPayment(@Observes @DocumentProcessedEvent Payment payment){
		CashDrawer cashDrawer = payment.getCashDrawer();
		cashDrawer = findById(cashDrawer.getId());
		Set<PaymentItem> paymentItems = payment.getPaymentItems();
		for (PaymentItem paymentItem : paymentItems) {
			BigDecimal amount = paymentItem.getAmount();
			cashDrawer.setTotalCashIn(cashDrawer.getTotalCashIn().add(amount));
			switch (paymentItem.getPaymentMode()) {
			case CASH:
				cashDrawer.setTotalCash(cashDrawer.getTotalCash().add(amount));
				break;
			case CREDIT_CARD:
				cashDrawer.setTotalCreditCard(cashDrawer.getTotalCreditCard().add(amount));
				break;
			case VOUCHER:
				paymentItem.getPaidBy();
				cashDrawer.setTotalClientVoucher(cashDrawer.getTotalClientVoucher().add(amount));
				break;
			case COMP_VOUCHER:
				cashDrawer.setTotalCompanyVoucher(cashDrawer.getTotalCompanyVoucher().add(amount));
				break;
			case CHECK:
				cashDrawer.setTotalCheck(cashDrawer.getTotalCheck().add(amount));
				break;
			default:
				break;
			}
		}
		update(cashDrawer);
	}
}
