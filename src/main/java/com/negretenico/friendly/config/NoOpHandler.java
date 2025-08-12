package com.negretenico.friendly.config;

import com.common.functionico.evaluation.Result;

import java.math.BigInteger;

public class NoOpHandler {
    public static Result<BigInteger> noOp(BigInteger first, BigInteger second){
        return Result.failure("Unknown or unimplemented opcode");
    }
}
