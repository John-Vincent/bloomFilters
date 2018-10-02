package coms435.pa1.hash;

import ie.ucd.murmur.MurmurHash;
import java.util.BitSet;

public class MurHash extends BFHash
{
    int seed;
    //used to set an upper bound on the returned hash value
    //since the bitset and will just ignore bits passed the size of the bitset made in the filter
    int p;

    @Override
    public void generateFunction(int t, int N, double k)
    {
        this.seed = Math.abs((int) (Math.random() * Integer.MAX_VALUE));
        this.p = t*N;
    }

    @Override
    public int getHash()
    {
        return Math.abs((int)MurmurHash.hash64(this.s.getBytes(), this.s.length(), this.seed)) % p;
    }

}