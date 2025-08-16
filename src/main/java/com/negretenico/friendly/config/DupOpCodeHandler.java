package com.negretenico.friendly.config;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.models.EVMContext;

import java.math.BigInteger;

public class DupOpCodeHandler {
    public static Result<BigInteger> dup1(EVMContext evmContext){
        Result<BigInteger> topR = evmContext.stack().peek();
        if(topR.isFailure()){
            return Result.failure("DUP1: Underflow");
        }
        evmContext.pushGuard(topR.data());
        return Result.success(evmContext.pushGuard(topR.data()));
    }
}
