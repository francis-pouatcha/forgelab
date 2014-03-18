package org.adorsys.adpharma.client.jpa.agency;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AgencySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Agency> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private AgencySearchInput searchInput;

   public AgencySearchResult()
   {
      super();
   }

   public AgencySearchResult(Long count, List<Agency> resultList,
         AgencySearchInput searchInput)
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

   public List<Agency> getResultList()
   {
      return resultList;
   }

   public AgencySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Agency> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(AgencySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
