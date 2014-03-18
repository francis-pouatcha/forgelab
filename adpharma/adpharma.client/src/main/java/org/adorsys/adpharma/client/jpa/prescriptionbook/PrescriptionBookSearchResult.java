package org.adorsys.adpharma.client.jpa.prescriptionbook;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PrescriptionBookSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<PrescriptionBook> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PrescriptionBookSearchInput searchInput;

   public PrescriptionBookSearchResult()
   {
      super();
   }

   public PrescriptionBookSearchResult(Long count, List<PrescriptionBook> resultList,
         PrescriptionBookSearchInput searchInput)
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

   public List<PrescriptionBook> getResultList()
   {
      return resultList;
   }

   public PrescriptionBookSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<PrescriptionBook> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PrescriptionBookSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
