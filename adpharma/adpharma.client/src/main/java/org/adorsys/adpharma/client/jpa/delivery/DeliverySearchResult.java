package org.adorsys.adpharma.client.jpa.delivery;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DeliverySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Delivery> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private DeliverySearchInput searchInput;

   public DeliverySearchResult()
   {
      super();
   }

   public DeliverySearchResult(Long count, List<Delivery> resultList,
         DeliverySearchInput searchInput)
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

   public List<Delivery> getResultList()
   {
      return resultList;
   }

   public DeliverySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Delivery> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(DeliverySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
