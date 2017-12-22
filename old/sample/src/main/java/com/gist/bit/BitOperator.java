package com.gist.bit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * test bit operator
 * 
 * @author zaccoding
 * @Date 2017. 8. 31.
 */
public class BitOperator {
    public static final int TYPE1 = 1;
    public static final int TYPE2 = 2;
    public static final int TYPE3 = 4;
    public static final int TYPE4 = 8;
    public static final int TYPE5 = 16;

    public int sample1, sample2;

    @Test
    public void bitOperator() {
        // given
        sample1 = TYPE1 + TYPE3 + TYPE5;
        sample2 = TYPE2 + TYPE4 + TYPE5;

        assertTrue(isMatched(sample1, TYPE1));
        assertTrue(isMatched(sample1, TYPE3));
        assertTrue(isMatched(sample1, TYPE5));
        assertFalse(isMatched(sample1, TYPE2));
        assertFalse(isMatched(sample1, TYPE4));

        assertTrue(isMatched(sample2, TYPE2));
        assertTrue(isMatched(sample2, TYPE4));
        assertTrue(isMatched(sample2, TYPE5));

        assertFalse(isMatched(sample2, TYPE1));
        assertFalse(isMatched(sample2, TYPE3));
    }


    private boolean isMatched(int target, int type) {
        return (target & type) != 0;

    }



}
