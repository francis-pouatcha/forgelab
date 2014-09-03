package org.adorsys.adpharma.client.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customerinvoice.ReceiptPrintProperties;
import org.adorsys.adpharma.client.jpa.procurementorder.ProcurementOrder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemProcurementOrder;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemSearchInput;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
public class PhmlOrderReceiver {


	private ReceiptPrintProperties applicationConfiguration;

	private final String ITEM_LINE ="K" ;

	@PostConstruct
	public void posconstruct(){

	}

	public ProcurementOrderItem readProductLine(String line){
		ProcurementOrderItem procurementOrderItem = null ;
		if(line.startsWith(ITEM_LINE)){
			procurementOrderItem= new ProcurementOrderItem();
			String receivedQty = StringUtils.substring(line, 2, 5);
			BigDecimal receiveQty = new BigDecimal(receivedQty);
			procurementOrderItem.setAvailableQty(receiveQty);
			String cip = StringUtils.substring(line, 8,57);
			procurementOrderItem.setMainPic(cip.trim());
			if(line.length()>116){
			String ppu = StringUtils.substring(line, 116,line.length());
			BigDecimal pp = new BigDecimal(ppu);
			procurementOrderItem.setPurchasePricePU(pp);
			}
		}
		return procurementOrderItem ;
	}

	/**
	 *retreived corresponding phml response
	 * @param order tobe sent on phml server
	 * @throws IOException
	 */
	public List<ProcurementOrderItem> receiveFromPhml(ProcurementOrder order) throws IOException{
		applicationConfiguration= ReceiptPrintProperties.loadPrintProperties();
		final String filename = order.getProcurementOrderNumber()+"-reponse-retour-";
		String responseDirectory = applicationConfiguration.getPhmlResponseDirectory();
		List<ProcurementOrderItem> responseItem = new ArrayList<ProcurementOrderItem>();
		List<String> lines = new ArrayList<String>();
		File file = new File(responseDirectory);
		File[] listFiles = file.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith(filename);
			}
		});
		File responseFile = null ;
		if(listFiles.length>0){
			responseFile = listFiles[0];
		}else {
			throw new FileNotFoundException("Fichier de reponse Phml Non disponible ");
		}
		if (responseFile!=null) {
			List<String> readLines = FileUtils.readLines(responseFile, "UTF-8");
			for (String string : readLines) {
				ProcurementOrderItem readProductLine = readProductLine(string);
				if(readProductLine!=null)
					responseItem.add(readProductLine);
			}
		}
		return responseItem ;

	}



}
