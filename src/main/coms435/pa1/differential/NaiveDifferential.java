package coms435.pa1.differential;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class NaiveDifferential
{
	private String databasePath;
	private HashMap<String, String> map = new HashMap<String, String>();

	public NaiveDifferential(String diffPath, String databasePath)
    {
    	this.databasePath = databasePath;
    	
    	try {
			BufferedReader br = new BufferedReader(new FileReader(diffPath));
    		String line = "";
			while((line=br.readLine())!=null) {
				int numCount = 0;
				int charCount = 0;
				for(int i = 0; i<line.length();i++) {
					if(Character.isDigit(line.charAt(i))){
						numCount++;
					}else {
						numCount = 0;
					}
					if(numCount == 4) {
						break;
					}
					charCount++;
				}
				String key = line.substring(0, charCount-4);
				map.put(key, line);
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Couldn't find file");
			e.printStackTrace();
		}
    }

	public Object retrieveRecord(String key) {
		String record = map.get(key);
		if (record != null) {
			return record;
		}
		try {	
			BufferedReader brDB = new BufferedReader(new FileReader(databasePath));
    		String line = "";
			while((line=brDB.readLine())!=null) {
				if(line.contains(key)) {
					brDB.close();
					return line;
				}
			}
			brDB.close();
			System.out.println("Key does not exist in database");
		} catch (IOException e) {
			System.out.println("Couldn't find file");
			e.printStackTrace();
		}
		return null;
	}
}