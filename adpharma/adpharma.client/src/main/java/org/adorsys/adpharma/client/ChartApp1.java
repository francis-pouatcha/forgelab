package org.adorsys.adpharma.client;


import org.apache.commons.lang3.StringUtils;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ChartApp1 extends Application {
	
//	public static void main(String[] args) {
//		
//		launch(args);
//	}
	

	@Override
	public void start(Stage stage) throws Exception {
		PieChart pieChart = new PieChart();
		pieChart.setData(getChartData());
		pieChart.setAnimated(Boolean.TRUE);
		pieChart.setLegendSide(Side.LEFT);
		pieChart.setClockwise(Boolean.TRUE);
	    pieChart.setLabelsVisible(Boolean.FALSE);
	    
		stage.setTitle("PieChart Sample");
		StackPane root = new StackPane();
		root.getChildren().add(pieChart);
		stage.setScene(new Scene(root, 400, 300));
		stage.show();
		
	}
	
	private ObservableList<PieChart.Data> getChartData() {
        ObservableList<PieChart.Data> answer = FXCollections.observableArrayList();
        answer.addAll(new PieChart.Data("java", 17.56),
                new PieChart.Data("C", 17.06),
                new PieChart.Data("C++", 8.25),
                new PieChart.Data("C#", 8.20),
                new PieChart.Data("ObjectiveC", 6.8),
                new PieChart.Data("PHP", 6.0),
                new PieChart.Data("(Visual)Basic", 4.76),
                new PieChart.Data("Other", 31.37));
        return answer;
}
	
	

}
