package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductDetailConfigSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<ProductDetailConfig> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private ProductDetailConfigSearchInput searchInput;

   public ProductDetailConfigSearchResult()
   {
      super();
   }

   public ProductDetailConfigSearchResult(Long count, List<ProductDetailConfig> resultList,
         ProductDetailConfigSearchInput searchInput)
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

   public List<ProductDetailConfig> getResultList()
   {
      return resultList;
   }

   public ProductDetailConfigSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<ProductDetailConfig> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(ProductDetailConfigSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
