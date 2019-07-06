package org.gpginc.ntateam.apptest.runtime.util;

import java.util.Random;

public class IntInterval extends Number
{

    public static final IntInterval HUNDRED_BOUND = new IntInterval(0, 100);
    public static final IntInterval THOUSAND_BOUND = new IntInterval(0, 1000);
    public static final IntInterval NULL = new IntInterval(0,0);

    protected int start;
    protected int end;

    public IntInterval(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     *
     * @param num Some integer
     * @returns True if provider number is higher than Interval Start and smaller than Interval end, like math (0,4) = 1,2,3
     */
    public boolean in(int num)
    {
        return num > start && num < end;
    }

    /**
     *
     * @param num Some Integer
     * @return @returns True if provider number is higher than Interval End and smaller than Interval Start, like math (0,4) = -1, 4, 5
     */
    public boolean out(int num)
    {
        return num < start && num > end;
    }

    public int getRandomIn()
    {

        int out = 0;
        if(end != 0) {
            do out = new Random().nextInt(end); while (in(out));
        }
        return end!=0 ? out : 0;
    }

    @Override
    public int intValue() {
        return end - start;
    }

    @Override
    public long longValue() {
        return end - start;
    }

    @Override
    public float floatValue() {
        return end - start;
    }

    @Override
    public double doubleValue() {
        return end - start;
    }
}
