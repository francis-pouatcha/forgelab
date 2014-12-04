package org.adorsys.adpharma.server.utils;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.adorsys.adpharma.server.jpa.Article;
import org.adorsys.adpharma.server.jpa.Section;
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PeriodicalDataSearchInput {

	private Date beginDate;

	private Date endDate;

	private Boolean check;
	
	private Boolean printXls;

	private Boolean taxableSalesOnly = Boolean.FALSE ;

	private Boolean nonTaxableSalesOnly = Boolean.FALSE ;

	private Boolean twentyOverHeightySalesOnly = Boolean.FALSE ;
	
	private Boolean twentyOverHeightyInQty = Boolean.FALSE ;
	
	private Boolean perVendorAndDiscount;
	
	private Section section;
	
	private Article article;

	private String pic;
	
	private String articleName;
	
	private String internalPic;
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getCheck() {
		return check;
	}

	public Boolean getTaxableSalesOnly() {
		return taxableSalesOnly;
	}

	public Boolean getNonTaxableSalesOnly() {
		return nonTaxableSalesOnly;
	}


	public void setCheck(Boolean check) {
		this.check = check;
	}

	public Boolean getTwentyOverHeightySalesOnly() {
		return twentyOverHeightySalesOnly;
	}

	public void setTwentyOverHeightySalesOnly(Boolean twentyOverHeightySalesOnly) {
		this.twentyOverHeightySalesOnly = twentyOverHeightySalesOnly;
	}
	
	

	public Boolean getTwentyOverHeightyInQty() {
		return twentyOverHeightyInQty;
	}

	public void setTwentyOverHeightyInQty(Boolean twentyOverHeightyInQty) {
		this.twentyOverHeightyInQty = twentyOverHeightyInQty;
	}

	public void setTaxableSalesOnly(Boolean taxableSalesOnly) {
		this.taxableSalesOnly = taxableSalesOnly;
	}

	public void setNonTaxableSalesOnly(Boolean nonTaxableSalesOnly) {
		this.nonTaxableSalesOnly = nonTaxableSalesOnly;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getArticleName() {
		return articleName;
	}

	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	public String getInternalPic() {
		return internalPic;
	}

	public void setInternalPic(String internalPic) {
		this.internalPic = internalPic;
	}
	
	public Boolean getPerVendorAndDiscount() {
		return perVendorAndDiscount;
	}
	
	public void setPerVendorAndDiscount(Boolean perVendorAndDiscount) {
		this.perVendorAndDiscount = perVendorAndDiscount;
	}

   public Boolean getPrintXls() {
	return printXls; 
    }
   
   public void setPrintXls(Boolean printXls) {
	this.printXls = printXls;
   }

	public Section getSection() {
		return section;
	}
	
	public void setSection(Section section) {
		this.section = section;
	}
   
   public Article getArticle() {
	return article;
    }
   
   public void setArticle(Article article) {
	this.article = article;
   }
   

}
