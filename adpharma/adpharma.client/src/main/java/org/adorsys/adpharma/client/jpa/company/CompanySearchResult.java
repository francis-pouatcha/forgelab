package org.adorsys.adpharma.client.jpa.company;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CompanySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Company> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CompanySearchInput searchInput;

   public CompanySearchResult()
   {
      super();
   }

   public CompanySearchResult(Long count, List<Company> resultList,
         CompanySearchInput searchInput)
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

   public List<Company> getResultList()
   {
      return resultList;
   }

   public CompanySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Company> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CompanySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
