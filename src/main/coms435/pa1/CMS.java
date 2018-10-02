package coms435.pa1;

import coms435.pa1.hash.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import java.util.ArrayList;

public class CMS
{

    protected static final double LN_2 = Math.log(2);

    private static ExecutorService threads = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private LinkedBlockingQueue<int[]> queue = new LinkedBlockingQueue<int[]>();

    private int[][] counters;

    private BFHash[] hashes;

    private ArrayList<String> s;

    public CMS(float epsilon, float delta, ArrayList<String> s)
    {
        this.counters = new int[(int)(2l/epsilon)][(int)(LN_2 / delta)];
        this.hashes = new BFHash[(int)(2l/epsilon)];
        int[] hash;
        this.s = s;
        for(int i = 0; i < this.hashes.length; i++)
        {
            this.hashes[i] = new RanHash();
            this.hashes[i].generateFunction(this.counters[0].length, 80, 1);
            this.hashes[i].setQueue(queue);
        }
        System.out.println();
        for(int i = 0; i < s.size(); i++)
        {
            startHash(s.get(i));
            System.out.print("\033[1Aadding string " + i + " of " + s.size() + " " + s.get(i) +  "        \n");
            for(int j = 0; j < this.hashes.length; j++)
            {
                System.out.print("taking hash number " + j + "    \r");
                try
                {
                    hash = this.queue.take();
                    this.counters[hash[1]][hash[0] % this.counters[0].length]++;
                }
                catch(Exception e)
                {
                    System.out.println("program interrupted");
                }
            }
        }
    }

    public int approximateFrequency(String x)
    {
        int ans = Integer.MAX_VALUE;
        int[] hash;
        int index;
        startHash(x);
        for(int j = 0; j < this.counters.length; j++)
        {
            try
            {
                hash = queue.take();
                index = hash[0] % this.counters[hash[1]].length;
                ans = ans < this.counters[hash[1]][index] ? ans : this.counters[hash[1]][index];
            }
            catch(Exception e)
            {
                System.out.println("program interrupted");
            }
        }
        return ans;
    }

    public ArrayList<String> approximateHeavyHitter(float q, float r)
    {
        //>= qN  < rN
        ArrayList<String> ans = new ArrayList<String>();
        float qn = q * this.s.size();
        float rn = r * this.s.size();
        int frequency;

        for (int i = 0; i < s.size(); i++)
        {
            if(!ans.contains(s.get(i)))
            {
                frequency = this.approximateFrequency(s.get(i));
                if(frequency >= qn && frequency < rn)
                {
                    ans.add(s.get(i));
                }
            }
        }
        return ans;
    }

    @Override
    public String toString()
    {
        String ans = "";
        for(int i = 0; i < counters.length; i++)
        {
            for(int j = 0; j < counters[i].length; j++)
            {
                ans += " " +counters[i][j];
            }
            ans += "\n";
        }
        return ans;
    }

    /**
     * starts concurrently hashing the string with all hashing functinos in
     * this.hashes
     *
     * @param s
     */
    private void startHash(String s)
    {
        for (int i = 0; i < hashes.length; i++)
        {
            hashes[i].setString(s);
            hashes[i].setIndex(i);
            threads.execute(hashes[i]);
        }
    }
}