package coms435.pa1.filter;

import coms435.pa1.hash.RanHash;

public class BloomFilterRan extends BloomFilterAbstract
{

    public BloomFilterRan(int setSize, int bitsPerElement)
    {
        super(setSize, bitsPerElement);
        try
        {
            super.setHashFunction(RanHash.class.getConstructor());
        }
        catch(Exception e)
        {
            System.out.println("Failed to set the hash function");
        }
    }
}