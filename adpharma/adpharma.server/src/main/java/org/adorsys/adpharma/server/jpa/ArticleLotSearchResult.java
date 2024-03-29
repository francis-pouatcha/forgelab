package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArticleLotSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<ArticleLot> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private ArticleLotSearchInput searchInput;

   public ArticleLotSearchResult()
   {
      super();
   }

   public ArticleLotSearchResult(Long count, List<ArticleLot> resultList,
         ArticleLotSearchInput searchInput)
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

   public List<ArticleLot> getResultList()
   {
      return resultList;
   }

   public ArticleLotSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<ArticleLot> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(ArticleLotSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
