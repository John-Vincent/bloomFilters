package main.coms435.pa1.differential;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import main.coms435.pa1.filter.BloomFilterRan;
import main.coms435.pa1.filter.BloomFilter;
import main.coms435.pa1.filter.BloomFilterFNV;
import main.coms435.pa1.filter.BloomFilterMurmur;

public class BloomDifferential
{
    private BloomFilter filter;
    private int filterType;
    private String diffPath;
  //bits per element at 4, 8, or 10
    private int bitsPerElement;
    private String databasePath;
    
    
    public BloomDifferential(int filterType, String diffPath, int bitsPerElement, String databasePath)
    {
    	this.filterType = filterType;
    	this.diffPath = diffPath;
    	this.bitsPerElement = bitsPerElement;
    	this.databasePath = databasePath;
    }

    public BloomFilter createFilter()
    {
    	//or path to the file
    	File file = new File(diffPath);
    	ArrayList<String> keys = new ArrayList<String>();
    	try {
			Scanner diffScanner = new Scanner(file);
			while(diffScanner.hasNextLine()) {
				String s = diffScanner.nextLine();
				int numCount = 0;
				int charCount = 0;
				for(int i = 0; i<s.length();i++) {
					if(Character.isDigit(s.charAt(i))){
						numCount++;
					}else {
						numCount = 0;
					}
					if(numCount == 4) {
						break;
					}
					charCount++;
				}
				String key = s.substring(0, charCount-4);
				keys.add(key);
			}
			diffScanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find DiffFile.txt");
			e.printStackTrace();
		}
    	
    	
    	if(filterType == 1) {
    		filter = new BloomFilterFNV(keys.size(),bitsPerElement);
    	}else if(filterType == 2) {
    		filter = new BloomFilterMurmur(keys.size(),bitsPerElement);
    	}else {
    		filter = new BloomFilterRan(keys.size(),bitsPerElement);
    	}
    	
    	//add keys to filter
    	for(int i = 0; i<keys.size();i++) {
    		filter.add(keys.get(i));
    	}
    	
    	return filter;
    }

    public Object retrieveRecord(String key)
    {
        if(filter.appears(key)) {
        	File file = new File(diffPath);
			Scanner diffScanner;
			try {
				diffScanner = new Scanner(file);
				while(diffScanner.hasNextLine()) {
					String line = diffScanner.nextLine();
					if(line.contains(key)) {
						diffScanner.close();
						return line;
					}
				}
				diffScanner.close();
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
        }
        try {
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