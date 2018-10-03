package coms435.pa1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import coms435.pa1.differential.BloomDifferential;
import coms435.pa1.differential.NaiveDifferential;
import coms435.pa1.filter.BloomFilterFNV;
import coms435.pa1.filter.BloomFilterMurmur;
import coms435.pa1.filter.BloomFilterRan;
import java.lang.instrument.Instrumentation;

public class EmpericalComparison {


	private ArrayList<String> keys;
	private String diffFilePath;
	private String databaseFilePath;
	private int setSize;
	private int bitsPerElement;
	private static Instrumentation instrumentation;

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

	public static void premain(String args, Instrumentation inst) {
		instrumentation = inst;
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(instrumentation);
		System.exit(-1);
		EmpericalComparison comp = new EmpericalComparison( args[0], args[1], args[2], 400000, 4);

		System.out.println("Ran Bloom Differential average time in ns: " + comp.averageBloomTime(comp.getBloomRan()));
		System.out.println("\n\tmemory useage: " + Runtime.getRuntime().totalMemory());
		System.gc();
		System.out.println("waiting for gc");
		Thread.sleep(2000);
		System.out.println("FNV Bloom Differential average time in ns: " + comp.averageBloomTime(comp.getBloomFNV()));
		System.out.println("\n\tmemory useage: " + Runtime.getRuntime().totalMemory());
		System.gc();
		System.out.println("waiting for gc");
		Thread.sleep(2000);
		System.out.println("Murmur Bloom Differential average time in ns: " + comp.averageBloomTime(comp.getBloomMurmur()));
		System.out.println("\n\tmemory useage: " + Runtime.getRuntime().totalMemory());
		System.gc();
		System.out.println("waiting for gc");
		Thread.sleep(2000);
		System.out.println("Naive Differential average time in ns: " + comp.averageNaiveTime(comp.getNaiveDiff()));
		System.out.println("\n\tmemory useage: " + Runtime.getRuntime().totalMemory());
	}
}
