package org.adorsys.adpharma.client.jpa.disbursement;

import java.awt.Desktop;
import java.io.File;
import java.util.Locale;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adpharma.client.access.SecurityUtil;
import org.adorsys.adpharma.client.events.PrintRequestedEvent;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucher;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchInput;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchResult;
import org.adorsys.adpharma.client.jpa.customervoucher.CustomerVoucherSearchService;
import org.adorsys.adpharma.client.jpa.paymentmode.PaymentMode;
import org.adorsys.javafx.crud.extensions.login.ErrorDisplay;
import org.adorsys.javafx.crud.extensions.login.ServiceCallFailedEventHandler;
import org.controlsfx.dialog.Dialogs;

@Singleton
public class DisbursementRepportPrinter {

	@Inject
	private CustomerVoucherSearchService customerVoucherSearchService;

	@Inject
	private ServiceCallFailedEventHandler callFailedEventHandler;

	@Inject
	private Locale locale ;

	@Inject
	private SecurityUtil securityUtil ;

	@Inject
	private Disbursement disbursement ;

	@PostConstruct
	public void postConstruct(){
		callFailedEventHandler.setErrorDisplay(new ErrorDisplay() {

			@Override
			protected void showError(Throwable exception) {
				Dialogs.create().showException(exception);

			}
		});

		customerVoucherSearchService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				CustomerVoucherSearchService s = (CustomerVoucherSearchService) event.getSource();
				CustomerVoucherSearchResult searchResult = s.getValue();
				event.consume();
				s.reset();
				CustomerVoucher voucher = searchResult.getResultList().iterator().next();
				printRepport(voucher,disbursement); 

			}
		});

	}

	public void handleDisbursementRepportPrintRequestEvent(@Observes @PrintRequestedEvent Disbursement disbursement){
		this.disbursement = disbursement ;
		if(PaymentMode.VOUCHER.equals(disbursement.getPaymentMode())){
			CustomerVoucherSearchInput searchInput = new CustomerVoucherSearchInput();
			searchInput.getEntity().setVoucherNumber(disbursement.getVoucherNumber());
			searchInput.getFieldNames().add("voucherNumber") ;
			customerVoucherSearchService.setSearchInputs(searchInput).start();
		}else {
			printRepport(null,disbursement); 
		}
	}

	private void printRepport(CustomerVoucher customerVoucher ,Disbursement disbursement){
		try {
			DisbursementPrintTemplatePdf pdfRepportTemplate = new DisbursementPrintTemplatePdf(customerVoucher, disbursement, securityUtil.getConnectedUser(), locale);
			pdfRepportTemplate.addItems();
			pdfRepportTemplate.closeDocument();
			Desktop.getDesktop().open(new File(pdfRepportTemplate.getFileName()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
