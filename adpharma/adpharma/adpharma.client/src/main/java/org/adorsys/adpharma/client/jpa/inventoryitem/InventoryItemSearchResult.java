package org.adorsys.adpharma.client.jpa.inventoryitem;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class InventoryItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<InventoryItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private InventoryItemSearchInput searchInput;

   public InventoryItemSearchResult()
   {
      super();
   }

   public InventoryItemSearchResult(Long count, List<InventoryItem> resultList,
         InventoryItemSearchInput searchInput)
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

   public List<InventoryItem> getResultList()
   {
      return resultList;
   }

   public InventoryItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<InventoryItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(InventoryItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
