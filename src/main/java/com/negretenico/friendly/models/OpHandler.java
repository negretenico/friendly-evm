package com.negretenico.friendly.models;

import com.common.functionico.evaluation.Result;

import java.math.BigInteger;
import java.util.function.BiFunction;

public interface OpHandler extends BiFunction<EVMContext , OPCode,
        Result<BigInteger>> {
}
