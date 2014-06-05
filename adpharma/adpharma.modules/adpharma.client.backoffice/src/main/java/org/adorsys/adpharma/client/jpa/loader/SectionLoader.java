package org.adorsys.adpharma.client.jpa.loader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.section.Section;
import org.adorsys.adpharma.client.jpa.section.SectionAgency;
import org.adorsys.adpharma.client.jpa.section.SectionSearchInput;
import org.adorsys.adpharma.client.jpa.section.SectionSearchResult;
import org.adorsys.adpharma.client.jpa.section.SectionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class SectionLoader extends Service<List<Section>> {

	@Inject
	private SectionService sectionService;
	
	private HSSFWorkbook workbook;
	private DataMap dataMap;

	public SectionLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}
	private String progressText;
	private Label progressLabel;
	public SectionLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public SectionLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	public SectionLoader setDataMap(DataMap dataMap) {
		this.dataMap = dataMap;
		return this;
	}

	private List<Section> load() {
		List<Section> result = new ArrayList<Section>();
		result.addAll(sectionService.listAll().getResultList());
		
		HSSFSheet sheet = workbook.getSheet("Section");
		if(sheet==null){
			return result;
		}
		
		Platform.runLater(new Runnable(){@Override public void run() {progressLabel.setText(progressText);}});

		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Section entity = new Section();

			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setSectionCode(cell.getStringCellValue().trim());

			if (StringUtils.isBlank(entity.getSectionCode()))
				continue;

			cell = row.getCell(1);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setName(cell.getStringCellValue().trim());

			cell = row.getCell(2);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setPosition(cell.getStringCellValue().trim());

			cell = row.getCell(3);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setGeoCode(cell.getStringCellValue().trim());

			cell = row.getCell(4);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				String agencyNumber = cell.getStringCellValue().trim();
				List<Agency> agencies = dataMap.getAgencies();
				Agency agency = null;
				for (Agency ag : agencies) {
					if(agencyNumber.equalsIgnoreCase(ag.getAgencyNumber())){
						agency = ag;
						break;
					}
				}
				if(agency!=null){
					entity.setAgency(new SectionAgency(agency));
				} else {
					throw new IllegalStateException("No agency found with agency number: " + agencyNumber);
				}
			}

			SectionSearchInput sectionSearchInput = new SectionSearchInput();
			sectionSearchInput.setEntity(entity);
			sectionSearchInput.getFieldNames().add("sectionCode");
			
			SectionSearchResult found = sectionService.findBy(sectionSearchInput);
			if (!found.getResultList().isEmpty()){
//				result.add(found.getResultList().iterator().next());
			} else {
				result.add(sectionService.create(entity));
			}
		}
		return result;
	}

	@Override
	protected Task<List<Section>> createTask() {
		return new Task<List<Section>>() {
			@Override
			protected List<Section> call() throws Exception {
				return load();
			}
		};
	}
}
