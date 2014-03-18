package org.adorsys.adpharma.client.jpa.customer;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CustomerSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Customer> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CustomerSearchInput searchInput;

   public CustomerSearchResult()
   {
      super();
   }

   public CustomerSearchResult(Long count, List<Customer> resultList,
         CustomerSearchInput searchInput)
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

   public List<Customer> getResultList()
   {
      return resultList;
   }

   public CustomerSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Customer> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CustomerSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
