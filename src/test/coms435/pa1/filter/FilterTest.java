package coms435.pa1.filter;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class FilterTest
{
    private static ArrayList<String> file = new ArrayList<String>();
    private static final int NUM_STRINGS = 10000;

    public static boolean RanTest()
    {
        boolean b = false;
        BloomFilterRan r = new BloomFilterRan(200, 64);
        r.add("hello");
        r.add("goodbye");
        b = r.appears("hello");
        b = r.appears("goodbye");
        b = !r.appears("foo");
        b = !r.appears("bar");
        return b;
    }

    public static void ShakeTest(BloomFilter r)
    {
        double appear = 0;
        double not = 0;
        System.out.println("starting adding " + file.size() + " strings");
        for(int i= 0; i < file.size(); i++)
        {
            r.add(file.get(i));
            System.out.print("added string " + i + " of " + file.size() + "\r");
        }
        System.out.println("added all strings");
        for (int i = 0; i < file.size(); i++)
        {
            if(r.appears(file.get(i)))
                appear++;
            if(!r.appears(file.get(i) + "bananas"))
                not++;
            System.out.print("checked index " + i + "\r");
        }
        appear = appear/file.size();
        not = not / file.size();
        System.out.println("\ntest result:\n\tcorrect appear: " + appear + "\n\tcorrect not appear: " + not);
    }

    public static void readFile(String pathname)
    {
        File f = new File(pathname);
        String s;
        try
        {
            BufferedReader reader = Files.newBufferedReader(f.toPath());
            s = reader.readLine();
            while(s != null && file.size() < NUM_STRINGS)
            {
                if( Math.random() > .5)
                    file.add(s);
                s = reader.readLine();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        readFile(args[0]);
        System.out.println(RanTest());
        BloomFilter b = new BloomFilterRan(file.size(), 50 * 8);
        ShakeTest(b);
        b = new BloomFilterFNV(file.size(), 50*8);
        ShakeTest(b);
        b = new BloomFilterMurmur(file.size(), 50 * 8);
        ShakeTest(b);
        BloomFilterAbstract.shutdown();
    }
}