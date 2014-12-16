package org.adorsys.adpharma.client.jpa.article;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ArticleDetailsSearchResult {
	
	/*
	    * The number of entities matching this search.
	    */
	   private Long count;
	   
	   /*
	    * The result list.
	    */
	   private List<ArticleDetails> resultList;
	   
	   /*
	    * The original search input object. For stateless clients.
	    */
	   private ArticleSearchInput searchInput;
	   
	   
	   

	public ArticleDetailsSearchResult() {
		super();
	}

	public ArticleDetailsSearchResult(Long count,
			List<ArticleDetails> resultList) {
		super();
		this.count = count;
		this.resultList = resultList;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<ArticleDetails> getResultList() {
		return resultList;
	}

	public void setResultList(List<ArticleDetails> resultList) {
		this.resultList = resultList;
	}

	public ArticleSearchInput getSearchInput() {
		return searchInput;
	}

	public void setSearchInput(ArticleSearchInput searchInput) {
		this.searchInput = searchInput;
	}
	   
	   

}
