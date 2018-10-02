package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.coms435.pa1.differential.BloomDifferential;
import main.coms435.pa1.differential.NaiveDifferential;
import main.coms435.pa1.filter.BloomFilterFNV;
import main.coms435.pa1.filter.BloomFilterMurmur;
import main.coms435.pa1.filter.BloomFilterRan;
public class EmpericalComparison {


	private ArrayList<String> keys;
	private String diffFilePath;
	private String databaseFilePath;
	private int setSize;
	private int bitsPerElement;
	
	public EmpericalComparison(String keysFilePath, String diffFilePath, String databaseFilePath, int setSize, int bitsPerElement){
		this.diffFilePath = diffFilePath;
    	this.databaseFilePath = databaseFilePath;
    	this.setSize = setSize;
    	this.bitsPerElement = bitsPerElement;
    	keys = getKeys(keysFilePath);
	}
	
	public ArrayList<String> getKeys(String path) {
		ArrayList<String> keys = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
    		String line = "";
			while((line=br.readLine())!=null) {
				keys.add(line);
			}
			br.close();
			return keys;
		} catch (IOException e) {
			System.out.println("Could not find the key file");
			e.printStackTrace();
		}
		return null;
	}
	
	public long BloomDiffTime(BloomDifferential bloomDiff, String key) {
		long startTime = System.nanoTime();
		bloomDiff.retrieveRecord(key);
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		return totalTime;
	}
	
	public long NaiveDiffTime(NaiveDifferential ndiff, String key) {
		long startTime = System.nanoTime();
		ndiff.retrieveRecord(key);
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		return totalTime;
	}
	
	public long averageBloomTime(BloomDifferential bloomDiff) {
		long avgTime = 0;
		for(int i=0;i<keys.size();i++) {
			avgTime+=BloomDiffTime(bloomDiff,keys.get(i));
		}
		return avgTime/keys.size();
	}
	
	public long averageNaiveTime(NaiveDifferential naiveDiff) {
		long avgTime = 0;
		for(int i=0;i<keys.size();i++) {
			avgTime+=NaiveDiffTime(naiveDiff,keys.get(i));
		}
		return avgTime/keys.size();
	}
	
	public BloomDifferential getBloomFNV() {
		return new BloomDifferential(new BloomFilterFNV(setSize,bitsPerElement),diffFilePath,databaseFilePath);
	}
	
	public BloomDifferential getBloomMurmur() {
		return new BloomDifferential(new BloomFilterMurmur(setSize,bitsPerElement),diffFilePath,databaseFilePath);
	}
	
	public BloomDifferential getBloomRan() {
		return new BloomDifferential(new BloomFilterRan(setSize,bitsPerElement),diffFilePath,databaseFilePath);
	}
	
	public NaiveDifferential getNaiveDiff() {
		return new NaiveDifferential(diffFilePath,databaseFilePath);
	}
	
	public static void main(String[] args) throws InterruptedException {
		EmpericalComparison comp = new EmpericalComparison("C:\\combinedKeys.txt","C:\\DiffFile.txt", "C:\\database.txt",400000,4);
		System.out.println("Naive Differential average time in ns: " + comp.averageNaiveTime(comp.getNaiveDiff()));
		Thread.sleep(1000);
		System.out.println("Ran Bloom Differential average time in ns: " + comp.averageBloomTime(comp.getBloomRan()));
		Thread.sleep(1000);
		System.out.println("FNV Bloom Differential average time in ns: " + comp.averageBloomTime(comp.getBloomFNV()));
		Thread.sleep(1000);
		System.out.println("Murmur Bloom Differential average time in ns: " + comp.averageBloomTime(comp.getBloomMurmur()));
		
	}
}
