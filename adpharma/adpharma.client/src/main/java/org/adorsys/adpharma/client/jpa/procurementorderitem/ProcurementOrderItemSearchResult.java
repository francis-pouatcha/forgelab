package org.adorsys.adpharma.client.jpa.procurementorderitem;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ProcurementOrderItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<ProcurementOrderItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private ProcurementOrderItemSearchInput searchInput;

   public ProcurementOrderItemSearchResult()
   {
      super();
   }

   public ProcurementOrderItemSearchResult(Long count, List<ProcurementOrderItem> resultList,
         ProcurementOrderItemSearchInput searchInput)
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

   public List<ProcurementOrderItem> getResultList()
   {
      return resultList;
   }

   public ProcurementOrderItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<ProcurementOrderItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(ProcurementOrderItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
