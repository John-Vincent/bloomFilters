package coms435.pa1;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import coms435.pa1.filter.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FalsePositives
{

    public static ArrayList<String> strings_in = new ArrayList<String>();
    public static ArrayList<String> strings_out = new ArrayList<String>();

    public static int max_length = 0;

    /**
     * this program takes a file path as the first parameter and an optional numerical second parameter N.
     *
     * it then puts up a max of N lines from the file into one array
     * and the rest of the lines into a second array as long as an identical line wasn't already put into the first array
     *
     * it then runs a false positive test with the three different bloom filters using the two arrays
     * by placing the strings in the first array into the filter and then checking the filter for the strings from the
     * second array
     * @param args
     */
    public static void main(String[] args)
    {
        int max = 1000;
        BloomFilter b;

        if(args.length < 1)
        {
            System.out.println("you must pass a text file to use for the test as the first argument\nand the number of strings from the file to insert as the second argument");
            System.exit(-1);
        }

        if(args.length > 1)
        {
            try
            {
                max = Integer.parseInt(args[1]);
            }
            catch(Exception e)
            {
                System.out.println("max string argument must be a numerical value");
                System.exit(-1);
            }
        }

        readFile(args[0], max);
        b = new BloomFilterRan(strings_in.size(), 4);
        FPTest(b, 4);
        b = new BloomFilterRan(strings_in.size(), 8);
        FPTest(b, 8);
        b = new BloomFilterRan(strings_in.size(), 10);
        FPTest(b, 10);
        b = new BloomFilterFNV(strings_in.size(), 4);
        FPTest(b, 4);
        b = new BloomFilterFNV(strings_in.size(), 8);
        FPTest(b, 8);
        b = new BloomFilterFNV(strings_in.size(), 10);
        FPTest(b, 10);
        b = new BloomFilterMurmur(strings_in.size(), 4);
        FPTest(b, 4);
        b = new BloomFilterMurmur(strings_in.size(), 8);
        FPTest(b, 8);
        b = new BloomFilterMurmur(strings_in.size(), 10);
        FPTest(b, 10);
        BloomFilterAbstract.shutdown();
    }

    /**
     * adds all the words in the strings_in array to the filter
     * then checks for the appearance of all strings in strings_out array
     * since these arrays have no words in common if appear returns true
     * then that is a false positive.
     * @param r
     */
    public static void FPTest(BloomFilter r, int n)
    {
        double fp = 0;
        System.out.println(r.getClass() + " test: " + n + " bits per element");
        for(int i= 0; i < strings_in.size(); i++)
        {
            r.add(strings_in.get(i));
            System.out.print("\tadded string " + i + " of " + strings_in.size() + "\r");
        }
        System.out.println("\tadded all strings                       ");
        for (int i = 0; i < strings_out.size(); i++)
        {
            if(r.appears(strings_out.get(i)))
            {
                //System.out.println("\tfailed on string: " + strings_out.get(i) + "           ");
                fp++;
            }
            System.out.print("\tchecked index " + i + " of " + (strings_out.size() - 1) + "\r");
        }
        //this is in case it is not evenly divisible
        fp = fp / strings_out.size();
        System.out.println("\tresult: false positive percentage: " + fp*100 + "\n");
    }

    /**
     * this function reads lines from a file and has a 50% chance to add the string to the
     * list of strings that will be used durring the test. each line should contain a unique string
     * @param pathname
     * @param NUM_STRINGS
     */
    public static void readFile(String pathname, int NUM_STRINGS)
    {
        File f = new File(pathname);
        String string;
        String t;
        Pattern p = Pattern.compile("[a-zA-Z0-9']{3,}");
        Matcher m;
        try {
            BufferedReader reader = Files.newBufferedReader(f.toPath());
            string = reader.readLine();
            while (string != null) {
                m = p.matcher(string);
                while (m.find()) {
                    t = m.group().toLowerCase();
                    if(strings_in.size() < NUM_STRINGS)
                        strings_in.add(t);
                    else if (!strings_in.contains(t))
                        strings_out.add(t);
                }
                string = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}