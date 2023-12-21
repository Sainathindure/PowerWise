import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EnergyConsumptionGraph extends JFrame {

    public EnergyConsumptionGraph(String title) {
        super(title);

        // Create dataset
        CategoryDataset dataset = createDataset();

        // Create chart
        JFreeChart chart = ChartFactory.createBarChart(
                "Energy Consumption per Month",
                "Month",
                "Energy Consumption (Units)",
                dataset
        );

        // Create Panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\College\\Minor 1\\energy_consumption.csv.csv"))) {
            String line;
            
            // Skip the header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                // Ensure the array has at least 5 elements before accessing them
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String month = data[0];
                    int submeter1 = Integer.parseInt(data[1]);
                    int submeter2 = Integer.parseInt(data[2]);
                    int submeter3 = Integer.parseInt(data[3]);
                    int totalConsumption = Integer.parseInt(data[4]);

                    // Add data to the dataset
                    dataset.addValue(submeter1, "Submeter 1", month);
                    dataset.addValue(submeter2, "Submeter 2", month);
                    dataset.addValue(submeter3, "Submeter 3", month);
                    dataset.addValue(totalConsumption, "Total Consumption", month);
                } else {
                    // Log a warning or handle lines with fewer elements as needed
                    System.err.println("Skipped line (not enough elements): " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EnergyConsumptionGraph example = new EnergyConsumptionGraph("Energy Consumption Graph");
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
