package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InventorySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Inventory> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private InventorySearchInput searchInput;

   public InventorySearchResult()
   {
      super();
   }

   public InventorySearchResult(Long count, List<Inventory> resultList,
         InventorySearchInput searchInput)
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

   public List<Inventory> getResultList()
   {
      return resultList;
   }

   public InventorySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Inventory> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(InventorySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
