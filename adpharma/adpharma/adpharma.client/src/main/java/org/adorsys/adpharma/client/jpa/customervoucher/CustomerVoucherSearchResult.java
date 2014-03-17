package org.adorsys.adpharma.client.jpa.customervoucher;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CustomerVoucherSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CustomerVoucher> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CustomerVoucherSearchInput searchInput;

   public CustomerVoucherSearchResult()
   {
      super();
   }

   public CustomerVoucherSearchResult(Long count, List<CustomerVoucher> resultList,
         CustomerVoucherSearchInput searchInput)
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

   public List<CustomerVoucher> getResultList()
   {
      return resultList;
   }

   public CustomerVoucherSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CustomerVoucher> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CustomerVoucherSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
