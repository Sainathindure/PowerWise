import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class ConsumptionSorting {

    public static void main(String[] args) {
        // Provide the path to your CSV file
        String csvFilePath = "D:\\College\\Minor 1\\energy_consumption.csv.csv";

        // Read data from CSV
        List<MonthlyConsumption> monthlyConsumptions = readDataFromCSV(csvFilePath);

        // Sorting based on Total_Consumption using Quick Sort
        quickSort(monthlyConsumptions, 0, monthlyConsumptions.size() - 1);

        // Print the sorted data
        for (MonthlyConsumption month : monthlyConsumptions) {
            System.out.println(month);
        }
    }

    private static List<MonthlyConsumption> readDataFromCSV(String filePath) {
        List<MonthlyConsumption> data = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            // Read and ignore the header row
            csvReader.readNext();

            List<String[]> records = csvReader.readAll();

            for (String[] record : records) {
                String month = record[0];
                int sub_metering_1 = Integer.parseInt(record[1]);
                int sub_metering_2 = Integer.parseInt(record[2]);
                int sub_metering_3 = Integer.parseInt(record[3]);
                int totalConsumption = Integer.parseInt(record[4]);

                data.add(new MonthlyConsumption(month, sub_metering_1, sub_metering_2, sub_metering_3, totalConsumption));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private static void quickSort(List<MonthlyConsumption> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);

            // Recursively sort elements before and after the pivot
            quickSort(list, low, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, high);
        }
    }

    private static int partition(List<MonthlyConsumption> list, int low, int high) {
        int pivot = list.get(high).getTotalConsumption();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (list.get(j).getTotalConsumption() <= pivot) {
                i++;

                // Swap list[i] and list[j]
                MonthlyConsumption temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        // Swap list[i+1] and list[high] (pivot)
        MonthlyConsumption temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }
}

class MonthlyConsumption {
    private String month;
    private int sub_metering_1;
    private int sub_metering_2;
    private int sub_metering_3;
    private int totalConsumption;

    public MonthlyConsumption(String month, int sub_metering_1, int sub_metering_2, int sub_metering_3, int totalConsumption) {
        this.month = month;
        this.sub_metering_1 = sub_metering_1;
        this.sub_metering_2 = sub_metering_2;
        this.sub_metering_3 = sub_metering_3;
        this.totalConsumption = totalConsumption;
    }

    public int getTotalConsumption() {
        return totalConsumption;
    }

    @Override
    public String toString() {
        return "Month: " + month +
                ", Sub_metering_1: " + sub_metering_1 +
                ", Sub_metering_2: " + sub_metering_2 +
                ", Sub_metering_3: " + sub_metering_3 +
                ", Total_Consumption: " + totalConsumption;
    }
}
