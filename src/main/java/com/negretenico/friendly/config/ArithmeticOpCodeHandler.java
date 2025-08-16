package com.negretenico.friendly.config;

import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.util.function.Function;

import static com.negretenico.friendly.service.MaskingService.mask;
import static com.negretenico.friendly.service.SignService.toSigned;


/**
 * All functions within this class assumes the responsibility of masking the
 * result for the EVM
 */
public class ArithmeticOpCodeHandler {
    
    public static BigInteger add(BigInteger first,
                                                      BigInteger second){
        return mask(first.add(second));
    }
    public static BigInteger sub(BigInteger first, BigInteger second){
        // think about how to handle underflow
        return mask(first.subtract(second));
    }
    public static BigInteger mult(BigInteger first, BigInteger second){
        return mask(first.multiply(second));
    }
    public static BigInteger div(BigInteger first, BigInteger second){
        return mask(first.divide(second));
    }
    public  static BigInteger mod(BigInteger first, BigInteger second){
        return mask(first.mod(second));
    }
    public  static BigInteger signDiv(BigInteger first, BigInteger second){
       //should this be toSigned(div(first,second))
        return mask(toSigned(first).divide(toSigned(second)));
    }
    public static BigInteger signMod(BigInteger first,BigInteger second){
        return mask(toSigned(mod(first,second)));
    }
    public static BigInteger addMod(BigInteger first, BigInteger second,
                                    BigInteger third){
        return mask(first.add(second).mod(third));
    }
    public static  BigInteger multMod(BigInteger first, BigInteger second,
                                      BigInteger third){
        return mask(first.multiply(second).mod(third));
    }
}
