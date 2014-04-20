package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.ArticleLotDetailsManager;
import org.adorsys.adpharma.server.jpa.ArticleLotTransferManager;
import org.adorsys.adpharma.server.jpa.ArticleLot_;
import org.adorsys.adpharma.server.jpa.WareHouse;
import org.adorsys.adpharma.server.jpa.WareHouseArticleLot;
import org.adorsys.adpharma.server.jpa.WareHouseArticleLot_;
import org.adorsys.adpharma.server.repo.WareHouseArticleLotRepository;
import org.apache.commons.lang3.text.WordUtils;

@Stateless
public class WareHouseArticleLotEJB
{

	@Inject
	private WareHouseArticleLotRepository repository;

	@Inject
	private WareHouseMerger wareHouseMerger;

	@Inject
	private ArticleLotMerger articleLotMerger;

	public WareHouseArticleLot create(WareHouseArticleLot entity)
	{
		return repository.save(attach(entity));
	}



	public WareHouseArticleLot deleteById(Long id)
	{
		WareHouseArticleLot entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public WareHouseArticleLot update(WareHouseArticleLot entity)
	{
		return repository.save(attach(entity));
	}

	public WareHouseArticleLot findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<WareHouseArticleLot> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<WareHouseArticleLot> findBy(WareHouseArticleLot entity, int start, int max, SingularAttribute<WareHouseArticleLot, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(WareHouseArticleLot entity, SingularAttribute<WareHouseArticleLot, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<WareHouseArticleLot> findByLike(WareHouseArticleLot entity, int start, int max, SingularAttribute<WareHouseArticleLot, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(WareHouseArticleLot entity, SingularAttribute<WareHouseArticleLot, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private WareHouseArticleLot attach(WareHouseArticleLot entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setWareHouse(wareHouseMerger.bindAggregated(entity.getWareHouse()));

		// aggregated
		entity.setArticleLot(articleLotMerger.bindAggregated(entity.getArticleLot()));

		return entity;
	}

	@Inject
	@DocumentProcessedEvent
	private Event<ArticleLotTransferManager> articleLotTransferEvent ;

	public WareHouseArticleLot processTransFer(ArticleLotTransferManager lotTransferManager){
		ArticleLot articleLot = lotTransferManager.getLotToTransfer();
		WareHouse wareHouse = lotTransferManager.getWareHouse();
		BigDecimal qtyToTransfer = lotTransferManager.getQtyToTransfer();
		if(articleLot.getStockQuantity().compareTo(qtyToTransfer)<0) throw new IllegalStateException("qty to transfer must be Smaller than lot qty ");

		WareHouseArticleLot wareHouseArticleLot = new WareHouseArticleLot();
		wareHouseArticleLot.setArticleLot(articleLot);
		wareHouseArticleLot.setWareHouse(wareHouse);
		@SuppressWarnings("unchecked")
		List<WareHouseArticleLot> found = findBy(wareHouseArticleLot, 0, 1, new SingularAttribute[]{WareHouseArticleLot_.articleLot,WareHouseArticleLot_.wareHouse});
		WareHouseArticleLot wal = null ;
		if(!found.isEmpty()){
			wal = found.iterator().next();
			wal.setStockQuantity(wal.getStockQuantity().add(qtyToTransfer));
			update(wal);
		}else {
			wal = new WareHouseArticleLot(articleLot);
			wal.setWareHouse(wareHouse);
			wal.setStockQuantity(qtyToTransfer);
			update(wal);
		}
		articleLotTransferEvent.fire(lotTransferManager);

		return wal ;
	}
}
