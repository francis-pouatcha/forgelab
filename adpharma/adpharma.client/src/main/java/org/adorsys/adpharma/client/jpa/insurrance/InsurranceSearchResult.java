package org.adorsys.adpharma.client.jpa.insurrance;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class InsurranceSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Insurrance> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private InsurranceSearchInput searchInput;

   public InsurranceSearchResult()
   {
      super();
   }

   public InsurranceSearchResult(Long count, List<Insurrance> resultList,
         InsurranceSearchInput searchInput)
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

   public List<Insurrance> getResultList()
   {
      return resultList;
   }

   public InsurranceSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Insurrance> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(InsurranceSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
