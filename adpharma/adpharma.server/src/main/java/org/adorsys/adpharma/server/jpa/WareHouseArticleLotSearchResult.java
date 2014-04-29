package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WareHouseArticleLotSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<WareHouseArticleLot> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private WareHouseArticleLotSearchInput searchInput;

   public WareHouseArticleLotSearchResult()
   {
      super();
   }

   public WareHouseArticleLotSearchResult(Long count, List<WareHouseArticleLot> resultList,
         WareHouseArticleLotSearchInput searchInput)
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

   public List<WareHouseArticleLot> getResultList()
   {
      return resultList;
   }

   public WareHouseArticleLotSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<WareHouseArticleLot> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(WareHouseArticleLotSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
