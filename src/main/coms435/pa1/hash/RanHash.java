package coms435.pa1.hash;

import java.util.BitSet;

public class RanHash extends BFHash
{
    private long a;

    private long b;

    private long p;

    private int max;

    @Override
    public int getHash()
    {
        int x = Math.abs(this.s.hashCode());
        int i = (int)((a*x + b)%p);
        //this is to make sure that the bitset is at most the size of the BitSet in the filter
        return i % this.max;
    }

    @Override
    public void generateFunction(int t, int N, double k)
    {
        int primes = (int)(Math.random() * 1000);
        this.max = t*N;
        this.p = this.max;
        for(int i = 0; i < primes; i++)
        {
            this.p = nextPrime(this.p + 1);
        }
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

    @Override
    public String toString()
    {
        return "p: " + p + "\na: " + a + "\nb: " + b + "\ns Hash: " + Math.abs(s.hashCode());
    }
}