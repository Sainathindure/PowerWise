import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ElectricityCostCalculator {

    public static void main(String[] args) {
        // Assuming the cost per unit is 7 rupees per kilowatt-hour
        double costPerUnit = 7.0;

        // CSV file path
        String csvFilePath = "D:\\College\\Minor 1\\energy_consumption.csv.csv";

        // Monthly consumption data
        Map<String, Integer[]> monthlyConsumption = readCSVData(csvFilePath);

        // Use a LinkedHashMap to maintain the order of insertion
        Map<String, Double> electricityCostMap = new LinkedHashMap<>();

        // Calculate electricity cost for each month
        for (Map.Entry<String, Integer[]> entry : monthlyConsumption.entrySet()) {
            String month = entry.getKey();
            Integer[] consumptionData = entry.getValue();
            int totalConsumption = consumptionData[3]; // Assuming the total consumption is in the last column

            double electricityCost = totalConsumption * costPerUnit; // Convert to kilowatt-hours
            electricityCostMap.put(month, electricityCost);
        }

        // Print the results in order
        electricityCostMap.forEach((month, cost) -> {
            System.out.println("Electricity cost for " + month + ": " + cost + " rupees");
        });
    }

    private static Map<String, Integer[]> readCSVData(String filePath) {
        Map<String, Integer[]> monthlyConsumption = new LinkedHashMap<>();

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath)).withSkipLines(1).build()) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                String month = nextRecord[0];
                Integer[] consumptionData = new Integer[]{
                        Integer.parseInt(nextRecord[1]),
                        Integer.parseInt(nextRecord[2]),
                        Integer.parseInt(nextRecord[3]),
                        Integer.parseInt(nextRecord[4])
                };
                monthlyConsumption.put(month, consumptionData);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return monthlyConsumption;
    }
}
