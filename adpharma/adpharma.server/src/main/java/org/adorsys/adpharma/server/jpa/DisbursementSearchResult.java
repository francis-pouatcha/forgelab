package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DisbursementSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Disbursement> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private DisbursementSearchInput searchInput;

   public DisbursementSearchResult()
   {
      super();
   }

   public DisbursementSearchResult(Long count, List<Disbursement> resultList,
         DisbursementSearchInput searchInput)
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

   public List<Disbursement> getResultList()
   {
      return resultList;
   }

   public DisbursementSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Disbursement> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(DisbursementSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
