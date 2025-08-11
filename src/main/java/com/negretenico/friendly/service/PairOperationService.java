package com.negretenico.friendly.service;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.models.EVMStack;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.negretenico.friendly.service.MaskingService.mask;

@Service
public class PairOperationService {
    public Result<BigInteger> handle(EVMStack stack,
                                     Function<List<BigInteger>,BigInteger> supplier){
        Result<BigInteger> x = stack.pop();
        Result<BigInteger> y = stack.pop();
        if(x.isFailure() || y.isFailure()){
            return Result.failure("We messed up");
        }
        return Result.success(supplier.apply(List.of(y.data(),x.data())));
    }
}
