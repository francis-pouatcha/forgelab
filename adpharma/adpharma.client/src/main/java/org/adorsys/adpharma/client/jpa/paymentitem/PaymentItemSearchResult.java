package org.adorsys.adpharma.client.jpa.paymentitem;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PaymentItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<PaymentItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private PaymentItemSearchInput searchInput;

   public PaymentItemSearchResult()
   {
      super();
   }

   public PaymentItemSearchResult(Long count, List<PaymentItem> resultList,
         PaymentItemSearchInput searchInput)
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

   public List<PaymentItem> getResultList()
   {
      return resultList;
   }

   public PaymentItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<PaymentItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(PaymentItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
