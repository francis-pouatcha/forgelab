package org.adorsys.adpharma.client.jpa.loader;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.customer.Customer;
import org.adorsys.adpharma.client.jpa.customer.CustomerCustomerCategory;
import org.adorsys.adpharma.client.jpa.customer.CustomerEmployer;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchInput;
import org.adorsys.adpharma.client.jpa.customer.CustomerSearchResult;
import org.adorsys.adpharma.client.jpa.customer.CustomerService;
import org.adorsys.adpharma.client.jpa.customercategory.CustomerCategory;
import org.adorsys.adpharma.client.jpa.customertype.CustomerType;
import org.adorsys.adpharma.client.jpa.employer.Employer;
import org.adorsys.adpharma.client.jpa.gender.Gender;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class CustomerLoader extends Service<List<Customer>> {

	@Inject
	private CustomerService remoteService;
	
	private HSSFWorkbook workbook;
	private DataMap dataMap;

	public CustomerLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}

	public CustomerLoader setDataMap(DataMap dataMap) {
		this.dataMap = dataMap;
		return this;
	}
	private String progressText;
	private Label progressLabel;
	public CustomerLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public CustomerLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	private List<Customer> loadAgencies() {
		List<Customer> result = new ArrayList<Customer>();
		result.addAll(remoteService.listAll().getResultList());
		
		HSSFSheet sheet = workbook.getSheet("Customer");
		if(sheet==null){
			return result;
		}
		
		Platform.runLater(new Runnable(){@Override public void run() {progressLabel.setText(progressText);}});

		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();
		
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Customer entity = new Customer();

			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setSerialNumber(cell.getStringCellValue().trim());

			if (StringUtils.isBlank(entity.getSerialNumber()))
				entity.setSerialNumber(RandomStringUtils.random(7));

			CustomerSearchInput searchInput = new CustomerSearchInput();
			searchInput.setEntity(entity);
			searchInput.getFieldNames().add("serialNumber");
			
			CustomerSearchResult found = remoteService.findBy(searchInput);
			if (!found.getResultList().isEmpty()){
//				result.add(found.getResultList().iterator().next());
				continue;
			}

			cell = row.getCell(1);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setGender(Gender.valueOf(cell.getStringCellValue().trim()));
			
			cell = row.getCell(2);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setFirstName(cell.getStringCellValue().trim());

			cell = row.getCell(3);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				entity.setLastName(cell.getStringCellValue().trim());
				
			}else {
				entity.setLastName(".");
			}

			cell = row.getCell(4);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				entity.setFullName(cell.getStringCellValue().trim());
				
			}else {
				entity.setFullName(".");
			}

//			cell = row.getCell(5);
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
//			{
//				String date = cell.getStringCellValue().trim();
//				Date parseDate;
//				try {
//					parseDate = DateUtils.parseDate(date, "dd-MM-yyyy");
//				} catch (ParseException e) {
//					throw new IllegalStateException(e);
//				}
//				Calendar calendar = Calendar.getInstance();
//				calendar.setTime(parseDate);
//				entity.setBirthDate(calendar);
//			}

			cell = row.getCell(6);
			cell.setCellType(Cell.CELL_TYPE_STRING);
//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
//				entity.setLandLinePhone(cell.getStringCellValue().trim());

			cell = row.getCell(7);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setMobile(cell.getStringCellValue().trim());

//			cell = row.getCell(8);
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
//				entity.setFax(cell.getStringCellValue().trim());
//			
//			cell = row.getCell(9);
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
//				entity.setEmail(cell.getStringCellValue().trim());

//			cell = row.getCell(10);
//			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//			if (cell != null)
//				entity.setCreditAuthorized(1==cell.getNumericCellValue());
//
//			cell = row.getCell(11);
//			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//			if (cell != null)
//				entity.setDiscountAuthorized(1==cell.getNumericCellValue());
//
//			cell = row.getCell(12);
//			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//			if (cell != null)
//			{
//				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
//				entity.setTotalCreditLine(decimal);
//			}

//			cell = row.getCell(13);
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
//				String employerName = cell.getStringCellValue().trim();
//				List<Employer> employers = dataMap.getEmployers();
//				Employer employer = null;
//				for (Employer e : employers) {
//					if(employerName.equals(e.getName())){
//						employer = e;
//						break;
//					}
//				}
//
//				if(employer!=null){
//					entity.setEmployer(new CustomerEmployer(employer));
//				} else {
//					throw new IllegalStateException("No employer found with name: " + employerName);
//				}
//			}

//			cell = row.getCell(14);
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
//				String customerCategoryName = cell.getStringCellValue().trim();
//				List<CustomerCategory>customerCategories = dataMap.getCustomerCategories();
//				CustomerCategory customerCategory = null;
//				for (CustomerCategory c : customerCategories) {
//					if(customerCategoryName.equals(c.getName())){
//						customerCategory = c;
//						break;
//					}
//				}
//
//				if(customerCategory!=null){
//					entity.setCustomerCategory(new CustomerCustomerCategory(customerCategory));
//				} else {
//					throw new IllegalStateException("No customer category found with name: " + customerCategoryName);
//				}
//			}

//			cell = row.getCell(15);
//			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
//			if (cell != null)
//			{
//				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
//				entity.setTotalDebt(decimal);
//			}

//			cell = row.getCell(16);
//			cell.setCellType(Cell.CELL_TYPE_STRING);
//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
//				entity.setCustomerType(CustomerType.valueOf(cell.getStringCellValue().trim()));
			
			result.add(remoteService.create(entity));
		}
		return result;
	}

	@Override
	protected Task<List<Customer>> createTask() {
		return new Task<List<Customer>>() {
			@Override
			protected List<Customer> call() throws Exception {
				return loadAgencies();
			}
		};
	}
}
