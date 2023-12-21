import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static int processArray(ArrayList<Integer> arr) {
        int i = 0;
        int n = arr.size();

        while (i < n) {
            // Check if the current element is a single-digit number
            if (0 <= arr.get(i) && arr.get(i) <= 9) {
                // Find the sequence of consecutive single-digit numbers
                int start = i;
                while (i < n && 0 <= arr.get(i) && arr.get(i) <= 9) {
                    i++;
                }

                // Retain the last number in the sequence
                int lastNumber = arr.get(i - 1);
                arr.subList(start, i).clear();
                arr.add(start, lastNumber);
                n = arr.size();
            } else {
                i++;
            }
        }

        return arr.size();
    }

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<>();
        try (Scanner sac = new Scanner(System.in))
        {
            while (sac.hasNextInt()) {
            int num = sac.nextInt();
            if (num < 0) {
                break;
            }
            arr.add(num);
        }
        }

        int length = processArray(arr);
        for (int i = 0; i < length; i++) {
            System.out.println(arr.get(i));
        }
    }
}
