package org.adorsys.adpharma.client.jpa.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class WareHouseSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<WareHouse> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private WareHouseSearchInput searchInput;

   public WareHouseSearchResult()
   {
      super();
   }

   public WareHouseSearchResult(Long count, List<WareHouse> resultList,
         WareHouseSearchInput searchInput)
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

   public List<WareHouse> getResultList()
   {
      return resultList;
   }

   public WareHouseSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<WareHouse> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(WareHouseSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
