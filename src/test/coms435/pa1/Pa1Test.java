package coms435.pa1;

import java.util.ArrayList;

public class Pa1Test
{
    public static String[] strings = {"bananas", "mark", "matt", "trump", "brett", "kita", "yanny", "lorral"};

    public static int[] count;

    public static ArrayList<String> s = new ArrayList<String>();

    public static void main(String[] args)
    {
        int size = 100;
        float e=0, d=0;

        if(args.length < 2)
        {
            System.out.println("you must provide epsilon and delta values as arguments");
            System.exit(-1);
        }

        try
        {
            e = Float.parseFloat(args[0]);
            d = Float.parseFloat(args[1]);
        }
        catch(Exception ex)
        {
            System.out.println("argument must be numerical value");
        }

        if(args.length > 2)
        {
            try
            {
                size = Integer.parseInt(args[2]);
            }
            catch(Exception ex)
            {
                System.out.println("argument must be numerical value");
            }
        }
        fillArrayList(size);
        CMS cms = new CMS(e, d, s);
        System.out.println(cms);
        approx(cms);
        for(int i = 3; i < args.length; i+=2)
        {
            float q=0, r=0;
            if(args.length > 2)
            try
            {
                q = Float.parseFloat(args[i]);
                r = Float.parseFloat(args[i+1]);
            }
            catch(Exception ex)
            {
                System.out.println("argument must be numerical value: " + args[i] + ", " + args[i+1]);
                System.exit(-1);
            }
            heavyHitter(cms, q, r);
        }
    }

    public static void approx(CMS cms)
    {
        for(int i = 0; i < strings.length; i++)
        {
            System.out.println(strings[i] + "\n\tapproximate frequency: " + cms.approximateFrequency(strings[i]) + "\n\tactual frequency: " + count[i]);
        }
    }

    public static void heavyHitter(CMS cms, float q, float r)
    {
        System.out.println("Heavy hitter list for q: " + q + " r: " + r);
        System.out.println("\t" + cms.approximateHeavyHitter(q, r));
    }

    public static void fillArrayList(int size)
    {
        int index;
        count = new int[strings.length];
        for(int i = 0; i < size; i++)
        {
            index = (int)(Math.random() * strings.length);
            count[index]++;
            s.add(strings[index]);
        }
    }
}