package org.adorsys.adpharma.server.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.events.DestockingProcessedEvent;
import org.adorsys.adpharma.server.events.DirectSalesClosedEvent;
import org.adorsys.adpharma.server.events.DocumentClosedEvent;
import org.adorsys.adpharma.server.events.DocumentProcessedEvent;
import org.adorsys.adpharma.server.events.ReturnSalesEvent;
import org.adorsys.adpharma.server.events.ReturnSalesTraceEvent;
import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.ArticleLot;
import org.adorsys.adpharma.server.jpa.ArticleLotDetailsManager;
import org.adorsys.adpharma.server.jpa.ArticleLotMovedToTrashData;
import org.adorsys.adpharma.server.jpa.ArticleLotTransferManager;
import org.adorsys.adpharma.server.jpa.Delivery;
import org.adorsys.adpharma.server.jpa.DeliveryItem;
import org.adorsys.adpharma.server.jpa.Login;
import org.adorsys.adpharma.server.jpa.SalesOrder;
import org.adorsys.adpharma.server.jpa.SalesOrderItem;
import org.adorsys.adpharma.server.jpa.StockMovement;
import org.adorsys.adpharma.server.jpa.StockMovementTerminal;
import org.adorsys.adpharma.server.jpa.StockMovementType;
import org.adorsys.adpharma.server.jpa.StockMovement_;
import org.adorsys.adpharma.server.repo.StockMovementRepository;
import org.adorsys.adpharma.server.security.SecurityUtil;
import org.adorsys.adpharma.server.startup.ApplicationConfiguration;
import org.adorsys.adpharma.server.utils.PeriodicalDataSearchInput;
import org.apache.commons.lang3.StringUtils;
@Stateless
public class StockMovementEJB
{

	@Inject
	private StockMovementRepository repository;

	@Inject
	private LoginMerger loginMerger;

	@Inject
	private ArticleMerger articleMerger;

	@Inject
	private AgencyMerger agencyMerger;

	@Inject
	private SecurityUtil securityUtil;

	@Inject
	private ArticleLotEJB articleLotEJB;

	@Inject
	@DocumentProcessedEvent
	private Event<StockMovement> stockMovementEvent;

	public StockMovement create(StockMovement entity)
	{
		return repository.save(attach(entity));
	}

	public StockMovement deleteById(Long id)
	{
		StockMovement entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public StockMovement update(StockMovement entity)
	{
		return repository.save(attach(entity));
	}

	public StockMovement findById(Long id)
	{
		return repository.findBy(id);
	}

	public List<StockMovement> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<StockMovement> findBy(StockMovement entity, int start, int max, SingularAttribute<StockMovement, Object>[] attributes)
	{
		entity = attach(entity);
		return repository.criteriafindBy(entity, attributes).orderDesc(StockMovement_.id).createQuery().setFirstResult(start).setMaxResults(max).getResultList();
	}

	public Long countBy(StockMovement entity, SingularAttribute<StockMovement, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<StockMovement> findByLike(StockMovement entity, int start, int max, SingularAttribute<StockMovement, Object>[] attributes)
	{
		entity = attach(entity);
		return repository.criteriafindBy(entity, attributes).orderDesc(StockMovement_.id).createQuery().setFirstResult(start).setMaxResults(max).getResultList();
	}

	public Long countByLike(StockMovement entity, SingularAttribute<StockMovement, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private StockMovement attach(StockMovement entity)
	{
		if (entity == null)
			return null;

		// aggregated
		entity.setCreatingUser(loginMerger.bindAggregated(entity.getCreatingUser()));

		// aggregated
		entity.setArticle(articleMerger.bindAggregated(entity.getArticle()));

		// aggregated
		entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

		return entity;
	}

	/**
	 *Reduice stock according to moved qty.
	 * 	- 
	 * @param closedDelivery
	 */
	@Inject
	private EntityManager em ;

	public List<StockMovement> periodicalStockMovement (PeriodicalDataSearchInput dataSearchInput){
		Article article = dataSearchInput.getArticle();
		String query ="SELECT s From StockMovement AS  s  WHERE s.id IS NOT NULL ";
		if(StringUtils.isNotBlank(dataSearchInput.getPic()))
			query = query+" AND s.article.pic = :pic ";
		
		if(article!=null && article.getId()!=null) {
			query = query+" AND s.article = :article ";
		}

		if(StringUtils.isNotBlank(dataSearchInput.getInternalPic()))
			query = query+" AND s.internalPic = :internalPic ";

		if(dataSearchInput.getBeginDate()!=null)
			query = query+" AND s.creationDate >= :beginDate ";

		if(dataSearchInput.getEndDate()!=null)
			query = query+" AND s.creationDate <= :endDate ";
		
		Query querys = em.createQuery(query) ;
		
		if(article!=null && article.getId()!=null) {
			querys.setParameter("article", article);
		}

		if(StringUtils.isNotBlank(dataSearchInput.getPic()))
			querys.setParameter("pic", dataSearchInput.getPic());


		if(StringUtils.isNotBlank(dataSearchInput.getInternalPic()))
			querys.setParameter("internalPic", dataSearchInput.getInternalPic());

		if(dataSearchInput.getBeginDate()!=null)
			querys.setParameter("beginDate", dataSearchInput.getBeginDate());

		if(dataSearchInput.getEndDate()!=null)
			querys.setParameter("endDate", dataSearchInput.getEndDate());

		return querys.getResultList();
	}
	public void handleArticlelLotTrashMoved(@Observes ArticleLotMovedToTrashData data){
		ArticleLot articleLot = articleLotEJB.findById(data.getId());
		Article article = articleLot.getArticle();
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		// Generate Stock Movement for article to details
		StockMovement sm = new StockMovement();
		sm.setAgency(creatingUser.getAgency());
		sm.setInternalPic(data.getInternalPic());
		sm.setMovementType(StockMovementType.OUT);
		sm.setArticle(article);
		sm.setCreatingUser(creatingUser);
		sm.setCreationDate(creationDate);
		sm.setInitialQty(articleLot.getStockQuantity().add(data.getQtyToMoved()));
		sm.setMovedQty(data.getQtyToMoved());
		sm.setFinalQty(articleLot.getStockQuantity());
		sm.setStockQty(articleLot.getArticle().getQtyInStock());
		sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
		sm.setMovementDestination(StockMovementTerminal.TRASH);
		String raison = data.getRaison();
		raison = raison+"";
		sm.setRaison(raison.toUpperCase());
		sm.setOriginatedDocNumber("....");
		sm.setTotalPurchasingPrice(articleLot.getPurchasePricePU().multiply(data.getQtyToMoved()));
		if(articleLot.getSalesPricePU()!=null)
			sm.setTotalSalesPrice(articleLot.getSalesPricePU().multiply(data.getQtyToMoved()));
		sm = create(sm);
	}

	public void handleArticleLotDetails(@Observes @DocumentProcessedEvent ArticleLotDetailsManager  lotDetailsManager){
		ArticleLot lotToDetails = lotDetailsManager.getLotToDetails();
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		// Generate Stock Movement for article to details
		StockMovement sm = new StockMovement();
		sm.setAgency(creatingUser.getAgency());
		sm.setInternalPic(lotToDetails.getInternalPic());
		sm.setMovementType(StockMovementType.OUT);
		sm.setArticle(lotToDetails.getArticle());
		sm.setCreatingUser(creatingUser);
		sm.setCreationDate(creationDate);
		sm.setInitialQty(BigDecimal.ZERO);
		sm.setMovedQty(lotDetailsManager.getDetailsQty());
		sm.setFinalQty(lotToDetails.getStockQuantity());
		sm.setStockQty(lotToDetails.getArticle().getQtyInStock());
		sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
		sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
		sm.setOriginatedDocNumber("DETAILS");
		sm.setRaison("DETAIL");
		sm.setTotalPurchasingPrice(lotToDetails.getPurchasePricePU().multiply(lotDetailsManager.getDetailsQty()));
		if(lotToDetails.getSalesPricePU()!=null)
			sm.setTotalSalesPrice(lotToDetails.getSalesPricePU().multiply(lotDetailsManager.getDetailsQty()));
		sm = create(sm);
	}

	public void handleArticleLotTransfer(@Observes @DocumentProcessedEvent ArticleLotTransferManager  lotTransferManager){
		ArticleLot articleLot = lotTransferManager.getLotToTransfer();
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		// Generate Stock Movement for article to details
		StockMovement sm = new StockMovement();
		sm.setAgency(creatingUser.getAgency());
		sm.setInternalPic(articleLot.getInternalPic());
		sm.setMovementType(StockMovementType.OUT);
		sm.setArticle(articleLot.getArticle());
		sm.setCreatingUser(creatingUser);
		sm.setCreationDate(creationDate);
		sm.setInitialQty(BigDecimal.ZERO);
		sm.setRaison("TRANSFERT");
		sm.setMovedQty(lotTransferManager.getQtyToTransfer());
		sm.setFinalQty(articleLot.getStockQuantity());
		sm.setStockQty(articleLot.getArticle().getQtyInStock());
		sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
		sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
		sm.setOriginatedDocNumber("TRANSFER");
		sm.setTotalPurchasingPrice(articleLot.getPurchasePricePU().multiply(lotTransferManager.getQtyToTransfer()));
		if(articleLot.getSalesPricePU()!=null)
			sm.setTotalSalesPrice(articleLot.getSalesPricePU().multiply(lotTransferManager.getQtyToTransfer()));
		sm = create(sm);
	}

	public void handleArticleLotDestocking(@Observes @DestockingProcessedEvent ArticleLotTransferManager  lotTransferManager){
		ArticleLot articleLot = lotTransferManager.getLotToTransfer();
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		// Generate Stock Movement for article to details
		StockMovement sm = new StockMovement();
		sm.setAgency(creatingUser.getAgency());
		sm.setInternalPic(articleLot.getInternalPic());
		sm.setMovementType(StockMovementType.IN);
		sm.setArticle(articleLot.getArticle());
		sm.setCreatingUser(creatingUser);
		sm.setCreationDate(creationDate);
		sm.setInitialQty(BigDecimal.ZERO);
		sm.setMovedQty(lotTransferManager.getQtyToTransfer());
		sm.setFinalQty(articleLot.getStockQuantity());
		sm.setStockQty(articleLot.getArticle().getQtyInStock());
		sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
		sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
		sm.setOriginatedDocNumber("TRANSFER");
		sm.setRaison("TRANSFERT");
		sm.setTotalPurchasingPrice(articleLot.getPurchasePricePU().multiply(lotTransferManager.getQtyToTransfer()));
		if(articleLot.getSalesPricePU()!=null)
			sm.setTotalSalesPrice(articleLot.getSalesPricePU().multiply(lotTransferManager.getQtyToTransfer()));
		sm = create(sm);
	}

	@Inject
	private ApplicationConfiguration applicationConfiguration;

	public void handleDelivery(@Observes @DocumentProcessedEvent Delivery closedDelivery){
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Set<DeliveryItem> deliveryItems = closedDelivery.getDeliveryItems();
		Boolean isManagedLot = Boolean.valueOf( applicationConfiguration.getConfiguration().getProperty("managed_articleLot.config"));
		if(isManagedLot==null) throw new IllegalArgumentException("managed_articleLot.config  is required in application.properties files");
		// Generate Stock Movement for each delivery item
		for (DeliveryItem deliveryItem : deliveryItems) {
			StockMovement sm = new StockMovement();
			sm.setAgency(creatingUser.getAgency());
			sm.setInternalPic(deliveryItem.getMainPic());
			if(isManagedLot)
				sm.setInternalPic(deliveryItem.getInternalPic());
			sm.setMovementType(StockMovementType.IN);
			sm.setArticle(deliveryItem.getArticle());
			sm.setCreatingUser(creatingUser);
			sm.setCreationDate(creationDate);
			sm.setInitialQty(BigDecimal.ZERO);
			sm.setRaison("LIVRAISON");
			sm.setMovedQty(deliveryItem.getStockQuantity());
			sm.setFinalQty(deliveryItem.getStockQuantity());
			sm.setStockQty(deliveryItem.getArticle().getQtyInStock());
			sm.setMovementOrigin(StockMovementTerminal.SUPPLIER);
			sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
			sm.setOriginatedDocNumber(closedDelivery.getDeliveryNumber());
			sm.setTotalPurchasingPrice(deliveryItem.getTotalPurchasePrice());
			if(deliveryItem.getSalesPricePU()!=null && deliveryItem.getStockQuantity()!=null)
				sm.setTotalSalesPrice(deliveryItem.getSalesPricePU().multiply(deliveryItem.getStockQuantity()));
			sm = create(sm);
			stockMovementEvent.fire(sm);
		}
	}

	/**
	 * Create corresponding stock movement.
	 * 
	 * @param salesOrder
	 */
	public void handleSalesClosed(@Observes @DirectSalesClosedEvent SalesOrder salesOrder){
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			StockMovement sm = new StockMovement();
			sm.setAgency(salesOrder.getAgency());
			sm.setInternalPic(salesOrderItem.getInternalPic());
			sm.setMovementType(StockMovementType.OUT);
			sm.setArticle(salesOrderItem.getArticle());
			sm.setCreatingUser(creatingUser);
			sm.setCreationDate(creationDate);
			sm.setRaison("VENTE");
			sm.setInitialQty(BigDecimal.ZERO);//supposed to be qty in stock
			BigDecimal releasedQty = salesOrderItem.getOrderedQty()==null?BigDecimal.ZERO:salesOrderItem.getOrderedQty();
			BigDecimal returnedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();
			BigDecimal movedQty = releasedQty.subtract(returnedQty);
			sm.setMovedQty(movedQty);
			sm.setFinalQty(movedQty);//supposed to be qty in stock.
            sm.setStockQty(salesOrderItem.getArticle().getQtyInStock());
			if(releasedQty.compareTo(BigDecimal.ZERO)>0){
				sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
				sm.setMovementDestination(StockMovementTerminal.CUSTOMER);
			} else if (returnedQty.compareTo(BigDecimal.ZERO)>0){
				sm.setMovementOrigin(StockMovementTerminal.CUSTOMER);
				sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
			} else {
				sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
				sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
			}

			sm.setOriginatedDocNumber(salesOrder.getSoNumber());
			sm.setTotalSalesPrice(salesOrder.getAmountBeforeTax());
			sm.setTotalPurchasingPrice(BigDecimal.ZERO);//TODO ADD purchase price field on salesorder item
			sm = create(sm);
			stockMovementEvent.fire(sm);
		}
	}

	/**
	 * Create corresponding stock movement.
	 * 
	 * @param salesOrder
	 */
	public void handleReturnSales(@Observes @ReturnSalesTraceEvent SalesOrder salesOrder){
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			if(salesOrderItem.hasReturnArticle()){
				StockMovement sm = new StockMovement();
				sm.setAgency(salesOrder.getAgency());
				sm.setInternalPic(salesOrderItem.getInternalPic());
				sm.setMovementType(StockMovementType.IN);
				sm.setArticle(salesOrderItem.getArticle());
				sm.setCreatingUser(creatingUser);
				sm.setCreationDate(creationDate);
				sm.setRaison("RETOUR CLIENT");
				sm.setInitialQty(BigDecimal.ZERO);//supposed to be qty in stock
				BigDecimal movedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();
				sm.setMovedQty(movedQty);
				sm.setFinalQty(movedQty);//supposed to be qty in stock.
				sm.setStockQty(salesOrderItem.getArticle().getQtyInStock());
				sm.setMovementOrigin(StockMovementTerminal.CUSTOMER);
				sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
				sm.setOriginatedDocNumber(salesOrder.getSoNumber());
				sm.setTotalSalesPrice(salesOrderItem.getSalesPricePU().multiply(movedQty));
				sm.setTotalPurchasingPrice(BigDecimal.ZERO);//TODO ADD purchase price field on salesorder item
				sm = create(sm);
			}

		}
	}

	/**
	 * Sales order canceled, create corresponding stock movement.
	 * 
	 * Canceling will not delete the original stock movement, but create a new one
	 * to neutralize the effect of the old one.
	 * 
	 * @param salesOrder
   public void handleSalesCanceled(@Observes @DocumentCanceledEvent SalesOrder salesOrder){
		Login creatingUser = securityUtil.getConnectedUser();
		Date creationDate = new Date();
		Set<SalesOrderItem> salesOrderItems = salesOrder.getSalesOrderItems();

		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			StockMovement sm = new StockMovement();
			sm.setAgency(salesOrder.getAgency());
			sm.setInternalPic(salesOrderItem.getInternalPic());
			sm.setMovementType(StockMovementType.IN);// return article in the stock.
			sm.setArticle(salesOrderItem.getArticle());
			sm.setCreatingUser(creatingUser);
			sm.setCreationDate(creationDate);
			sm.setInitialQty(BigDecimal.ZERO);//supposed to be qty in stock
			BigDecimal releasedQty = salesOrderItem.getOrderedQty()==null?BigDecimal.ZERO:salesOrderItem.getOrderedQty();
			BigDecimal returnedQty = salesOrderItem.getReturnedQty()==null?BigDecimal.ZERO:salesOrderItem.getReturnedQty();
			BigDecimal movedQty = returnedQty.subtract(releasedQty);// Return minus release. inverse of sales operation.
			sm.setMovedQty(movedQty);
			sm.setFinalQty(movedQty);//supposed to be qty in stock.

			if(releasedQty.compareTo(BigDecimal.ZERO)>0){
				sm.setMovementOrigin(StockMovementTerminal.CUSTOMER);
				sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
			} else if (returnedQty.compareTo(BigDecimal.ZERO)>0){
				sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
				sm.setMovementDestination(StockMovementTerminal.CUSTOMER);
			} else {
				sm.setMovementOrigin(StockMovementTerminal.WAREHOUSE);
				sm.setMovementDestination(StockMovementTerminal.WAREHOUSE);
			}

			sm.setOriginatedDocNumber(salesOrder.getSoNumber());
			sm.setTotalSalesPrice(salesOrder.getAmountBeforeTax());
			sm.setTotalPurchasingPrice(BigDecimal.ZERO);//TODO ADD purchase price field on sales order item
			sm = create(sm);
			stockMovementEvent.fire(sm);
		}
	}
	 */
}
