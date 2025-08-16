package com.negretenico.friendly.config;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.models.EVMContext;

import java.math.BigInteger;

public class SwapOpCodeHandler {
    public static Result<BigInteger> swap1(EVMContext evmContext){
        Result<BigInteger> topR = evmContext.stack().peek();
        Result<BigInteger> secondR = evmContext.stack().peek(1);
        if(topR.isFailure() || secondR.isFailure()){
            return Result.failure("SWAP1: Underflow");
        }
        evmContext.pushGuard(topR.data());
        return Result.success(evmContext.pushGuard(secondR.data()));
    }
}
