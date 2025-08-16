package com.negretenico.friendly.service;

import java.math.BigInteger;

public class SignService {
    public static BigInteger  toSigned(BigInteger u) {
        if (u.testBit(255)) return u.subtract(BigInteger.ONE.shiftLeft(256));
        return u;
    }
    public static BigInteger toUnsigned(BigInteger s) {
        BigInteger MASK_256 = BigInteger.ONE.shiftLeft(256).subtract(BigInteger.ONE);
        return s.and(MASK_256);
    }
}
