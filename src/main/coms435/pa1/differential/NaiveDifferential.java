package coms435.pa1.differential;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class NaiveDifferential
{
	private String databasePath;
	private String diffPath;

	public NaiveDifferential(String diffPath, String databasePath)
    {

    	this.diffPath = diffPath;
    	this.databasePath = databasePath;
    }

	public Object retrieveRecord(String key) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(diffPath));
    		String line = "";
			while((line=br.readLine())!=null) {
				if(line.contains(key)) {
					br.close();
					return line;
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Couldn't find file");
			e.printStackTrace();
		}
			//or path to database.txt
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