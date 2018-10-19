package coms435.pa1.hash;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.io.*;
import java.util.Scanner;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.InputMismatchException;

public class HashTest
{

    public static Scanner sc = new Scanner(System.in);

    public static Set<String> list;

    public static RanHash ran = new RanHash();

    public static FNVHash fnv = new FNVHash();

    public static MurHash mur = new MurHash();

    public static int FNVTest(int t, int N)
    {
        FNVHash h = new FNVHash();
        h.generateFunction(t, N, 1);
        h.setString("asdkfjhq");
        return h.getHash();
    }

    public static int MurmurTest(int t, int N)
    {
        MurHash h = new MurHash();
        h.generateFunction(t, N, 1);
        h.setString("fwsdvwqs");
        return h.getHash();
    }

    public static int RanHash(int t, int N)
    {
        RanHash h = new RanHash();
        h.generateFunction(t, N, 1);
        h.setString("aoektbci");
        return h.getHash();
    }

    public static void main(String[] args)
    {
        File f;
        double d = 100, e = 50;
        try
        {

            String current;
            if(args.length < 1)
            {
                System.out.println("Give a file to read for test strings: ");
                current = sc.next();
            }
            else
            {
                current = args[0];
                if( args.length > 2 )
                {
                    d = Double.parseDouble(args[1]);
                    e = Double.parseDouble(args[2]);
                }
            }

            list = fillArrayList(new File(current));
            ran.generateFunction((int)d, (int)e, 0);
            fnv.generateFunction((int)d, (int)e, 0);
            mur.generateFunction((int)d, (int)e, 0);

            System.out.print("Enter a command: ");
            while(handleCommand(sc.next()) == 0)
            {
                System.out.print("Enter a command: ");
            };
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static int handleCommand(String command)
    {
        int ans = 0;
        switch(command.toLowerCase())
        {
            case "ran":
                ans = handleHashCommand(ran);
                break;
            case "fnv":
                ans = handleHashCommand(fnv);
                break;
            case "mur":
                ans = handleHashCommand(mur);
                break;
            case "quit":
                ans = 1;
                break;
            default:
                System.out.println("command not recognized try one of these: ran, fnv, mur, quit");
                break;
        }
        return ans;
    }

    public static int handleHashCommand(BFHash h)
    {
        int ans = 0, a = 100, b = 50;
        String args[] = new String[2];
        Scanner line;
        try
        {
            switch(sc.next())
            {
                case "findC":
                    ans = findCollisions(h);
                    break;
                case "generate":
                case "gen":
                    h.generateFunction(sc.nextInt(), 1, 0);
                    System.out.println("New Function Generated");
                    break;
                case "collisions":
                case "col":
                    args[0] = sc.nextLine();
                    line = new Scanner(args[0]);
                    args[0] = line.next();
                    args[1] = line.next();
                    a = 1000;
                    b = 100;
                    if(line.hasNext())
                        a = line.nextInt();
                    if(line.hasNext())
                        b = line.nextInt();
                    numberOfCollisions(h, args[0], args[1], a, b);
                    break;
                case "hash":
                    line = new Scanner(sc.nextLine());
                    while(line.hasNext())
                    {
                        args[0] = line.next();
                        System.out.println("\t" + args[0] + " = " + h.getHash(args[0]));
                    }
            }
        }
        catch(InputMismatchException e)
        {
            System.out.println("invalid input type entering initial state");
            return 0;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ans;
    }

    public static int findCollisions(BFHash h)
    {
        int ans = 0;
        Iterator<String> it = list.iterator();
        Iterator<String> it2;
        String s1,s2;

        System.out.println("Colliding Strings");
        while(it.hasNext())
        {
            s1 = it.next();
            it2 = list.iterator();
            while(it2.hasNext())
            {
                s2 = it2.next();
                if(!s1.equals(s2) && h.getHash(s1) == h.getHash(s2))
                {
                    System.out.println("\t" + s1 + " " + s2);
                }
            }
        }
        return ans;
    }

    public static int numberOfCollisions(BFHash h, String a, String b, int size,int tries)
    {
        int ans = 0;
        int count = 0;
        System.out.print("\r# of collisions: " + count + " out of " + tries);
        for (int i = 0; i < tries; i++)
        {
            h.generateFunction(size, 1, 1);
            if(h.getHash(a) == h.getHash(b))
            {
                count++;
                System.out.print("\r# of collisions: " + count + " out of " + tries);
            }
        }
        System.out.println();
        return ans;
    }

    public static Set<String> fillArrayList(File f) {
        HashMap<String, Integer> h = new HashMap<String, Integer>();
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
                    h.put(t, 1);
                }
                string = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return h.keySet();
        // System.out.println(h.keySet().size());
        // System.out.println(s.get(s.size()/2) + ": " + h.get(s.get(s.size()/2)) +
        // "\nand: " + h.get("and") );
    }
}