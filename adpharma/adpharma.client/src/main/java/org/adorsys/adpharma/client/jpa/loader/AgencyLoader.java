package org.adorsys.adpharma.client.jpa.loader;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.agency.AgencyCompany;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchInput;
import org.adorsys.adpharma.client.jpa.agency.AgencySearchResult;
import org.adorsys.adpharma.client.jpa.agency.AgencyService;
import org.adorsys.adpharma.client.jpa.company.Company;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class AgencyLoader extends Service<List<Agency>> {

	@Inject
	private AgencyService remoteService;
	
	private HSSFWorkbook workbook;

	public AgencyLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}

	private DataMap dataMap;
	public AgencyLoader setDataMap(DataMap dataMap) {
		this.dataMap = dataMap;
		return this;
	}

	private List<Agency> loadAgencies() {
		HSSFSheet sheet = workbook.getSheet("Agency");
		
		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		
		List<Agency> result = new ArrayList<Agency>();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Agency entity = new Agency();

			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setAgencyNumber(cell.getStringCellValue().trim());

			if (StringUtils.isBlank(entity.getAgencyNumber()))
				continue;

			AgencySearchInput searchInput = new AgencySearchInput();
			searchInput.setEntity(entity);
			searchInput.getFieldNames().add("agencyNumber");
			
			AgencySearchResult found = remoteService.findBy(searchInput);
			if (!found.getResultList().isEmpty()){
				result.add(found.getResultList().iterator().next());
				continue;
			}

			cell = row.getCell(1);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setName(cell.getStringCellValue().trim());

			cell = row.getCell(2);
			if (cell != null)
				entity.setActive(1==cell.getNumericCellValue());

			cell = row.getCell(3);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setStreet(cell.getStringCellValue().trim());

			cell = row.getCell(4);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setZipCode(cell.getStringCellValue().trim());

			cell = row.getCell(5);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setCity(cell.getStringCellValue().trim());

			cell = row.getCell(6);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setCountry(cell.getStringCellValue().trim());

			cell = row.getCell(7);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setPhone(cell.getStringCellValue().trim());

			cell = row.getCell(8);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setFax(cell.getStringCellValue().trim());

			cell = row.getCell(9);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				String companyRegNumber = cell.getStringCellValue().trim();
				List<Company> companies = dataMap.getCompanies();
				Company company = null;
				for (Company c : companies) {
					if(companyRegNumber.equalsIgnoreCase(c.getRegisterNumber())){
						company=c;
						break;
					}
				}
				if(company!=null)entity.setCompany(new AgencyCompany(company));
			}

			cell = row.getCell(10);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setTicketMessage(cell.getStringCellValue().trim());

			cell = row.getCell(11);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setInvoiceMessage(cell.getStringCellValue().trim());

			entity.setRecordingDate(new GregorianCalendar());

			result.add(remoteService.create(entity));
		}
		return result;
	}

	@Override
	protected Task<List<Agency>> createTask() {
		return new Task<List<Agency>>() {
			@Override
			protected List<Agency> call() throws Exception {
				return loadAgencies();
			}
		};
	}
}
