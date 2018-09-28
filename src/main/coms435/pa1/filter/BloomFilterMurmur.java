package coms435.pa1.filter;

import coms435.pa1.hash.MurHash;

public class BloomFilterMurmur extends BloomFilterAbstract
{

    public BloomFilterMurmur(int setSize, int bitsPerElement)
    {
        super(setSize, bitsPerElement);
        try
        {
            super.setHashFunction(MurHash.class.getConstructor());
        }
        catch (Exception e)
        {
            System.out.println("Failed to set the hash function");
        }
    }
}