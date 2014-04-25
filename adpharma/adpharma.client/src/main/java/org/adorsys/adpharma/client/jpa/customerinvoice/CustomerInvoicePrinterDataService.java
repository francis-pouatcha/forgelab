package org.adorsys.adpharma.client.jpa.customerinvoice;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencyService;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.adorsys.adpharma.client.jpa.company.CompanyService;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItem;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemInvoice;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchInput;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemSearchResult;
import org.adorsys.adpharma.client.jpa.customerinvoiceitem.CustomerInvoiceItemService;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginService;

public class CustomerInvoicePrinterDataService extends Service<CustomerInvoicePrinterData>
{

   @Inject
   private CustomerInvoiceService customerInvoiceService;
   @Inject
   private CompanyService companyService;
   @Inject
   private AgencyService agencyService;
   @Inject
   private LoginService loginService;
   @Inject
   private CustomerInvoiceItemService customerInvoiceItemService;

   private CustomerInvoiceSearchInput searchInputs;

   public CustomerInvoicePrinterDataService setSearchInputs(CustomerInvoiceSearchInput searchInputs)
   {
      this.searchInputs = searchInputs;
      return this;
   }

   @Override
   protected Task<CustomerInvoicePrinterData> createTask()
   {
      return new Task<CustomerInvoicePrinterData>()
      {
         @Override
         protected CustomerInvoicePrinterData call() throws Exception
         {
            if (searchInputs == null)
               return null;
            CustomerInvoicePrinterData result = null;
            
            CustomerInvoice customerInvoice = searchInputs.getEntity();
            if(customerInvoice.getId()!=null){
            	customerInvoice = customerInvoiceService.findById(customerInvoice.getId());
            } else {
            	CustomerInvoiceSearchResult invoiceSearchResult = customerInvoiceService.findByLike(searchInputs);
            	if (invoiceSearchResult.getResultList().isEmpty()) {
            		return result;
            	}
            	customerInvoice = invoiceSearchResult.getResultList().iterator().next();
            }
            Agency agency = agencyService.findById(customerInvoice.getAgency().getId());
            Company company = companyService.findById(agency.getCompany().getId());
            Login login = loginService.findById(customerInvoice.getCreatingUser().getId());
            CustomerInvoiceItem customerInvoiceItem = new CustomerInvoiceItem();
            customerInvoiceItem.setInvoice(new CustomerInvoiceItemInvoice(customerInvoice));
            CustomerInvoiceItemSearchInput searchInput = new CustomerInvoiceItemSearchInput();
            searchInput.setEntity(customerInvoiceItem);
            searchInput.getFieldNames().add("invoice");
			CustomerInvoiceItemSearchResult found = customerInvoiceItemService.findBy(searchInput);
            result = new CustomerInvoicePrinterData(customerInvoice, company, agency, login, found);
			return result;
         }
      };
   }
}
