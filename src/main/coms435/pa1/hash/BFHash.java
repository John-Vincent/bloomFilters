package coms435.pa1.hash;

import java.util.BitSet;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class BFHash implements Runnable
{

    private LinkedBlockingQueue<BitSet> queue;

    protected String s;

    public void run()
    {
        this.queue.add(this.getHash());
    }

    public void setQueue(LinkedBlockingQueue<BitSet> q)
    {
        this.queue = q;
    }

    public void setString(String s)
    {
        this.s = s;
    }

    public void generateFunction(int t, int N, double k)
    {

    }

    public BitSet getHash()
    {
        return new BitSet();
    }
}