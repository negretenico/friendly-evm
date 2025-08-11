package com.negretenico.friendly.service;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.models.EVMStack;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class SingleOperationService {
    public Result<BigInteger> handle(EVMStack stack,
                                     Function<BigInteger,BigInteger> function){
        Result<BigInteger> single = stack.pop();
        if(single.isFailure()){
            return Result.failure("This is a problem");
        }
        return Result.success(function.apply(single.data()));
    }
}
