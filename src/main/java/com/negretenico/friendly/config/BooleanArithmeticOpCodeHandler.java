package com.negretenico.friendly.config;

import java.math.BigInteger;

import static com.negretenico.friendly.service.MaskingService.mask;
import static com.negretenico.friendly.service.SignService.toUnsigned;

public class BooleanArithmeticOpCodeHandler {
    public static BigInteger and(BigInteger first, BigInteger second){
        return mask(first.and(second));
    }
    public static BigInteger or(BigInteger first, BigInteger second){
        return  mask(first.or(second));
    }
    public static BigInteger xor(BigInteger first, BigInteger second){
        return mask(first.xor(second));
    }
    public static BigInteger equals(BigInteger first, BigInteger second){
        return mask(first.compareTo(second)==0? BigInteger.ONE: BigInteger.ZERO);
    }
    public static BigInteger lessThan(BigInteger first, BigInteger second){
        return mask(toUnsigned(first).compareTo(toUnsigned(second)) < 0 ?
                BigInteger.ONE:
                BigInteger.ZERO);
    }
    public static BigInteger greaterThan(BigInteger first, BigInteger second){
        return mask(toUnsigned(first).compareTo(toUnsigned(second))>0?
                BigInteger.ONE:
                BigInteger.ZERO);
    }
    public static BigInteger isZero(BigInteger first, BigInteger second){
        return mask(first.compareTo(BigInteger.ZERO)==0? BigInteger.ONE:
                BigInteger.ZERO);
    }
    public static BigInteger not(BigInteger first, BigInteger second){
        return mask(first.not());
    }
}
