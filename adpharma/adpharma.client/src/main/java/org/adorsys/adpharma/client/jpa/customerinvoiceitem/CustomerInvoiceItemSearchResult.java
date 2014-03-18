package org.adorsys.adpharma.client.jpa.customerinvoiceitem;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CustomerInvoiceItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CustomerInvoiceItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CustomerInvoiceItemSearchInput searchInput;

   public CustomerInvoiceItemSearchResult()
   {
      super();
   }

   public CustomerInvoiceItemSearchResult(Long count, List<CustomerInvoiceItem> resultList,
         CustomerInvoiceItemSearchInput searchInput)
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

   public List<CustomerInvoiceItem> getResultList()
   {
      return resultList;
   }

   public CustomerInvoiceItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CustomerInvoiceItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CustomerInvoiceItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
