package org.adorsys.adpharma.client.jpa.prescriber;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PrescriberSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Prescriber> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PrescriberSearchInput searchInput;

   public PrescriberSearchResult()
   {
      super();
   }

   public PrescriberSearchResult(Long count, List<Prescriber> resultList,
         PrescriberSearchInput searchInput)
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

   public List<Prescriber> getResultList()
   {
      return resultList;
   }

   public PrescriberSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Prescriber> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PrescriberSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
