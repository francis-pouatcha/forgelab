package org.adorsys.adpharma.client.jpa.currency;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
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
