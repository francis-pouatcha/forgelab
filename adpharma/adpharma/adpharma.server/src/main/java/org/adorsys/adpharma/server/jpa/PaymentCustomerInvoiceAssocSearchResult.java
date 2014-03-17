package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaymentCustomerInvoiceAssocSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<PaymentCustomerInvoiceAssoc> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PaymentCustomerInvoiceAssocSearchInput searchInput;

   public PaymentCustomerInvoiceAssocSearchResult()
   {
      super();
   }

   public PaymentCustomerInvoiceAssocSearchResult(Long count, List<PaymentCustomerInvoiceAssoc> resultList,
         PaymentCustomerInvoiceAssocSearchInput searchInput)
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

   public List<PaymentCustomerInvoiceAssoc> getResultList()
   {
      return resultList;
   }

   public PaymentCustomerInvoiceAssocSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<PaymentCustomerInvoiceAssoc> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PaymentCustomerInvoiceAssocSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
