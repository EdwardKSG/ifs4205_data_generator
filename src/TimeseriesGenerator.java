import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TimeseriesGenerator {
    public static void main(String[] args) {
        int numOfEntries = 10000;

        // Write results to a csv file
        try {

            FileWriter csvWriter = new FileWriter("time_series.csv");

            // csv chart header
            csvWriter.append("timestamp");
            csvWriter.append("\n");

            for (int i = 0; i < numOfEntries; i++) {
                List<String> diag = Arrays.asList(randomTime()); // timestamp
                csvWriter.append(String.join(",", diag));
                csvWriter.append("\n");

            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String randomTime () {
        int randomYear, randomMon, randomDay, randomHour, randomMin;

        randomYear = (new Random().nextInt(3)) + 2017; // year range 2017, 2018, 2019

        // if 2019, then only up to September
        randomMon = (randomYear == 2019? (new Random().nextInt(9)) + 1 : (new Random().nextInt(12)) + 1);

        // omit leap years and 31st day
        randomDay = (randomMon == 2? (new Random().nextInt(28)) + 1 : (new Random().nextInt(30)) + 1);

        randomHour = (new Random().nextInt(24)); // 0:xx to 23:xx

        randomMin = (new Random().nextInt(60)); // 0 to 59

        return randomYear + "-" + digitToString(randomMon) + "-" + digitToString(randomDay) + "T" + digitToString(randomHour) + ":" + digitToString(randomMin);
    }

    // num is only in the range of month/day/hour/min, at most two digits, non negative.
    public static String digitToString (int num) {
        String numStr;

        if (num < 10) {
            numStr = "0" + num;
        } else {
            numStr = "" + num;
        }

        return numStr;
    }
}
