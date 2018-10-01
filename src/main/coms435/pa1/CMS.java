package coms435.pa1;

import coms435.pa1.hash.*;
import java.util.ArrayList;

public class CMS
{

    protected static final double LN_2 = Math.log(2);

    private int[][] counters;

    private BFHash[] hashes;

    private ArrayList<String> s;

    public CMS(float epsilon, float delta, ArrayList<String> s)
    {
        this.counters = new int[(int)(2l/epsilon)][(int)(LN_2 / delta)];
        this.hashes = new BFHash[(int)(2l/epsilon)];
        this.s = s;
        for(int i = 0; i < this.hashes.length; i++)
        {
            this.hashes[i] = new RanHash();
            this.hashes[i].generateFunction(this.counters[0].length, 80, 1);
        }
        for(int i = 0; i < s.size(); i++)
        {
            for(int j = 0; j < this.counters.length; j++)
            {
                this.hashes[j].setString(s.get(i));
                int index = this.hashes[j].getHash().nextSetBit(0) % this.counters[j].length;
                this.counters[j][index]++;
            }
        }
    }

    public int approximateFrequency(String x)
    {
        int ans = Integer.MAX_VALUE;
        int hash;
        for(int j = 0; j < this.counters.length; j++)
        {
            this.hashes[j].setString(x);
            hash = this.hashes[j].getHash().nextSetBit(0) % this.counters[j].length;
            ans = ans < this.counters[j][hash] ? ans : this.counters[j][hash];
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
}