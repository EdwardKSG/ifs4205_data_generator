import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class UserGeneratorFinal {
    public static void main(String[] args) {
        String row;
        String[] data; // split row data in csv
        BufferedReader csvReader;
        LinkedList<String> maleFirstName = new LinkedList<String>();
        LinkedList<String> femaleFirstName = new LinkedList<String>();
        LinkedList<String> lastName = new LinkedList<String>();
        String[] lastNameArray = new String[0];
        LinkedList<String> address = new LinkedList<String>();
        String[] addressArray = new String[0];
        LinkedList<String> postalcode = new LinkedList<String>();
        String[] postalcodeArray = new String[0];

        // Read and prepare data from files

        // Process first names for males
        try {
            csvReader = new BufferedReader(new FileReader("C:/Users/e0134/Desktop/raw_data/babies-first-names-top-100-boys.csv"));
            row = csvReader.readLine(); // discard first line which is the table header
            while ((row = csvReader.readLine()) != null) {
                data = row.split(",");
                maleFirstName.add(data[2]);
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process first names for females
        try {
            csvReader = new BufferedReader(new FileReader("C:/Users/e0134/Desktop/raw_data/babies-first-names-top-100-girls.csv"));
            row = csvReader.readLine(); // discard first line which is the table header
            while ((row = csvReader.readLine()) != null) {
                data = row.split(",");
                femaleFirstName.add(data[2]);
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process last names
        try {
            csvReader = new BufferedReader(new FileReader("C:/Users/e0134/Desktop/raw_data/uk.txt"));
            while ((row = csvReader.readLine()) != null) {
                lastName.add(row);
            }

            // put in an array, easier for random selection as array only needs O(1) for random access
            lastNameArray = new String[lastName.size()];
            lastNameArray = lastName.toArray(lastNameArray);

            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Process locations
        try {
            csvReader = new BufferedReader(new FileReader("C:/Users/e0134/Desktop/raw_data/intermediate/address_sg.csv"));
            row = csvReader.readLine(); // discard first line which is the table header
            while ((row = csvReader.readLine()) != null) {
                data = row.split(",");
                address.add(data[0]);
                postalcode.add(data[1]);
            }

            addressArray = new String[address.size()];
            addressArray = address.toArray(addressArray);
            postalcodeArray = new String[postalcode.size()];
            postalcodeArray = postalcode.toArray(postalcodeArray);

            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Write results to a csv file
        try {

            FileWriter csvWriter = new FileWriter("test_users_final.csv");

            // csv chart header
            csvWriter.append("username");
            csvWriter.append(",");
            csvWriter.append("first_name");
            csvWriter.append(",");
            csvWriter.append("last_name");
            csvWriter.append(",");
            csvWriter.append("gender");
            csvWriter.append(",");
            csvWriter.append("dob");
            csvWriter.append(",");
            csvWriter.append("age");
            csvWriter.append(",");
            csvWriter.append("email");
            csvWriter.append(",");
            csvWriter.append("contact_number");
            csvWriter.append(",");
            csvWriter.append("address");
            csvWriter.append(",");
            csvWriter.append("postal_code");
            csvWriter.append(",");
            csvWriter.append("password");
            csvWriter.append("\n");

            int numOfLastName = lastName.size();
            int numOfAddress = address.size();
            int counter = 0;

            // create male users
            for (String name : maleFirstName) {
                counter++;
                String randomLastName = lastNameArray[new Random().nextInt(numOfLastName)];
                int randomAddressIndex = new Random().nextInt(numOfAddress);
                String randomAddress = addressArray[randomAddressIndex];
                String randomPostalcode = postalcodeArray[randomAddressIndex];
                int[] dob = randomDob();

                List<String> user = Arrays.asList(
                        name + "_" + randomLastName, //username
                        name, // first name
                        randomLastName, // last name
                        "M", // gender
                        dob[0] + "-" + dob[1] + "-" + dob[2], // dob
                        dob[3] + "", // age
                        name + "_" + randomLastName + "@gmail.com", // email
                        randomContact(counter), // contact number
                        randomAddress, // address
                        randomPostalcode, // postal code
                        randomPasswd()); // password
                csvWriter.append(String.join(",", user));
                csvWriter.append("\n");
            }

            // female users
            for (String name : femaleFirstName) {
                counter++;
                String randomLastName = lastNameArray[new Random().nextInt(numOfLastName)];
                int randomAddressIndex = new Random().nextInt(numOfAddress);
                String randomAddress = addressArray[randomAddressIndex];
                String randomPostalcode = postalcodeArray[randomAddressIndex];
                int[] dob = randomDob();

                List<String> user = Arrays.asList(
                        name + "_" + randomLastName, //username
                        name, // first name
                        randomLastName, // last name
                        "F", // gender
                        dob[0] + "-" + dob[1] + "-" + dob[2], // dob
                        dob[3] + "", // age
                        name + "_" + randomLastName + "@gmail.com", // email
                        randomContact(counter), // contact number
                        randomAddress, // address
                        randomPostalcode, // postal code
                        randomPasswd()); // password
                csvWriter.append(String.join(",", user));
                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String randomPasswd () {
        int n = (new Random().nextInt(5)) + 16; // [16, 20]
        // range of characters in a hex string
        String chars = "01234567890123456789" + "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "~!@#$%^&*()_+`-=" + ",.<>?/" + ";:'[]{}|";

        // create StringBuffer
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number to randomly select a character
            int index = (int)(chars.length() * Math.random());

            // add Character one by one in end of the string builder
            sb.append(chars.charAt(index));
        }

        return sb.toString();
    }

    public static int[] randomDob () {
        int randomYear = (new Random().nextInt(79)) + 1940; // year range [1940, 2018]
        int randomMonth = (new Random().nextInt(12)) + 1; // month range [1, 12]
        int randomDay = (randomMonth == 2? (new Random().nextInt(28)) + 1 : (new Random().nextInt(30)) + 1); // omit leap years and 31st day
        int age = 2019 - randomYear;
        if (randomMonth > 10) {
            age--;
        }

        int[] dob = {randomYear, randomMonth, randomDay, age};
        return dob;
    }

    public static String randomContact(int counter) { //the phone number generated for each counter are all different
        int number = (counter-1)*1000 + ( new Random().nextInt(1000)) + 80000000;
        return Integer.toString(number);
    }
}
