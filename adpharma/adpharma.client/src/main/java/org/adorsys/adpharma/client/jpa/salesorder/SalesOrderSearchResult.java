package org.adorsys.adpharma.client.jpa.salesorder;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SalesOrderSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SalesOrder> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SalesOrderSearchInput searchInput;

   public SalesOrderSearchResult()
   {
      super();
   }

   public SalesOrderSearchResult(Long count, List<SalesOrder> resultList,
         SalesOrderSearchInput searchInput)
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

   public List<SalesOrder> getResultList()
   {
      return resultList;
   }

   public SalesOrderSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SalesOrder> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SalesOrderSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
