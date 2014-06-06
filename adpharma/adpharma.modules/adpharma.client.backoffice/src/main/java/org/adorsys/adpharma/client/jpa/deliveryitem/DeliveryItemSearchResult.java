package org.adorsys.adpharma.client.jpa.deliveryitem;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DeliveryItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<DeliveryItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private DeliveryItemSearchInput searchInput;

   public DeliveryItemSearchResult()
   {
      super();
   }

   public DeliveryItemSearchResult(Long count, List<DeliveryItem> resultList,
         DeliveryItemSearchInput searchInput)
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

   public List<DeliveryItem> getResultList()
   {
      return resultList;
   }

   public DeliveryItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<DeliveryItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(DeliveryItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
