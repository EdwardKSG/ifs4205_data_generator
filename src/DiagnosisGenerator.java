import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DiagnosisGenerator {
    public static void main(String[] args) {
        String[] types= {"Dengue", "Stomach Cancer", "HIV", "Lung Cancer", "Liver Tumor",
                "Cardiovascular Disease", "Teeth Caries", "Short-sighted", "Arthritis"};
        int[] occurrence = {500, 300, 500, 300, 500, 700, 2000, 2000, 1000};
        int[] baseDuration = {20, 40, 20, 40, 30, 30, 15, 10, 30}; //base duration, plus up to 50min

        int numOfTypes = types.length;

        // Write results to a csv file
        try {

            FileWriter csvWriter = new FileWriter("diagnosis.csv");

            // csv chart header
            csvWriter.append("title");
            csvWriter.append(",");
            csvWriter.append("time_start");
            csvWriter.append(",");
            csvWriter.append("time_end");
            csvWriter.append("\n");

            for (int i = 0; i < numOfTypes; i++) {
                for (int j = 0; j < occurrence[i]; j++) {
                    int duration = (new Random().nextInt(51)) + baseDuration[i];
                    String[] timePair = randomTime(duration);

                    List<String> diag = Arrays.asList(
                            types[i], //title
                            timePair[0], // time_start
                            timePair[1]); // time_end
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

    public static String[] randomTime (int duration) {
        int randomYear, randomMon, randomDay, randomHour, randomMin;

        randomYear = (new Random().nextInt(3)) + 2017; // year range 2017, 2018, 2019

        // if 2019, then only up to September
        randomMon = (randomYear == 2019? (new Random().nextInt(9)) + 1 : (new Random().nextInt(12)) + 1);

        // omit leap years and 31st day
        randomDay = (randomMon == 2? (new Random().nextInt(28)) + 1 : (new Random().nextInt(30)) + 1);

        randomHour = (new Random().nextInt(16)) + 6; // 7:xx to 21:xx

        randomMin = (new Random().nextInt(60)); // 0 to 59

        String start = randomYear + "-" + digitToString(randomMon) + "-" + digitToString(randomDay) + "T" + digitToString(randomHour) + ":" + digitToString(randomMin);

        String end = "";
        int minEnd = randomMin + duration;
        if (minEnd < 60) {
            end = randomYear + "-" + digitToString(randomMon) + "-" + digitToString(randomDay) + "T" + digitToString(randomHour) + ":" + digitToString(minEnd);
        } else if (minEnd < 120) {
            end = randomYear + "-" + digitToString(randomMon) + "-" + digitToString(randomDay) + "T" + digitToString(randomHour+1) + ":" + digitToString(minEnd-60);
        } else if (minEnd < 180) {
            end = randomYear + "-" + digitToString(randomMon) + "-" + digitToString(randomDay) + "T" + digitToString(randomHour+2) + ":" + digitToString(minEnd-120);
        }
        // should not be any other cases based on the design of our fake data


        String[] pair = {start, end};

        return pair;
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
