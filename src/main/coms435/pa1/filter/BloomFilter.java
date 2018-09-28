package coms435.pa1.filter;

public interface BloomFilter
{

    /**
     * Adds the String s to the filter
     * strings should be case-insensitive
     * @param s
     */
    public void add(String s);

    /**
     * Returns true if s appears in the filter
     * otherwise false.
     * also case-insensitive
     * @param s
     * @return
     */
    public boolean appears(String s);

    /**
     * returns the size of the filter/table
     * @return
     */
    public int filterSize();

    /**
     * returns the number of elements added to the filter
     * @return
     */
    public int dataSize();

    /**
     * returns the number of hash functions used.
     * this should be the optimal choice (ln(2) * filterSize / setSize)
     * @return
     */
    public int numHashes();
}