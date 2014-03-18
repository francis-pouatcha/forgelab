package org.adorsys.adpharma.client.jpa.supplierinvoice;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SupplierInvoiceSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<SupplierInvoice> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private SupplierInvoiceSearchInput searchInput;

   public SupplierInvoiceSearchResult()
   {
      super();
   }

   public SupplierInvoiceSearchResult(Long count, List<SupplierInvoice> resultList,
         SupplierInvoiceSearchInput searchInput)
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

   public List<SupplierInvoice> getResultList()
   {
      return resultList;
   }

   public SupplierInvoiceSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<SupplierInvoice> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(SupplierInvoiceSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
