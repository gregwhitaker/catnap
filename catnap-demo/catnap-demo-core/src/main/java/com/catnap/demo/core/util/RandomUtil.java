package com.catnap.demo.core.util;

import java.util.Random;

/**
 * Created by Woody on 12/29/13.
 */
public class RandomUtil
{
    public static String getRandomValue(final Random random, final int lowerBound, final int upperBound, final int decimalPlaces)
    {
        if(lowerBound < 0 || upperBound <= lowerBound || decimalPlaces < 0)
        {
            throw new IllegalArgumentException("Invalid bounds");
        }

        final double dbl = ((random == null ? new Random() : random).nextDouble() * (upperBound - lowerBound)) + lowerBound;

        return String.format("%." + decimalPlaces + "f", dbl);
    }
}
