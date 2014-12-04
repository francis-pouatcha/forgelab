package org.adorsys.adpharma.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.SortType;

import org.adorsys.adpharma.client.jpa.customer.Customer;

import com.google.common.collect.Lists;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableController;

public class TableViewUtils {
	
	// Create a TableControl from a TableView
	public static TableControl<Customer> fromTableViewToTableControl(TableView<Customer> tableView){
		Iterator<Customer> iterator = tableView.getItems().iterator();
		final List<Customer> customers = Lists.newArrayList(iterator);
		TableController<Customer> controller = new TableController<Customer>() {
			@Override
			public TableData<Customer> loadData(int startIndex, List<TableCriteria> filteredColumns,
					List<String> sortedColumns, List<SortType> sortingOrders, int maxResult) {
				return new TableData<Customer>(customers, Boolean.FALSE, customers.size());
			}
		};
		TableControl<Customer> customerControl= new TableControl<Customer>();
		customerControl.getTableView().getColumns().addAll(tableView.getColumns()); 
		customerControl.setRecordClass(Customer.class);
		customerControl.setController(controller);
		return customerControl;
	}
	
	// Return data from TableView
	public static List<Customer> getDataFromTableView(TableView<Customer> tableView){
		Iterator<Customer> iterator = tableView.getItems().iterator();
		final List<Customer> customers = Lists.newArrayList(iterator);
		return customers;
	}
	
	// Return ColumnWidths from TableView
	public static List<Double> getColumnWidthsFromTableView(TableView<Customer> tableView){
		int size = tableView.getColumns().size();
		List<Double> columnsWidth= new ArrayList<Double>();
		for (int i = 0; i < size; i++) {
			columnsWidth.add(Double.valueOf(50));
		}
		return columnsWidth;
	}

}
