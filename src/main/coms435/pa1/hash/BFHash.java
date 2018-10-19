package coms435.pa1.hash;

import java.util.BitSet;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class BFHash implements Runnable
{

    private LinkedBlockingQueue<int[]> queue;

    protected volatile String s;

    private volatile int index = 0;

    public void run()
    {
        this.queue.add(new int[]{this.getHash(), this.index});
    }

    public void setQueue(LinkedBlockingQueue<int[]> q)
    {
        this.queue = q;
    }

    public void setIndex(int i)
    {
        this.index = i;
    }

    public void setString(String s)
    {
        this.s = s;
    }

    public void generateFunction(int t, int N, double k)
    {

    }

    public int getHash()
    {
        return 0;
    }

    public int getHash(String s)
    {
        int ans;
        String temp = this.s;
        this.s = s;
        ans = this.getHash();
        this.s = temp;
        return ans;
    }
}