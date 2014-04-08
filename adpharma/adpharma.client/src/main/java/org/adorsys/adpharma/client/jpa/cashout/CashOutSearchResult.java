package org.adorsys.adpharma.client.jpa.cashout;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CashOutSearchResult {
	  /*
	    * The number of entities matching this search.
	    */
	   private Long count;

	   /*
	    * The result list.
	    */
	   private List<CashOut> resultList;

	   /*
	    * The original search input object. For stateless clients.
	    */
	   private CashOutSearchInput searchInput;

	   public CashOutSearchResult()
	   {
	      super();
	   }

	   public CashOutSearchResult(Long count, List<CashOut> resultList,
			   CashOutSearchInput searchInput)
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

	   public List<CashOut> getResultList()
	   {
	      return resultList;
	   }

	   public CashOutSearchInput getSearchInput()
	   {
	      return searchInput;
	   }

	   public void setCount(Long count)
	   {
	      this.count = count;
	   }

	   public void setResultList(List<CashOut> resultList)
	   {
	      this.resultList = resultList;
	   }

	   public void setSearchInput(CashOutSearchInput searchInput)
	   {
	      this.searchInput = searchInput;
	   }
}
