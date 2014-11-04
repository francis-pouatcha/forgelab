package org.adorsys.adpharma.client.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adorsys.adpharma.client.AppConfigProperties;
import org.apache.commons.lang3.StringUtils;
import org.jboss.weld.exceptions.IllegalStateException;


public class ReportColumns {
	private final List<ReportColumn> columns = new ArrayList<ReportColumn>();
	private final Set<String> columnNameSet = new HashSet<String>();
	private float[] colSizes;

	public ReportColumns(String reportName) {
		AppConfigProperties appConfigProperties = new AppConfigProperties();
		Properties properties = appConfigProperties.getProperties();
		String fieldNames = properties.getProperty(reportName+".fieldNames");
		String[] fieldNamesArray = StringUtils.split(fieldNames, ",");
		String fieldSizes = properties.getProperty(reportName+".fieldSizes");
		String[] fieldSizesArray = StringUtils.split(fieldSizes, ",");
		if(fieldNamesArray.length!=fieldSizesArray.length) throw new IllegalStateException(reportName+".fieldSizes and "+reportName+".fieldNames must have same number of entries in the proeprty file appconfig.properties.");
		colSizes = new float[fieldSizesArray.length];
		for (int i = 0; i < fieldSizesArray.length; i++) {
			Float floati = new Float(fieldSizesArray[i]);
			columns.add(new ReportColumn(fieldNamesArray[i], floati));
			colSizes[i]=floati;
			columnNameSet.add(fieldNamesArray[i]);
		}
	}
	
	public boolean containsCol(String fieldName) {
		return columnNameSet.contains(fieldName);
	}

	public List<ReportColumn> getColumns() {
		return columns;
	}

	public float[] getColArray() {
		return colSizes;
	}

}
