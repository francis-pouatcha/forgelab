package org.adorsys.adpharma.client.jpa.customerinvoice;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CustomerInvoiceSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<CustomerInvoice> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private CustomerInvoiceSearchInput searchInput;

   public CustomerInvoiceSearchResult()
   {
      super();
   }

   public CustomerInvoiceSearchResult(Long count, List<CustomerInvoice> resultList,
         CustomerInvoiceSearchInput searchInput)
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

   public List<CustomerInvoice> getResultList()
   {
      return resultList;
   }

   public CustomerInvoiceSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<CustomerInvoice> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(CustomerInvoiceSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
