package org.adorsys.adpharma.client.jpa.hospital;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class HospitalSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Hospital> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private HospitalSearchInput searchInput;

   public HospitalSearchResult()
   {
      super();
   }

   public HospitalSearchResult(Long count, List<Hospital> resultList,
         HospitalSearchInput searchInput)
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

   public List<Hospital> getResultList()
   {
      return resultList;
   }

   public HospitalSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Hospital> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(HospitalSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
