package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CashDrawerSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CashDrawer> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CashDrawerSearchInput searchInput;

   public CashDrawerSearchResult()
   {
      super();
   }

   public CashDrawerSearchResult(Long count, List<CashDrawer> resultList,
         CashDrawerSearchInput searchInput)
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

   public List<CashDrawer> getResultList()
   {
      return resultList;
   }

   public CashDrawerSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CashDrawer> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CashDrawerSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
