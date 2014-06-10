package org.adorsys.adpharma.client.jpa.loader;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Label;

import javax.inject.Inject;

import org.adorsys.adpharma.client.jpa.agency.Agency;
import org.adorsys.adpharma.client.jpa.gender.Gender;
import org.adorsys.adpharma.client.jpa.login.Login;
import org.adorsys.adpharma.client.jpa.login.LoginAgency;
import org.adorsys.adpharma.client.jpa.login.LoginSearchInput;
import org.adorsys.adpharma.client.jpa.login.LoginSearchResult;
import org.adorsys.adpharma.client.jpa.login.LoginService;
import org.adorsys.adpharma.client.jpa.loginrolenameassoc.LoginRoleNameAssoc;
import org.adorsys.adpharma.client.jpa.loginrolenameassoc.LoginRoleNameAssocSearchInput;
import org.adorsys.adpharma.client.jpa.loginrolenameassoc.LoginRoleNameAssocSearchResult;
import org.adorsys.adpharma.client.jpa.loginrolenameassoc.LoginRoleNameAssocService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class LoginLoader extends Service<List<Login>> {

	@Inject
	private LoginService remoteService;

	@Inject
	private LoginRoleNameAssocService loginRoleNameAssocService;

	private HSSFWorkbook workbook;
	private DataMap dataMap;

	public LoginLoader setWorkbook(HSSFWorkbook workbook) {
		this.workbook = workbook;
		return this;
	}
	private String progressText;
	private Label progressLabel;
	public LoginLoader setProgressText(String progressText) {
		this.progressText = progressText;
		return this;
	}
	public LoginLoader setProgressLabel(Label progressLabel) {
		this.progressLabel = progressLabel;
		return this;
	}

	public LoginLoader setDataMap(DataMap dataMap) {
		this.dataMap = dataMap;
		return this;
	}

	private List<Login> load() {

		List<Login> result = setModelLoginAgencies();

		HSSFSheet sheet = workbook.getSheet("Login");
		if(sheet==null){
			return result;
		}

		Platform.runLater(new Runnable(){@Override public void run() {progressLabel.setText(progressText);}});

		Iterator<Row> rowIterator = sheet.rowIterator();
		rowIterator.next();
		rowIterator.next();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Login entity = new Login();

			Cell cell = row.getCell(0);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setLoginName(cell.getStringCellValue().trim());

			if (StringUtils.isBlank(entity.getLoginName()))
				continue;

			LoginSearchInput searchInput = new LoginSearchInput();
			searchInput.setEntity(entity);
			searchInput.getFieldNames().add("loginName");

			LoginSearchResult found = remoteService.findBy(searchInput);
			if (!found.getResultList().isEmpty()){
				//				result.add(found.getResultList().iterator().next());
				continue;
			}

			cell = row.getCell(1);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setEmail(cell.getStringCellValue().trim());

			cell = row.getCell(2);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setFullName(cell.getStringCellValue().trim());

			cell = row.getCell(3);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setPassword(cell.getStringCellValue().trim());

			cell = row.getCell(4);
			if (cell != null)
				entity.setDisableLogin(Boolean.FALSE);

			cell = row.getCell(5);
			if (cell != null)
				entity.setAccountLocked(Boolean.FALSE);

//			cell = row.getCell(6);
//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
//			{
//				String date = cell.getStringCellValue().trim();
//				Date parseDate;
//				try {
//					parseDate = DateUtils.parseDate(date, "dd-MM-yyyy HH:mm");
//				} catch (ParseException e) {
//					throw new IllegalStateException(e);
//				}
//				Calendar calendar = Calendar.getInstance();
//				calendar.setTime(parseDate);
//				entity.setCredentialExpiration(calendar);
//			}

//			cell = row.getCell(7);
//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
//			{
//				String date = cell.getStringCellValue().trim();
//				Date parseDate;
//				try {
//					parseDate = DateUtils.parseDate(date, "dd-MM-yyyy HH:mm");
//				} catch (ParseException e) {
//					throw new IllegalStateException(e);
//				}
//				Calendar calendar = Calendar.getInstance();
//				calendar.setTime(parseDate);
//				entity.setAccountExpiration(calendar);
//			}

			cell = row.getCell(8);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setSaleKey(cell.getStringCellValue().trim());

			cell = row.getCell(9);
			if (cell != null)
			{
				//				BigDecimal decimal = new BigDecimal(cell.getNumericCellValue());
				entity.setDiscountRate(BigDecimal.ZERO);
			}

			cell = row.getCell(10);
//			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				entity.setGender(Gender.NEUTRAL);

			//			cell = row.getCell(11);
			entity.setRecordingDate(new GregorianCalendar());

			cell = row.getCell(12);
			String agencyNumber = "AG-0001";
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue()))
				agencyNumber = cell.getStringCellValue().trim();
			List<Agency> agencies = dataMap.getAgencies();
			Agency agency = null;
			for (Agency ag : agencies) {
				if(agencyNumber.equalsIgnoreCase(ag.getAgencyNumber())){
					agency = ag;
					break;
				}
			}
			if(agency!=null){
				entity.setAgency(new LoginAgency(agency));
			} else {
				throw new IllegalStateException("No agency found with agency number: " + agencyNumber + " specified for login: " + entity.getLoginName());
			}


			entity = remoteService.create(entity);

			cell = row.getCell(13);
			if (cell != null && StringUtils.isNotBlank(cell.getStringCellValue())){
				String modelLoginName = cell.getStringCellValue().trim();
				LoginSearchInput loginSearchInput = new LoginSearchInput();
				Login login = new Login();
				login.setLoginName(modelLoginName);
				loginSearchInput.setEntity(login);
				loginSearchInput.getFieldNames().add("loginName");

				LoginSearchResult lsr = remoteService.findBy(loginSearchInput);
				if (lsr.getResultList().isEmpty()){
					throw new IllegalStateException("No login found with login name: " + modelLoginName + " specified as model for login: " + entity.getLoginName());
				}
				Login modelLogin = lsr.getResultList().iterator().next();
				LoginRoleNameAssoc loginRoleNameAssoc = new LoginRoleNameAssoc();
				loginRoleNameAssoc.setSource(modelLogin);
				LoginRoleNameAssocSearchInput loginRoleNameAssocSearchInput = new LoginRoleNameAssocSearchInput();
				loginRoleNameAssocSearchInput.setEntity(loginRoleNameAssoc);
				loginRoleNameAssocSearchInput.getFieldNames().add("source");
				loginRoleNameAssocSearchInput.setStart(0);
				loginRoleNameAssocSearchInput.setMax(100);
				LoginRoleNameAssocSearchResult foundAssoc = loginRoleNameAssocService.findBy(loginRoleNameAssocSearchInput);
				List<LoginRoleNameAssoc> resultList = foundAssoc.getResultList();
				for (LoginRoleNameAssoc m : resultList) {
					LoginRoleNameAssoc lra = new LoginRoleNameAssoc();
					lra.setSource(entity);
					lra.setTarget(m.getTarget());
					lra.setSourceQualifier(m.getSourceQualifier());
					lra.setTargetQualifier(m.getTargetQualifier());
					lra = loginRoleNameAssocService.create(lra);
					entity.getRoleNames().add(lra.getTarget());
				}
				//				
				//				// later on auto lock model logins.
				//				if (!modelLogin.getAccountLocked()){
				//					modelLogin.setAccountLocked(Boolean.TRUE);
				//					remoteService.update(modelLogin);
				//				}
			} else {
//				throw new IllegalStateException("No model login specified for login: " + entity.getLoginName());
			}

			result.add(entity);
		}
		return result;
	}

	@Override
	protected Task<List<Login>> createTask() {
		return new Task<List<Login>>() {
			@Override
			protected List<Login> call() throws Exception {
				return load();
			}
		};
	}

	private List<Login> setModelLoginAgencies(){
		List<Login> result = new ArrayList<Login>();

		Agency agency = dataMap.getAgencies().iterator().next();

		// After loading the agencies, we could switch the agency of those users already in the database.
		LoginSearchResult logins = remoteService.listAll();
		List<Login> resultList = logins.getResultList();
		for (Login login : resultList) {
			if(login.getAgency()==null || StringUtils.isBlank(login.getAgency().getAgencyNumber())){
				login.setAgency(new LoginAgency(agency));
				Login updated = remoteService.update(login);
				result.add(updated);
			} else {
				result.add(login);
			}
		}
		return result ;
	}
}
