package coms435.pa1.hash;

public class HashTest
{

    public static int FNVTest(int t, int N)
    {
        FNVHash h = new FNVHash();
        h.generateFunction(t, N, 1);
        h.setString("asdkfjhq");
        return h.getHash();
    }

    public static int MurmurTest(int t, int N)
    {
        MurHash h = new MurHash();
        h.generateFunction(t, N, 1);
        h.setString("fwsdvwqs");
        return h.getHash();
    }

    public static int RanHash(int t, int N)
    {
        RanHash h = new RanHash();
        h.generateFunction(t, N, 1);
        h.setString("aoektbci");
        return h.getHash();
    }

    public static void main(String[] args)
    {
        int t, N;
        int b;
        boolean control = true;
        //runs until one of the hashing algorithms fails to meet the requirements or program is manually terminated
        while(control)
        {
            t = (int)(Math.random()*9900d);
            t += 100;
            N = 64;
            System.out.println("hashes bound by: " + t*N);
            b = RanHash(t, N);
            control = control && (b < t * N);
            control = control && b == 1;
            System.out.println("RanHash: " + b);
            b = FNVTest(t, N);
            control = control && (b < t * N);
            control = control && b == 1;
            System.out.println("FNVHash: " + b);
            b = MurmurTest(t, N);
            control = control && (b < t * N);
            control = control && b == 1;
            System.out.println("MurmurHash: " + b);
        }
    }
}