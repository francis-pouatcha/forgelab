package org.adorsys.adpharma.client;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ChartApp2 extends Application {
	
//	public static void main(String[] args) {
//		launch(args);
//	}


	@Override
	public void start(Stage stage) throws Exception {
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		LineChart lineChart = new LineChart(xAxis, yAxis);
		lineChart.setData(getChartData());
		stage.setTitle("LineChart Sample");
		StackPane root = new StackPane();
		root.getChildren().add(lineChart);
		stage.setScene(new Scene(root, 500, 400));
		stage.show();
		
	}
	
	@SuppressWarnings("unchecked")
	private ObservableList<XYChart.Series<String, Double>> getChartData(){
		double javaValue = 17.56;
        double cValue = 17.06;
        double cppValue = 8.25;
        ObservableList<XYChart.Series<String, Double>> answer =FXCollections.observableArrayList();
        Series<String, Double> java = new Series<String, Double>();
        java.setName("Java");
        Series<String, Double> c = new Series<String, Double>();
        c.setName("C");
        Series<String, Double> cpp = new Series<String, Double>();
        cpp.setName("C++");
        for (int i = 2011; i < 2021; i++) { 
            java.getData().add(new XYChart.Data(Integer.toString(i), javaValue));
            javaValue = javaValue + 4 * Math.random() - 2;
            c.getData().add(new XYChart.Data(Integer.toString(i), cValue));
            cValue = cValue + Math.random() - .5;
            cpp.getData().add(new XYChart.Data(Integer.toString(i), cppValue));
            cppValue = cppValue + 4 * Math.random() - 2;
        }
        answer.addAll(java, c, cpp);
        return answer;
	}

}
