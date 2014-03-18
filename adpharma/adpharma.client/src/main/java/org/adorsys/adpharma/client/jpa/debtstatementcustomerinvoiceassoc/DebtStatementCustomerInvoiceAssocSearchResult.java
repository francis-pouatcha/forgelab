package org.adorsys.adpharma.client.jpa.debtstatementcustomerinvoiceassoc;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DebtStatementCustomerInvoiceAssocSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<DebtStatementCustomerInvoiceAssoc> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private DebtStatementCustomerInvoiceAssocSearchInput searchInput;

   public DebtStatementCustomerInvoiceAssocSearchResult()
   {
      super();
   }

   public DebtStatementCustomerInvoiceAssocSearchResult(Long count, List<DebtStatementCustomerInvoiceAssoc> resultList,
         DebtStatementCustomerInvoiceAssocSearchInput searchInput)
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

   public List<DebtStatementCustomerInvoiceAssoc> getResultList()
   {
      return resultList;
   }

   public DebtStatementCustomerInvoiceAssocSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<DebtStatementCustomerInvoiceAssoc> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(DebtStatementCustomerInvoiceAssocSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
