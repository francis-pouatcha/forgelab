package org.adorsys.adpharma.client.jpa.debtstatement;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DebtStatementSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<DebtStatement> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private DebtStatementSearchInput searchInput;

   public DebtStatementSearchResult()
   {
      super();
   }

   public DebtStatementSearchResult(Long count, List<DebtStatement> resultList,
         DebtStatementSearchInput searchInput)
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

   public List<DebtStatement> getResultList()
   {
      return resultList;
   }

   public DebtStatementSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<DebtStatement> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(DebtStatementSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
