package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArticleEquivalenceSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<ArticleEquivalence> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private ArticleEquivalenceSearchInput searchInput;

   public ArticleEquivalenceSearchResult()
   {
      super();
   }

   public ArticleEquivalenceSearchResult(Long count, List<ArticleEquivalence> resultList,
         ArticleEquivalenceSearchInput searchInput)
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

   public List<ArticleEquivalence> getResultList()
   {
      return resultList;
   }

   public ArticleEquivalenceSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<ArticleEquivalence> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(ArticleEquivalenceSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
