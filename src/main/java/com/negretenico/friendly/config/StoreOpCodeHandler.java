package com.negretenico.friendly.config;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.models.EVMContext;

import java.math.BigInteger;

import static com.negretenico.friendly.service.MaskingService.mask;

public class StoreOpCodeHandler {
    public static Result<BigInteger> sstore(EVMContext evmContext){
        Result<BigInteger> valueR = evmContext.stack().pop();
        Result<BigInteger> keyR = evmContext.stack().pop();
        if(keyR.isFailure() || valueR.isFailure()){
            return Result.failure("SSTORE: Underflow");
        }
        BigInteger store = evmContext.storage().put(mask(keyR.data()).toString(16),
                mask(valueR.data()));
        return  Result.success(store);
    }
    public  static Result<BigInteger> sload(EVMContext evmContext){
        Result<BigInteger> keyR = evmContext.stack().pop();
        if(keyR.isFailure()){
            return  Result.failure("SLOAD: Underflow");
        }
        BigInteger storedData =
                evmContext.storage().getOrDefault(keyR.data().toString(16)
                ,BigInteger.ZERO);
        return  Result.success(evmContext.pushGuard(mask(storedData)));
    }
}
