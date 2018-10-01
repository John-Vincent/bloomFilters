package coms435.pa1.filter;

import java.lang.reflect.Constructor;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Executors;
import coms435.pa1.hash.BFHash;

public abstract class BloomFilterAbstract implements BloomFilter
{
    private static final String INT_EXP_ERR = "getting BitSet from hashing thread was interrupted" +
    " if these interruptions keep happening the program will be stuck in an infinite loop";

    protected static final double LN_2 = Math.log(2);

    private BitSet table;

    private Collection<BFHash> hashes;

    private  static ExecutorService threads = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private int elements;

    private int setSize;

    private int bitsPerElement;

    public BloomFilterAbstract(int setSize, int bitsPerElement)
    {
        this.table = new BitSet(setSize * bitsPerElement);
        this.setSize = setSize;
        this.bitsPerElement = bitsPerElement;
        this.hashes = new ArrayList<BFHash>();
        //set all arrays indecies to false
        this.table.clear();
    }

    protected void setHashFunction(Constructor<? extends BFHash> c)
    {
        double size = LN_2 * bitsPerElement;
        BFHash h;
        // generates ceil(size) hash functions
        for (double i = 0; i < size; i++) {
            try {
                h = c.newInstance();
                h.generateFunction(bitsPerElement, setSize, size);
                hashes.add(h);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("\n exception creating hash function instance");

            }
        }
    }

    /**
     * Adds the String s to the filter strings should be case-insensitive
     *
     * @param s
     */
    public void add(String s)
    {
        BitSet b;
        LinkedBlockingQueue<BitSet> queue = new LinkedBlockingQueue<BitSet>();
        this.startHash(s, queue);
        for(int i = 0; i < hashes.size(); i++)
        {
            try
            {
                b = queue.take();
                this.table.or(b);
            }
            catch(InterruptedException e)
            {
                System.out.println(INT_EXP_ERR);
                i--;
            }
        }
        this.elements++;
    }

    /**
     * Returns true if s appears in the filter otherwise false. also
     * case-insensitive
     *
     * @param s
     * @return boolean
     */
    public boolean appears(String s)
    {
        boolean ans = true;
        BFHash h;
        Iterator<BFHash> it = hashes.iterator();
        //checks if the index returned from each hash function is set in the table
        //if the index is not set the loop exits and returns false
        while(it.hasNext() && ans)
        {
            h = it.next();
            h.setString(s);
            ans = h.getHash().intersects(this.table);
        }
        return ans;
    }

    /**
     * starts concurrently hashing the string with all
     * hashing functinos in this.hashes
     * @param s
     */
    private void startHash(String s, LinkedBlockingQueue<BitSet> queue)
    {
        BFHash h;
        Iterator<BFHash> it = hashes.iterator();
        while (it.hasNext()) {
            h = it.next();
            h.setString(s);
            h.setQueue(queue);
            threads.execute(h);
        }
    }

    /**
     * returns the size of the filter/table
     *
     * @return int
     */
    public int filterSize()
    {
        return this.table.length();
    }

    /**
     * returns the number of elements added to the filter
     *
     * @return int
     */
    public int dataSize()
    {
        return this.elements;
    }

    /**
     * returns the number of hash functions used. this should be the optimal choice
     * (ln(2) * filterSize / setSize)
     *
     * @return int
     */
    public int numHashes()
    {
        return this.hashes.size();
    }

    public static  void shutdown()
    {
        threads.shutdown();
    }
}