package org.adorsys.adpharma.client.jpa.supplier;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SupplierSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Supplier> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SupplierSearchInput searchInput;

   public SupplierSearchResult()
   {
      super();
   }

   public SupplierSearchResult(Long count, List<Supplier> resultList,
         SupplierSearchInput searchInput)
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

   public List<Supplier> getResultList()
   {
      return resultList;
   }

   public SupplierSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Supplier> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SupplierSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
