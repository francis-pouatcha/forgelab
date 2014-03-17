package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArticleSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<Article> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private ArticleSearchInput searchInput;

   public ArticleSearchResult()
   {
      super();
   }

   public ArticleSearchResult(Long count, List<Article> resultList,
         ArticleSearchInput searchInput)
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

   public List<Article> getResultList()
   {
      return resultList;
   }

   public ArticleSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<Article> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(ArticleSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
