package org.adorsys.adpharma.client.jpa.delivery;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItem;
import org.adorsys.adpharma.client.jpa.deliveryitem.DeliveryItemDelivery;
import org.adorsys.adpharma.client.utils.DateHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.google.common.collect.Lists;

public class DeliveryXlsExporter {
	
	// Print document to xls
	@SuppressWarnings("resource")
	public static void exportDeliveryToXls(Iterator<DeliveryItem> iterator, String agencyName) {
		
		  // Get delivery items
		  List<DeliveryItem> deliveryItems = Lists.newArrayList(iterator);
		
		    DeliveryItemDelivery delivery = deliveryItems.get(0).getDelivery();
		    
			String supllierName = delivery.getSupplier().getName();

			if(StringUtils.isNotBlank(supllierName))
				if(supllierName.length()>4)
					supllierName = StringUtils.substring(supllierName, 0, 3);

			HSSFWorkbook deleveryXls = new HSSFWorkbook();
			int rownum = 0 ;
			int cellnum = 0 ;
			HSSFCell cell ;
			HSSFSheet sheet = deleveryXls.createSheet(delivery.getDeliveryNumber());
			HSSFRow header = sheet.createRow(rownum++);

			cell = header.createCell(cellnum++);
			cell.setCellValue("cipm");

			cell = header.createCell(cellnum++);
			cell.setCellValue("designation");

			cell = header.createCell(cellnum++);
			cell.setCellValue("pv");

			cell = header.createCell(cellnum++);
			cell.setCellValue("fournisseur");
			
			cell = header.createCell(cellnum++);
			cell.setCellValue("site");
			
			cell = header.createCell(cellnum++);
			cell.setCellValue("date");

			if( delivery!=null&&sheet!=null){
//				String agencyName = securityUtil.getAgency().getName();
				for (DeliveryItem item : deliveryItems) {
					int intValue = item.getStockQuantity().intValue();

					for (int i = 0; i < intValue; i++) {
						cellnum = 0 ;
						HSSFRow row = sheet.createRow(rownum++);
						cell = row.createCell(cellnum++);
						cell.setCellValue(item.getInternalPic());

						cell = row.createCell(cellnum++);
						cell.setCellValue(item.getArticle().getArticleName());
						//
						//					cell = row.createCell(cellnum++);
						//					cell.setCellValue(item.getStockQuantity().doubleValue());

						cell = row.createCell(cellnum++);
						cell.setCellValue(item.getSalesPricePU().toBigInteger()+"F");

						cell = row.createCell(cellnum++);
						cell.setCellValue(supllierName);
						
						cell = row.createCell(cellnum++);
						cell.setCellValue(agencyName);
						
						if(item.getCreationDate()!=null){
						cell = row.createCell(cellnum++);
						cell.setCellValue(DateHelper.format(item.getCreationDate().getTime(),"ddMMyy"));
						}else {
							cell = row.createCell(cellnum++);
							cell.setCellValue("");
						}
					}
				}
				try {
					File file = new File("delivery.xls");
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
