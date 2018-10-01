package coms435.pa1.hash;

import java.util.BitSet;

public class FNVHash extends BFHash
{
    private int offset;

    private int p;

    private static final long prime = 0x100000001B3L;

    @Override
    public void generateFunction(int t, int N, double k)
    {
        this.p = t*N;
        this.offset = Math.abs((int)(Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public BitSet getHash()
    {
        BitSet b;
        int index;
        long hash = this.offset;
        byte[] bytes = this.s.getBytes();

        for(int i = 0; i < bytes.length; i++)
        {
            hash = hash * FNVHash.prime;
            hash = hash ^ bytes[i];
        }
        index = Math.abs((int)hash) % p;
        b = new BitSet(index);
        b.set(index);
        return b;
    }


}