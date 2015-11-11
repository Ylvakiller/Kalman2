package def;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.border.BevelBorder;
/**
 * This class will run the kalman filter algorithm
 * This class will also need to do the complete interface
 * @author Ylva
 *
 */
public class Runner {

	/**
	 * This is the runner class, right now it will load all the data directly in a graph, in the future this will not be the case but it will instead hold the data in the data class
	 * @param args Nothing
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		Data data = new Data();
		
		
		JFrame  jf = new JFrame("Kalman filter");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);
		jf.setSize(1200, 600);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 11, 200, 200);
		jf.getContentPane().add(buttonPanel);
		
		JPanel panel = new JPanel();
		//panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(216, 11, 800, 500);

		JFreeChart lineChart = ChartFactory.createXYLineChart("", "value", "x", Runner.createDataset(ExcelReader.read()),PlotOrientation.VERTICAL, true, true, false);
		//ChartFactory.createLineChart(title, categoryAxisLabel, valueAxisLabel, dataset, orientation, legend, tooltips, urls)
		lineChart.setBackgroundPaint(Color.white);
		final XYPlot plot = lineChart.getXYPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);
		
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		//renderer.setSeriesLi
		//renderer.setSeriesLinesVisible(5, false);
		
		renderer.setShapesVisible(false);
		renderer.setSeriesVisible(5, false);
		renderer.setSeriesPaint(1, Color.black);
		plot.setRenderer(renderer);
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		final NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		ChartPanel chartPanel = new ChartPanel(lineChart);
	      
        // settind default size
        chartPanel.setPreferredSize(panel.getSize());
        chartPanel.setVisible(true);
        chartPanel.validate();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        // add to contentPane
        panel.add(chartPanel);
        panel.validate();
        panel.setVisible(true);
    	jf.getContentPane().add(panel);
        //jf.pack();
    	
		jf.setVisible(true);
		jf.validate();
        System.out.println("done");
	}
	/**
	 * This creates a dataset for the chart from the input data
	 * @param floatArray the array to input, uses the format from the ExcelReader.read() method
	 * @return a dataset that can be used to plot a graph
	 */
	private static XYDataset createDataset(float[][] floatArray) {

		final XYSeries gRoll = new XYSeries("Gyro Roll");
		final XYSeries gPitch = new XYSeries("Gyro Pitch");
		final XYSeries gYaw = new XYSeries("Gyro Yaw");
		final XYSeries aX = new XYSeries("Accel X");
		final XYSeries aY = new XYSeries("Accel Y");
		final XYSeries aZ = new XYSeries("Accel Z");
		
		int i=0;
		while(i<floatArray.length){
			gRoll.add(i,floatArray[i][0]);
			gPitch.add(i,floatArray[i][1]);
			gYaw.add(i,floatArray[i][2]);
			aX.add(i,floatArray[i][3]);
			aY.add(i,floatArray[i][4]);
			aZ.add(i,floatArray[i][5]);
			i++;
		}


		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(gRoll);
		dataset.addSeries(gPitch);
		dataset.addSeries(gYaw);
		dataset.addSeries(aX);
		dataset.addSeries(aY);
		dataset.addSeries(aZ);
		

		return dataset;

	}
	
}
