package org.adorsys.adpharma.client.jpa.salesorder;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.adorsys.adpharma.client.jpa.salesorderitem.SalesOrderItem;
import org.adorsys.adpharma.client.utils.DateHelper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class SalesOrderXlsExporter {
	
	public static void exportSalesOrderItemsToXls(List<SalesOrderItem> items, PeriodicalDataSearchInput model){
		if(items.isEmpty()) 
			return ;

		HSSFWorkbook salesOrderXls = new HSSFWorkbook();
		int rownum = 0 ;
		int cellnum = 0 ;
		HSSFCell cell ;
		String beginDate = DateHelper.format(model.getBeginDate().getTime(), DateHelper.DATE_FORMAT);
		String endDate = DateHelper.format(model.getEndDate().getTime(), DateHelper.DATE_FORMAT);
		HSSFSheet sheet = salesOrderXls.createSheet("Ventes -"+beginDate+" - "+endDate);
		HSSFRow header = sheet.createRow(rownum++);

		cell = header.createCell(cellnum++);
		cell.setCellValue("cip");

		cell = header.createCell(cellnum++);
		cell.setCellValue("designation");

		cell = header.createCell(cellnum++);
		cell.setCellValue("Qte en Stock");
		
		cell = header.createCell(cellnum++);
		cell.setCellValue("Qte Vendue");

		cell = header.createCell(cellnum++);
		cell.setCellValue("PV Unitaire ");
		
		cell = header.createCell(cellnum++);
		cell.setCellValue("PV Total ");
		
		cell = header.createCell(cellnum++);
		cell.setCellValue("TVA ");

			for (SalesOrderItem item : items) {
				cellnum = 0 ;
				HSSFRow row = sheet.createRow(rownum++);
				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getInternalPic());

				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getArticle().getArticleName());

				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getArticle().getQtyInStock().toBigIntegerExact().toString());
				
				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getDeliveredQty().toBigIntegerExact().toString());

				cell = row.createCell(cellnum++);
				String salesPricePu=item.getSalesPricePU()!=null?item.getSalesPricePU().toString():"";
				cell.setCellValue(salesPricePu);
				
				cell = row.createCell(cellnum++);
				String totalSalesPrice=item.getTotalSalePrice()!=null?item.getTotalSalePrice().toBigIntegerExact().toString():"";
				cell.setCellValue(totalSalesPrice);
				
				cell = row.createCell(cellnum++);
				cell.setCellValue(item.getVatValue().toBigIntegerExact().toString());
			}
			
			try {
				File file = new File("Rapport-Ventes.xls");
				FileOutputStream outputStream = new FileOutputStream(file);
				salesOrderXls.write(outputStream);
				outputStream.close();
				Desktop.getDesktop().open(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}
