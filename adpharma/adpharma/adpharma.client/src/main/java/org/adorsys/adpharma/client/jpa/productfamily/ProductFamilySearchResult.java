package org.adorsys.adpharma.client.jpa.productfamily;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ProductFamilySearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<ProductFamily> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private ProductFamilySearchInput searchInput;

   public ProductFamilySearchResult()
   {
      super();
   }

   public ProductFamilySearchResult(Long count, List<ProductFamily> resultList,
         ProductFamilySearchInput searchInput)
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

   public List<ProductFamily> getResultList()
   {
      return resultList;
   }

   public ProductFamilySearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<ProductFamily> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(ProductFamilySearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
