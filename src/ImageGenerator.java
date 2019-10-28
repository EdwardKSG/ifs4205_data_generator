import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ImageGenerator {
    public static void main(String[] args) {
        String[] types = {"Cancer", "MRI", "Ultrasound", "Xray"};
        int[] occurrence = {300, 800, 2000, 10000};

        int numOfTypes = types.length;

        String[] cancer = {"brain", "stomach", "lung", "breast", "bone", "oral", "throat", "liver", "skin", "pancreas", "intestinal"};
        int numOfCancers = cancer.length;

        // https://stanfordhealthcare.org/medical-tests/m/mri/types.html
        String[] mri = {"functional", "breast", "MRA", "MRV", "cardiac"};
        int numOfMris = mri.length;

        // https://www.atlanticmedicalimaging.com/radiology-services/ultrasound/types-of-ultrasounds/
        String[] ultra = {"abdominal", "pelvic", "transabdominal", "transvaginal", "transrectal", "obstetric"};
        int numOfUltras = ultra.length;

        // https://www.lalpathlabs.com/blog/what-are-the-different-types-of-x-rays-and-their-usage/
        String[] xray = {"chest", "lungs", "abdomen", "teeth", "bones", "KUB", "standard"};
        int numOfXrays = xray.length;

        // Write results to a csv file
        try {

            FileWriter csvWriter = new FileWriter("images.csv");

            // csv chart header
            csvWriter.append("type");
            csvWriter.append(",");
            csvWriter.append("title");
            csvWriter.append(",");
            csvWriter.append("time");
            csvWriter.append("\n");

            for (int i = 0; i < numOfTypes; i++) {
                for (int j = 0; j < occurrence[i]; j++) {
                    String title = "";
                    int x;
                    switch (i) {
                        case 0: // cancer
                            x = (new Random().nextInt(numOfCancers));
                            title = types[i] + "_" + cancer[x];
                            break;
                        case 1: // MRI
                            x = (new Random().nextInt(numOfMris));
                            title = types[i] + "_" + mri[x];
                            break;
                        case 2: // ultrasound
                            x = (new Random().nextInt(numOfUltras));
                            title = types[i] + "_" + ultra[x];
                            break;
                        case 3: // x-ray
                            x = (new Random().nextInt(numOfXrays));
                            title = types[i] + "_" + xray[x];

                    }

                    List<String> diag = Arrays.asList(
                            types[i], // type
                            title, // title
                            randomTime()); // timestamp
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
