package com.negretenico.friendly.config;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.models.EVMContext;

import java.math.BigInteger;

import static com.negretenico.friendly.service.MaskingService.mask;

public class PcOpCodeHandler {
    public static Result<BigInteger> pc(EVMContext context){
        int PC =context.PC();
        BigInteger x= BigInteger.valueOf(PC);
        return  Result.success(context.pushGuard(mask(x)));
    }
}
