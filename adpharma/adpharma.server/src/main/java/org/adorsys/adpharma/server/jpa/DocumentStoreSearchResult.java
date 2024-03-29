package org.adorsys.adpharma.server.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DocumentStoreSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<DocumentStore> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private DocumentStoreSearchInput searchInput;

   public DocumentStoreSearchResult()
   {
      super();
   }

   public DocumentStoreSearchResult(Long count, List<DocumentStore> resultList,
         DocumentStoreSearchInput searchInput)
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

   public List<DocumentStore> getResultList()
   {
      return resultList;
   }

   public DocumentStoreSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<DocumentStore> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(DocumentStoreSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
