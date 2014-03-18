package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerCategorySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CustomerCategory> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CustomerCategorySearchInput searchInput;

   public CustomerCategorySearchResult()
   {
      super();
   }

   public CustomerCategorySearchResult(Long count, List<CustomerCategory> resultList,
         CustomerCategorySearchInput searchInput)
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

   public List<CustomerCategory> getResultList()
   {
      return resultList;
   }

   public CustomerCategorySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CustomerCategory> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CustomerCategorySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
