package lektion3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

/**
 * 
 * @author mikael
 * Main class for reading a JSON file and printing it in the console.
 */
public class ReaderJSON {

	/*
	 * Main method for running the program.
	 */
	public static void main(String[] args) {
		// Declaration of variables.
		JSONParser jp = new JSONParser();
		String path = "Materiallista.json";
		String apu = "";
		
		// Try to parse JSON from the file.
		try {
			JSONArray arr = (JSONArray) jp.parse(new FileReader(path));
			
			// Get the headers in a Set.
			JSONObject ele = (JSONObject) arr.get(0);
			Set<String> headers = ele.keySet();
			
			// Print the headers.
			for (Iterator<String> iterator = headers.iterator(); iterator.hasNext();) {
				System.out.print(iterator.next());
				if (iterator.hasNext()) System.out.print(", ");
			}
			System.out.println();
			
			// Print the rest of the rows.
			for(int i=0 ; i < arr.size(); i++) {
				ele = (JSONObject) arr.get(i);
			    System.out.print(ele.get("Item"));
			    apu = ele.get("Amount per unit").toString();
			    // Check if Amount per unit has "(x" and calculate total amount.
			    if(apu.contains("(x")) {
			    	System.out.print(", ");
			    	System.out.print(apu);
			    	System.out.print(", ");
					
					// Put the result of the calculation back in place of the '?'.
			    	String[] numbers = apu.split("\\(x");
					ele.put("Total amount", String.valueOf(
							Integer.parseInt(numbers[0].strip()) * 
							Integer.parseInt(numbers[1].replace(")", "").strip())));
					System.out.print(ele.get("Total amount"));
				}
				System.out.println();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
