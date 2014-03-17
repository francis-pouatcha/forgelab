package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmployerSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Employer> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private EmployerSearchInput searchInput;

   public EmployerSearchResult()
   {
      super();
   }

   public EmployerSearchResult(Long count, List<Employer> resultList,
         EmployerSearchInput searchInput)
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

   public List<Employer> getResultList()
   {
      return resultList;
   }

   public EmployerSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Employer> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(EmployerSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
