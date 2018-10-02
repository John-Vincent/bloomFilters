package coms435.pa1.differential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
			//or path to DiffFile.txt
			File diff = new File(diffPath);
			Scanner diffScanner = new Scanner(diff);
			while(diffScanner.hasNextLine()) {
				String line = diffScanner.nextLine();
				if(line.contains(key)) {
					diffScanner.close();
					return line;
				}
			}
			diffScanner.close();
			//or path to database.txt
			File database = new File(databasePath);
			Scanner dbScanner = new Scanner(database);
			while(dbScanner.hasNextLine()) {
				String line = dbScanner.nextLine();
				if(line.contains(key)) {
					dbScanner.close();
					return line;
				}
			}
			dbScanner.close();
			System.out.println("Key does not exist in database");
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find file");
			e.printStackTrace();
		}
		return null;
	}
}