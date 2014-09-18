package org.adorsys.adpharma.client.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
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
import org.controlsfx.dialog.Dialogs;
public class PhmlOrderSender {

	@Inject
	private ProcurementOrderItemService itemService ;

	private ReceiptPrintProperties applicationConfiguration;

	private String REPARTITEUR_LINE ="R" ;

	private String END_COMMAND_LINE ="Z" ;

	private String DATE_LINE ="L" ;

	private String COMMENT_LINE ="W" ;

	private String WORK_TYPE_LINE ="PC" ;

	private final String ITEM_LINE ="E" ;

	private final String COMMAND_TYPE_LINE ="C" ;

	private final String NORMAL_COMMAND_TYPE ="000" ;

	private final String SEPARATOR ="R" ;

	private final String CIP39_CODIFICATION ="C1";


	@PostConstruct
	public void posconstruct(){

	}

	public String buildItemLine(ProcurementOrderItem item , int lineIndex){
		String itemLine =ITEM_LINE;
		BigDecimal qty =	item.getQtyOrdered()!=null ? item.getQtyOrdered() : BigDecimal.ZERO;
		// add qty ordered
		itemLine =itemLine + StringUtils.leftPad(qty.toBigInteger()+"", 4, "0");
		// add codification type
		itemLine = itemLine +  CIP39_CODIFICATION;
		// add product code 
		itemLine = itemLine +StringUtils.rightPad(item.getMainPic(),50);
		//partial delivery ?
		itemLine = itemLine +"N";
		//RELICAT ?
		itemLine = itemLine +"N";
		//EQUIVALENT DELIVERY ?
		itemLine = itemLine +"N";
		// add line number
		itemLine =itemLine + StringUtils.leftPad(lineIndex+"", 4, "0");
		return itemLine;
	}

	public String buildCommandTypeLine(String reference){
		String commandeTypeLine =COMMAND_TYPE_LINE+NORMAL_COMMAND_TYPE+SEPARATOR+
				StringUtils.rightPad(reference,25);
		return commandeTypeLine;
	}

	public String buildRepartiteurLine(String repartiteur){
		String repartiteurLine =REPARTITEUR_LINE+repartiteur;
		return repartiteurLine;
	}

	public String buildDeliveryDateLine(Date deleiveryDate){
		return DATE_LINE+DateHelper.format(deleiveryDate, "yyyy-MM-dd");
	}

	public String buildCommentLine(String comment){
		return COMMENT_LINE+comment;
	}

	public String buildEndCommandLine(int clair,int encoded){
		return END_COMMAND_LINE+StringUtils.leftPad(encoded+"", 4,"0")+StringUtils.leftPad(clair+"", 4,"0");
	}

	/**
	 * create phml order file and store it in message directory
	 * @param order tobe sent on phml server
	 * @throws IOException
	 */
	public void sendToPhml(ProcurementOrder order,String repartiteur) throws IOException{
		applicationConfiguration= ReceiptPrintProperties.loadPrintProperties();
		String messageDir = applicationConfiguration.getPhmlMessageDirectory();
		if(StringUtils.isBlank(repartiteur)) throw new RuntimeException("Vous devez selectionner un repartiteur !");
		String filename = messageDir+order.getProcurementOrderNumber()+".txt";

		List<String> lines = new ArrayList<String>();
		File file = null;
		try {
			file = new File(filename);
			if(file.exists())
				file.delete();
		} catch (Exception e) {
			throw new FileNotFoundException("Impossible de creer le fichier d'envoi");
		}
		lines.add(buildRepartiteurLine(repartiteur));
		lines.add(WORK_TYPE_LINE);
		lines.add(buildCommandTypeLine(order.getProcurementOrderNumber()));
		lines.add(buildDeliveryDateLine(new Date()));

		ProcurementOrderItemSearchInput searchInput = new ProcurementOrderItemSearchInput();
		searchInput.getEntity().setProcurementOrder(new ProcurementOrderItemProcurementOrder(order));
		searchInput.getFieldNames().add("procurementOrder");
		searchInput.setMax(-1);
		List<ProcurementOrderItem> items = itemService.findBy(searchInput).getResultList();

		int index=1;
		for (ProcurementOrderItem item : items) {
			lines.add(buildItemLine(item, index));
			index++;
		}
		lines.add(buildCommentLine(" Bien Vouloir nous livrer dans de bref delais Merci"));
		lines.add(buildEndCommandLine(0, items.size()));

		FileUtils.writeLines(file, "UTF-8", lines);
		//		Desktop.getDesktop().open(file);
	}



}
