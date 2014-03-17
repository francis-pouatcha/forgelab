package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CurrencySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Currency> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CurrencySearchInput searchInput;

   public CurrencySearchResult()
   {
      super();
   }

   public CurrencySearchResult(Long count, List<Currency> resultList,
         CurrencySearchInput searchInput)
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

   public List<Currency> getResultList()
   {
      return resultList;
   }

   public CurrencySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Currency> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CurrencySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
