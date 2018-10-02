package coms435.pa1;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pa1Test
{

    public static int[] count;

    public static ArrayList<String> s = new ArrayList<String>();

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
        System.out.println(cms);

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
        Pattern p = Pattern.compile("[a-zA-Z0-9'\\-]{3,}");
        Matcher m;
        try {
            BufferedReader reader = Files.newBufferedReader(f.toPath());
            string = reader.readLine();
            while (string != null) {
                m = p.matcher(string);
                while(m.find())
                {
                    t = m.group();
                    if(!t.toLowerCase().equals("the"))
                        s.add(t);
                }
                string = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}