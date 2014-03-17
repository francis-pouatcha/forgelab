package org.adorsys.adpharma.client.jpa.payment;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PaymentSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Payment> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PaymentSearchInput searchInput;

   public PaymentSearchResult()
   {
      super();
   }

   public PaymentSearchResult(Long count, List<Payment> resultList,
         PaymentSearchInput searchInput)
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

   public List<Payment> getResultList()
   {
      return resultList;
   }

   public PaymentSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Payment> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PaymentSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
