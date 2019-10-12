import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ReadingGenerator {
    public static void main(String[] args) {
        String[] types= {"Blood Pressure", "Heart Rate", "Temperature"};
        int[] occurrence = {15000, 15000, 10000};

        // the units are: blood pressure -> mmHg, heart rate -> beats per minute (bpm), temperature -> degree celsius

        int numOfTypes = types.length;

        // Write results to a csv file
        try {

            FileWriter csvWriter = new FileWriter("readings.csv");

            // csv chart header
            csvWriter.append("type");
            csvWriter.append(",");
            csvWriter.append("data");
            csvWriter.append(",");
            csvWriter.append("time");
            csvWriter.append("\n");

            for (int i = 0; i < numOfTypes; i++) {
                for (int j = 0; j < occurrence[i]; j++) {
                    String data = "";
                    switch (i) {
                        case 0: // blood pressure
                            int high = (new Random().nextInt(100)) + 75; // 75 to 174
                            int low = (int)((high * 0.6) + new Random().nextInt(11)); // hight * 0.6 + [0, 10]
                            data = high + "/" + low;
                            break;
                        case 1: // heart rate
                            data = Integer.toString(new Random().nextInt(121) + 40); // 40 to 160
                            break;
                        case 2: // temperature
                            data = Double.toString((new Random().nextInt(61)) * 0.1 + 34.5); // 34.5 to 40.5

                    }

                    List<String> diag = Arrays.asList(
                            types[i], //title
                            data, // time_start
                            randomTime()); // time_end
                    csvWriter.append(String.join(",", diag));
                    csvWriter.append("\n");

                }
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
