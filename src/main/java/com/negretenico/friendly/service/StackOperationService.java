package com.negretenico.friendly.service;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.models.EVMStack;
import com.negretenico.friendly.models.TriFunction;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.function.BiFunction;

@Service
public class StackOperationService {
    public Result<BigInteger> handleOne(EVMStack stack,
                                        BiFunction<BigInteger, BigInteger, BigInteger> func) {
        Result<BigInteger> x = stack.pop();
        if (x.isFailure()) {
            return Result.failure("Stack underflow on unary op");
        }
        return Result.success(func.apply(x.data(), BigInteger.ZERO));
    }

    public Result<BigInteger> handle(EVMStack stack,
                                     BiFunction<BigInteger, BigInteger,BigInteger> supplier){
        Result<BigInteger> first = stack.pop();
        Result<BigInteger> second = stack.pop();
        if(first.isFailure() || second.isFailure()){
            return Result.failure("Stack underflow on 2-arg op");
        }
        return Result.success(supplier.apply(first.data(),second.data()));
    }
    public Result<BigInteger> handleThree(EVMStack stack,
                                          TriFunction<BigInteger, BigInteger, BigInteger, BigInteger> func) {
        Result<BigInteger> first = stack.pop();
        Result<BigInteger> second = stack.pop();
        Result<BigInteger> third = stack.pop();
        if (first.isFailure() || second.isFailure() || third.isFailure()) {
            return Result.failure("Stack underflow on 3-arg op");
        }
        return Result.success(func.apply(first.data(), second.data(), third.data()));
    }

}
