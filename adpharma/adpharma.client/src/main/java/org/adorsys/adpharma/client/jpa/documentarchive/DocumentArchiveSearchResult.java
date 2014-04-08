package org.adorsys.adpharma.client.jpa.documentarchive;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DocumentArchiveSearchResult
{

   /*
    * The number of entities matching this search.
    */
   private Long count;

   /*
    * The result list.
    */
   private List<DocumentArchive> resultList;

   /*
    * The original search input object. For stateless clients.
    */
   private DocumentArchiveSearchInput searchInput;

   public DocumentArchiveSearchResult()
   {
      super();
   }

   public DocumentArchiveSearchResult(Long count, List<DocumentArchive> resultList,
         DocumentArchiveSearchInput searchInput)
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

   public List<DocumentArchive> getResultList()
   {
      return resultList;
   }

   public DocumentArchiveSearchInput getSearchInput()
   {
      return searchInput;
   }

   public void setCount(Long count)
   {
      this.count = count;
   }

   public void setResultList(List<DocumentArchive> resultList)
   {
      this.resultList = resultList;
   }

   public void setSearchInput(DocumentArchiveSearchInput searchInput)
   {
      this.searchInput = searchInput;
   }

}
