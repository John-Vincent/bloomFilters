package coms435.pa1.hash;

import java.util.BitSet;

public class RanHash extends BFHash
{
    private long a;

    private long b;

    private long p;

    @Override
    public BitSet getHash()
    {
        BitSet ans = new BitSet();
        //this may be redundant but I don't really care to look
        ans.clear();
        int x = this.s.hashCode();
        int i = (int)((a*x + b)%p);
        ans.set(i);
        return ans;
    }

    @Override
    public void generateFunction(int t, int N, double k)
    {
        this.p = nextPrime(t*N);
        this.a = (long)(Math.random() * Long.MAX_VALUE);
        this.a = this.a % this.p;
        this.b = (long) (Math.random() * Long.MAX_VALUE);
        this.b = this.b % this.p;
    }

    private long nextPrime(long p)
    {
        while(!isPrime(p))
        {
            p++;
        }
        return p;
    }

    private boolean isPrime(long p)
    {
        long n = (long)Math.ceil(Math.sqrt(p));
        if (p < 2) return false;
        if (p == 2) return true;
        if (p % 2 == 0) return false;
        for(long i = 3; i <= n; i++)
        {
            if(p%i == 0 ) return false;
        }
        return true;
    }
}