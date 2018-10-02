package coms435.pa1.differential;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import coms435.pa1.filter.BloomFilter;


public class BloomDifferential
{
    private BloomFilter filter;
    private String diffPath;
  //bits per element at 4, 8, or 10
    private String databasePath;
    
    
    public BloomDifferential(BloomFilter filter, String diffPath, String databasePath)
    {
    	this.diffPath = diffPath;
    	this.databasePath = databasePath;
    	this.filter = createFilter(filter);
    	
    }

    public BloomFilter createFilter(BloomFilter filter)
    {
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(diffPath));
    		String line = "";
			//int linesEntered = 0;
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
				filter.add(key);
				//linesEntered++;
				
			}
			//System.out.println("lines entered in filter: "+ linesEntered);
			br.close();
		} catch (IOException e) {
			System.out.println("Could not find DiffFile.txt");
			e.printStackTrace();
		}
    	
    	return filter;
    }

    public Object retrieveRecord(String key)
    {
        if(filter.appears(key)) {
			//int linesSearched = 0;
			try {
				BufferedReader br = new BufferedReader(new FileReader(diffPath));
	    		String line = "";
				while((line=br.readLine())!=null) {
					//linesSearched++;
					if(line.contains(key)) {
						br.close();
						return line;
					}
				}
				//System.out.println("lines searched in diff: "+ linesSearched);
				br.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
        }
        try {
			//int linesSearched = 0;
			BufferedReader br = new BufferedReader(new FileReader(databasePath));
    		String line = "";
			while((line=br.readLine())!=null) {
				//linesSearched++;
				if(line.contains(key)) {
					br.close();
					return line;
				}
			}
			br.close();
			//System.out.println("lines searched in database: "+ linesSearched);
			System.out.println("Key does not exist in database");
        } catch (IOException e) {
			System.out.println("Couldn't find file");
			e.printStackTrace();
		}
    	return null;
    }
}