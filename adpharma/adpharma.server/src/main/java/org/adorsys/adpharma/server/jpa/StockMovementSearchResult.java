package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StockMovementSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<StockMovement> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private StockMovementSearchInput searchInput;

   public StockMovementSearchResult()
   {
      super();
   }

   public StockMovementSearchResult(Long count, List<StockMovement> resultList,
         StockMovementSearchInput searchInput)
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

   public List<StockMovement> getResultList()
   {
      return resultList;
   }

   public StockMovementSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<StockMovement> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(StockMovementSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
