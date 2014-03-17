package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProcurementOrderSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<ProcurementOrder> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private ProcurementOrderSearchInput searchInput;

   public ProcurementOrderSearchResult()
   {
      super();
   }

   public ProcurementOrderSearchResult(Long count, List<ProcurementOrder> resultList,
         ProcurementOrderSearchInput searchInput)
   {
      super();
      this.count = count;
      this.resultList = resultList;
      this.searchInput = searchInput;
   }

   public Long getCount()
   {
      return count;
   }

   public List<ProcurementOrder> getResultList()
   {
      return resultList;
   }

   public ProcurementOrderSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<ProcurementOrder> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(ProcurementOrderSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
