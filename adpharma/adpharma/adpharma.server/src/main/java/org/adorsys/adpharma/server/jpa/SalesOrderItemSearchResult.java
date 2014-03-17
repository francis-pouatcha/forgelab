package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SalesOrderItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SalesOrderItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SalesOrderItemSearchInput searchInput;

   public SalesOrderItemSearchResult()
   {
      super();
   }

   public SalesOrderItemSearchResult(Long count, List<SalesOrderItem> resultList,
         SalesOrderItemSearchInput searchInput)
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

   public List<SalesOrderItem> getResultList()
   {
      return resultList;
   }

   public SalesOrderItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SalesOrderItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SalesOrderItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
