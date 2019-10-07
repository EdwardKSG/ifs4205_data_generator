import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class LocationCreator {

    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("C:/Users/e0134/Desktop/raw_data/buildings.json"))
        {
            // read JSON file
            Object fObj = jsonParser.parse(reader);

            JSONArray list = (JSONArray) fObj;

            FileWriter csvWriter = new FileWriter("address_sg.csv");

            // csv chart header
            csvWriter.append("address");
            csvWriter.append(",");
            csvWriter.append("postalcode");
            csvWriter.append("\n");

            // iterate over json array
            list.forEach( obj -> {
                try {
                    parseObject( (JSONObject) obj, csvWriter );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            csvWriter.flush();
            csvWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseObject(JSONObject obj, FileWriter csvWriter) throws IOException
    {
        //Get address
        String address = (String) obj.get("ADDRESS");
        if (address.contains(",")) {
            address = address.substring(0, address.indexOf(","));
        }
        System.out.println(address);

        //Get postal code
        String postalCode = (String) obj.get("POSTAL");
        System.out.println(postalCode);

        csvWriter.append(address);
        csvWriter.append(",");
        csvWriter.append(postalCode);
        csvWriter.append("\n");
    }
}
