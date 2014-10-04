package org.adorsys.adpharma.server.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adpharma.server.jpa.PeriodicalPrescriptionBookDataSearchInput;
import org.adorsys.adpharma.server.jpa.PrescriptionBook;
import org.adorsys.adpharma.server.repo.PrescriptionBookRepository;

@Stateless
public class PrescriptionBookEJB
{

   @Inject
   private PrescriptionBookRepository repository;

   @Inject
   private LoginMerger loginMerger;

   @Inject
   private SalesOrderMerger salesOrderMerger;

   @Inject
   private HospitalMerger hospitalMerger;

   @Inject
   private PrescriberMerger prescriberMerger;

   @Inject
   private AgencyMerger agencyMerger;
   
   @Inject
   private EntityManager em ;

   public PrescriptionBook create(PrescriptionBook entity)
   {
      return repository.save(attach(entity));
   }

   public PrescriptionBook deleteById(Long id)
   {
      PrescriptionBook entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrescriptionBook update(PrescriptionBook entity)
   {
      return repository.save(attach(entity));
   }

   public PrescriptionBook findById(Long id)
   {
      return repository.findBy(id);
   }

   public List<PrescriptionBook> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrescriptionBook> findBy(PrescriptionBook entity, int start, int max, SingularAttribute<PrescriptionBook, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrescriptionBook entity, SingularAttribute<PrescriptionBook, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrescriptionBook> findByLike(PrescriptionBook entity, int start, int max, SingularAttribute<PrescriptionBook, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrescriptionBook entity, SingularAttribute<PrescriptionBook, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }
   
   
   // Get Periodical Prescription book
   public List<PrescriptionBook> periodicalPrescriptionBook(PeriodicalPrescriptionBookDataSearchInput data){
	   Date beginDate = data.getBeginDate();
	   Date endDate = data.getEndDate();
	   StringBuilder query = new StringBuilder("SELECT p FROM PrescriptionBook AS p WHERE p.id IS NOT NULL AND p.salesOrder.cashed = :cashed");
	   if(beginDate!=null) {
		   query.append(" AND p.recordingDate >= :begin");
	   }
	   if(endDate!=null) {
		   query.append(" AND p.recordingDate <= :end");
	   }
	   TypedQuery<PrescriptionBook> createQuery = em.createQuery(query.toString(), PrescriptionBook.class);
	   createQuery.setParameter("cashed", Boolean.TRUE);
	   if(beginDate!=null) {
		   createQuery.setParameter("begin", beginDate);
	   }
	   if(endDate!=null) {
		   createQuery.setParameter("end", endDate);
	   }
	   List<PrescriptionBook> resultList = createQuery.getResultList();
	   return resultList;
   }
   

   private PrescriptionBook attach(PrescriptionBook entity)
   {
      if (entity == null)
         return null;

      // aggregated
      entity.setPrescriber(prescriberMerger.bindAggregated(entity.getPrescriber()));

      // aggregated
      entity.setHospital(hospitalMerger.bindAggregated(entity.getHospital()));

      // aggregated
      entity.setAgency(agencyMerger.bindAggregated(entity.getAgency()));

      // aggregated
      entity.setRecordingAgent(loginMerger.bindAggregated(entity.getRecordingAgent()));

      // aggregated
      entity.setSalesOrder(salesOrderMerger.bindAggregated(entity.getSalesOrder()));

      return entity;
   }
}
