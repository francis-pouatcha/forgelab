package org.adorsys.adpharma.client.jpa.supplierinvoiceitem;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SupplierInvoiceItemSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SupplierInvoiceItem> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SupplierInvoiceItemSearchInput searchInput;

   public SupplierInvoiceItemSearchResult()
   {
      super();
   }

   public SupplierInvoiceItemSearchResult(Long count, List<SupplierInvoiceItem> resultList,
         SupplierInvoiceItemSearchInput searchInput)
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

   public List<SupplierInvoiceItem> getResultList()
   {
      return resultList;
   }

   public SupplierInvoiceItemSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SupplierInvoiceItem> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SupplierInvoiceItemSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
