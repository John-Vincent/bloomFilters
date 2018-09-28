package coms435.pa1.filter;

import java.util.Collection;
import java.util.ArrayList;

import coms435.pa1.hash.BFHash;
import coms435.pa1.hash.FNVHash;

public class BloomFilterFNV extends BloomFilterAbstract
{

    public BloomFilterFNV(int setSize, int bitsPerElement)
    {
        super(setSize, bitsPerElement);
        try
        {
            super.setHashFunction(FNVHash.class.getConstructor());
        }
        catch(Exception e)
        {
            System.out.println("Failed to set the hash function");
        }
    }

}