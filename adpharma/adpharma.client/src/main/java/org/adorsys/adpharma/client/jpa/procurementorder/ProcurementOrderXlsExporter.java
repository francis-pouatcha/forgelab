package org.adorsys.adpharma.client.jpa.procurementorder;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItem;
import org.adorsys.adpharma.client.jpa.procurementorderitem.ProcurementOrderItemProcurementOrder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.common.collect.Lists;

public class ProcurementOrderXlsExporter {

	@SuppressWarnings("resource")
	public static void exportProcurementToXls(Iterator<ProcurementOrderItem> iterator){
		List<ProcurementOrderItem> items = Lists.newArrayList(iterator);
		if(items.isEmpty()) 
			return ;
		ProcurementOrderItemProcurementOrder procurementOrder = items.get(0).getProcurementOrder();

		HSSFWorkbook deleveryXls = new HSSFWorkbook();
		int rownum = 0 ;
		int cellnum = 0 ;
		HSSFCell cell ;
		HSSFSheet sheet = deleveryXls.createSheet(procurementOrder.getProcurementOrderNumber());
		HSSFRow header = sheet.createRow(rownum++);

		cell = header.createCell(cellnum++);
		cell.setCellValue("cip");

		cell = header.createCell(cellnum++);
		cell.setCellValue("designation");

		cell = header.createCell(cellnum++);
		cell.setCellValue("qte");

		cell = header.createCell(cellnum++);
		cell.setCellValue("pv");

		if( procurementOrder!=null&&sheet!=null){
			for (ProcurementOrderItem item : items) {
				cellnum = 0 ;
				HSSFRow row = sheet.createRow(rownum++);
				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getMainPic());

				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getArticle().getArticleName());

				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getQtyOrdered()!=null?item.getQtyOrdered().toBigInteger()+"":"");

				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getPurchasePricePU()!=null?item.getPurchasePricePU().toBigInteger()+"":"");


			}
			
			try {
				File file = new File("commande.xls");
				FileOutputStream outputStream = new FileOutputStream(file);
				deleveryXls.write(outputStream);
				outputStream.close();
				Desktop.getDesktop().open(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
