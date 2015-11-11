package def;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

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
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
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
		
		
		
		
		JFrame  jf = new JFrame("Kalman filter");
		JScrollPane scrollPane = new JScrollPane();
    	scrollPane.setBounds(10, 222, 200, 216);
    	jf.getContentPane().add(scrollPane);
    	
    	JTextArea textArea = new JTextArea();
    	scrollPane.setViewportView(textArea);
        //jf.pack();
    	OutputStream out = new OutputStream() {
    	    @Override
    	    public void write(final int b) throws IOException {
    	    	textArea.append((String.valueOf((char) b)));
    	    }
    	 
    	    @Override
    	    public void write(byte[] b, int off, int len) throws IOException {
    	    	textArea.append((new String(b, off, len)));
    	    }
    	    
    	    @Override
    	    public void write(byte[] b) throws IOException {
    	    	textArea.append(new String(b, 0, b.length));
    	    }
    	  };
    	 
    	  System.setOut(new PrintStream(out, true));
    	  System.setErr(new PrintStream(out, true));
    	  Data data = new Data();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);
		jf.setSize(1200, 600);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(10, 11, 200, 200);
		jf.getContentPane().add(buttonPanel);
		buttonPanel.setLayout(null);
		
		JToggleButton btnGR = new JToggleButton("Gyro Roll");
		
		btnGR.setSelected(true);
		btnGR.setBounds(0, 11, 121, 23);
		buttonPanel.add(btnGR);
		
		JToggleButton btnGP = new JToggleButton("Gyro Pitch");
		btnGP.setSelected(true);
		btnGP.setBounds(0, 36, 121, 23);
		buttonPanel.add(btnGP);
		
		JToggleButton btnGY = new JToggleButton("Gyro Yaw");
		btnGY.setSelected(true);
		btnGY.setBounds(0, 61, 121, 23);
		buttonPanel.add(btnGY);
		
		JToggleButton btnAX = new JToggleButton("Accel X");
		btnAX.setSelected(true);
		btnAX.setBounds(0, 86, 121, 23);
		buttonPanel.add(btnAX);
		
		JToggleButton btnAY = new JToggleButton("Accel Y");
		btnAY.setSelected(true);
		btnAY.setBounds(0, 111, 121, 23);
		buttonPanel.add(btnAY);
		
		JToggleButton btnAZ = new JToggleButton("Accel Z");
		btnAZ.setSelected(true);
		btnAZ.setBounds(0, 136, 121, 23);
		buttonPanel.add(btnAZ);
		
		JButton btnReReadData = new JButton("Re read data");
		btnReReadData.setBounds(0, 166, 121, 23);
		buttonPanel.add(btnReReadData);
		
		
		JPanel panel = new JPanel();
		//panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(216, 11, 800, 500);

		JFreeChart lineChart = ChartFactory.createXYLineChart("", "x", "value", Runner.createDataset(data.returnAll()),PlotOrientation.VERTICAL, true, true, false);
		//ChartFactory.createLineChart(title, categoryAxisLabel, valueAxisLabel, dataset, orientation, legend, tooltips, urls)
		lineChart.setBackgroundPaint(Color.lightGray);
		final XYPlot plot = lineChart.getXYPlot();
		lineChart.getLegend().setBackgroundPaint(Color.gray);
		lineChart.getLegend().setItemPaint(Color.BLACK);
		
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.lightGray);
		plot.setRangeGridlinePaint(Color.lightGray);
		
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		//renderer.setSeriesLi
		//renderer.setSeriesLinesVisible(5, false);
		
		renderer.setShapesVisible(false);
		renderer.setSeriesVisible(5, true);
		renderer.setSeriesPaint(0, Color.black);
		renderer.setSeriesPaint(1, Color.green);
		renderer.setSeriesPaint(2, Color.red);
		renderer.setSeriesPaint(3, Color.blue);
		renderer.setSeriesPaint(4, Color.cyan);
		renderer.setSeriesPaint(5, Color.orange);
		
		
		plot.setRenderer(renderer);
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		final NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
		domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		ChartPanel chartPanel = new ChartPanel(lineChart);
	      
        // settind default size
       
		
		btnGR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				renderer.setSeriesVisible(0,btnGR.isSelected());
			}
		});
		btnGP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				renderer.setSeriesVisible(1,btnGP.isSelected());
			}
		});
		btnGY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				renderer.setSeriesVisible(2,btnGY.isSelected());
			}
		});
		btnAX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				renderer.setSeriesVisible(3,btnAX.isSelected());
			}
		});
		btnAY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				renderer.setSeriesVisible(4,btnAY.isSelected());
			}
		});
		btnAZ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				renderer.setSeriesVisible(5,btnAZ.isSelected());
			}
		});
		
		 chartPanel.setPreferredSize(panel.getSize());
	        chartPanel.setVisible(true);
	        chartPanel.validate();
	        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	        // add to contentPane
	        panel.add(chartPanel);
	        panel.validate();
	        panel.setVisible(true);
	    	jf.getContentPane().add(panel);
	    	
	    	
			jf.setVisible(true);
			jf.validate();
			System.err.println("Test");
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
