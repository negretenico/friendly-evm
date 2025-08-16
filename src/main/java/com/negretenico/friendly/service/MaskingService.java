package com.negretenico.friendly.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;

public class MaskingService {
    public static BigInteger mask(BigInteger toBeMasked){
        BigInteger MASK_256 = BigInteger.ONE.shiftLeft(256).subtract(BigInteger.ONE);
        return toBeMasked.and(MASK_256);
    }
}
