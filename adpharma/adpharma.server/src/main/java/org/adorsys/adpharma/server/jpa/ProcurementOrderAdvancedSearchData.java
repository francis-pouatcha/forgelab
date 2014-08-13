package org.adorsys.adpharma.server.jpa;

import java.util.Calendar;

public class ProcurementOrderAdvancedSearchData {
	

	private Calendar from;
	
	private Calendar to;
	
	private DocumentProcessingState  status;
	
	private String orderNumber;
	
	private Login user;
	
	private Supplier supplier;
	
	
   public Calendar getFrom() {
	return from;
   }
   
   public Calendar getTo() { 	 	
	return to;
   }
   
   public String getOrderNumber() {
	return orderNumber;
   }
   
   public Supplier getSupplier() {
	return supplier;
   }
   
   public void setSupplier(Supplier supplier) {
	this.supplier = supplier;
   } 
   
   public DocumentProcessingState getStatus() {
	return status;
   }
   
   public Login getUser() {
	return user;
   }
   
   public void setFrom(Calendar from) {
	this.from = from;
   }
   
   public void setTo(Calendar to) {
	this.to = to;
   }
   
   public void setUser(Login user) {
	this.user = user;
   }
   
   public void setStatus(DocumentProcessingState status) {
	this.status = status;
   }
   
   public void setOrderNumber(String orderNumber) {
	   this.orderNumber = orderNumber;
   }
   

   
   @Override
   public String toString() {
	   return "ProcurementOrderAdvancedSearchData: [ From Date= "+from
			   +" ,To Date= "+to+" ,Status= "+status+" ,Order Number= "+orderNumber+" ,User= "+user.getFullName()+" ,Supplier= "+supplier.getName();
   }
   
   

}
