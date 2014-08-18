package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.jpa.ProcurementOrder;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItem;
import org.adorsys.adpharma.server.jpa.ProcurementOrderItem_;
import org.adorsys.adpharma.server.repo.ProcurementOrderItemRepository;
import org.adorsys.adpharma.server.repo.ProcurementOrderRepository;

@Stateless
public class ProcurementOrderItemEJB
{

	@Inject
	private ProcurementOrderItemRepository repository;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private ProcurementOrderMerger procurementOrderMerger;

	@Inject
	private ArticleMerger articleMerger;

	@Inject
	private ProcurementOrderRepository procurementOrderRepository ;

	@SuppressWarnings("unchecked")
	public ProcurementOrderItem create(ProcurementOrderItem entity)
	{
		ProcurementOrderItem original = attach(entity);
		ProcurementOrderItem poi = new ProcurementOrderItem();
		poi.setArticle(entity.getArticle());
		poi.setProcurementOrder(original.getProcurementOrder());
		List<ProcurementOrderItem> found = findBy(poi, 0, 1, new SingularAttribute[]{ProcurementOrderItem_.article,ProcurementOrderItem_.procurementOrder});
		if(!found.isEmpty()){
			ProcurementOrderItem next = found.iterator().next();
			next.setQtyOrdered(next.getQtyOrdered().add(entity.getQtyOrdered()));
			next.setPurchasePricePU(entity.getPurchasePricePU());
			next.setSalesPricePU(entity.getSalesPricePU());
			next.calculTotalPuschasePrice();
			return update(next);
		}
		addItem(original);
		return repository.save(original);
	}

	public ProcurementOrderItem deleteById(Long id)
	{
		ProcurementOrderItem entity = repository.findBy(id);
		if (entity != null)
		{
			removeItem(entity);
			repository.remove(entity);
		}
		return entity;
	}

	public ProcurementOrderItem update(ProcurementOrderItem entity)
	{
		BigDecimal removedAmount = repository.findBy(entity.getId()).getTotalPurchasePrice();
		ProcurementOrderItem attach = attach(entity);
		editItem(attach, removedAmount);
		return repository.save(attach);
	}

	public ProcurementOrderItem findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<ProcurementOrderItem> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<ProcurementOrderItem> findBy(ProcurementOrderItem entity, int start, int max, SingularAttribute<ProcurementOrderItem, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(ProcurementOrderItem entity, SingularAttribute<ProcurementOrderItem, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<ProcurementOrderItem> findByLike(ProcurementOrderItem entity, int start, int max, SingularAttribute<ProcurementOrderItem, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(ProcurementOrderItem entity, SingularAttribute<ProcurementOrderItem, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private ProcurementOrderItem attach(ProcurementOrderItem entity)
	{
		if (entity == null)
			return null;

		// composed


		// aggregated
		entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

		// aggregated
		entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

		return entity;
	}

	private void addItem(ProcurementOrderItem item){
		ProcurementOrder procurementOrder = procurementOrderMerger.getRepository().findBy(item.getProcurementOrder().getId());
		procurementOrder.setAmountBeforeTax(procurementOrder.getAmountBeforeTax().add(item.getTotalPurchasePrice()));

		calculateAmount(procurementOrder, item);
	}

	private void editItem(ProcurementOrderItem item,BigDecimal removedAmount){
		ProcurementOrder procurementOrder = procurementOrderMerger.getRepository().findBy(item.getProcurementOrder().getId());
		procurementOrder.setAmountBeforeTax(procurementOrder.getAmountBeforeTax().subtract(removedAmount));
		procurementOrder.setAmountBeforeTax(procurementOrder.getAmountBeforeTax().add(item.getTotalPurchasePrice()));

		calculateAmount(procurementOrder, item);
	}

	private void removeItem(ProcurementOrderItem item){
		ProcurementOrder procurementOrder = procurementOrderMerger.getRepository().findBy(item.getProcurementOrder().getId());
		procurementOrder.setAmountBeforeTax(procurementOrder.getAmountBeforeTax().subtract(item.getTotalPurchasePrice()));
		calculateAmount(procurementOrder, item);

	}

	public void calculateAmount(ProcurementOrder procurementOrder ,ProcurementOrderItem item){
		procurementOrder.setAmountAfterTax(procurementOrder.getAmountBeforeTax());
		procurementOrder.setNetAmountToPay(procurementOrder.getAmountBeforeTax());
		ProcurementOrder save = procurementOrderMerger.getRepository().save(procurementOrder);
		item.setProcurementOrder(save);
	}


}
