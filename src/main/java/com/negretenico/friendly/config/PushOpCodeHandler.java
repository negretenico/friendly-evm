package com.negretenico.friendly.config;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.OPCode;

import java.math.BigInteger;

import static com.negretenico.friendly.service.MaskingService.mask;

public class PushOpCodeHandler {
    public static Result<BigInteger> push1(EVMContext context){
        return Result.success(context.pushGuard(mask(BigInteger.valueOf(OPCode.PUSH1.hex))));
    }
}
