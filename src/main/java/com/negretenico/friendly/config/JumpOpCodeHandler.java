package com.negretenico.friendly.config;

import ch.qos.logback.core.model.INamedModel;
import com.common.functionico.either.Either;
import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.models.EVMContext;

import java.math.BigInteger;

public class JumpOpCodeHandler {
    public static Result<EVMContext> jump(EVMContext context){
        Result<BigInteger> destinationR = context.stack().pop();
        if(destinationR.isFailure()){
            return Result.failure("Underflow: jump");
        }
        Either<Boolean, Integer> destination =
                context.jumpDestination(destinationR.data());
        if(!destination.getLeft()){
            return Result.failure("Invalid jump destination");
        }
        return Result.success(context.updatePC(destination.getRight()));
    }
    public static Result<EVMContext> jumpI(EVMContext context){
        Result<BigInteger> destinationR = context.stack().pop();
        Result<BigInteger> conditionR = context.stack().pop();

        if(destinationR.isFailure() || conditionR.isFailure()){
            return Result.failure("Underflow: jumpI");
        }
        if(conditionR.data().equals(BigInteger.ZERO)){
            return Result.success(context);
        }
        Either<Boolean, Integer> destination = context.jumpDestination(destinationR.data());
        if(!destination.getLeft()){
            return Result.failure("Invalid jump destination");
        }
        return Result.success(context.updatePC(destination.getRight()));
    }

}
