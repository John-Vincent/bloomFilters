package coms435.pa1;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pa1Test
{

    public static int[] count;

    public static ArrayList<String> s = new ArrayList<String>();

    private static HashMap<String,Integer> h = new HashMap<String,Integer>();


    public static void main(String[] args)
    {
        float e=0, d=0;
        File f = null;

        if(args.length < 3)
        {
            System.out.println("you must provide a text file, epsilon, and delta values as arguments");
            System.exit(-1);
        }

        try
        {
            f = new File(args[0]);
            e = Float.parseFloat(args[1]);
            d = Float.parseFloat(args[2]);
        }
        catch(Exception ex)
        {
            System.out.println("argument malformed: ");
            ex.printStackTrace();
            System.exit(-1);
        }

        fillArrayList(f);
        CMS cms = new CMS(e, d, s);
        //System.out.println("approx average frequency: " + cms.averageFrequency() + "\n tears: " + cms.approximateFrequency("tears") + "\nand: " + cms.approximateFrequency("and"));
        Scanner sc = new Scanner(System.in);

        while(true)
        {
            System.out.print("waiting for hh or freq cmd: ");
            String cmd = sc.next();
            float q, r;

            switch(cmd)
            {
                case "HH":
                case "hh":
                case "heavy hitter":
                    q = sc.nextFloat();
                    r = sc.nextFloat();
                    heavyHitter(cms, q, r);
                    break;
                case "frequency":
                case "freq":
                    cmd = sc.next();
                    System.out.println("calculating freq of: " + cmd);
                    System.out.println("\tapprox: " + cms.approximateFrequency(cmd) + " actual: " + h.get(cmd));
                    break;
                case "exit":
                case "close":
                case "bye":
                case "quit":
                    cms.shutdown();
                    System.exit(-1);
                    break;
            }
        }
    }

    public static void approx(CMS cms)
    {
        for(int i = 0; i < s.size(); i++)
        {
            //System.out.println(strings[i] + "\n\tapproximate frequency: " + cms.approximateFrequency(strings[i]) + "\n\tactual frequency: " + count[i]);
        }
    }

    public static void heavyHitter(CMS cms, float q, float r)
    {
        System.out.println("Heavy hitter list for q: " + q + " r: " + r);
        System.out.println("\t" + cms.approximateHeavyHitter(q, r));
    }

    public static void fillArrayList(File f)
    {
        String string;
        String t;
        Pattern p = Pattern.compile("[a-zA-Z0-9']{3,}");
        Matcher m;
        try {
            BufferedReader reader = Files.newBufferedReader(f.toPath());
            string = reader.readLine();
            while (string != null) {
                m = p.matcher(string);
                while(m.find())
                {
                    t = m.group().toLowerCase();
                    if(h.get(t) == null)
                        h.put(t,1);
                    else
                        h.put(t, h.get(t) + 1);
                    if(!t.equals("the"))
                        s.add(t);
                }
                string = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(h.keySet().size());
        //System.out.println(s.get(s.size()/2) + ": " + h.get(s.get(s.size()/2)) + "\nand: " + h.get("and") );
    }
}