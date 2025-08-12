package com.negretenico.friendly.vm;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.exception.StackSizeException;
import com.negretenico.friendly.models.EVMCode;
import com.negretenico.friendly.models.EVMStack;
import com.negretenico.friendly.models.OPCode;
import com.negretenico.friendly.service.GasChargeService;
import com.negretenico.friendly.service.PairOperationService;
import com.negretenico.friendly.service.SingleOperationService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.negretenico.friendly.config.OpCodeConfig.*;
import static com.negretenico.friendly.service.MaskingService.mask;
public class EVM {
    private final EVMStack stack;
    private final Map<String, BigInteger> storage;
    private final BigInteger[] memory ;
    private   BigInteger gas = new BigInteger("1000000");
    private final GasChargeService gasChargeService;
    private final EVMCode[] codes;
    private  int PC = 0;
    private final PairOperationService pairOperationService;
    private final SingleOperationService singleOperationService;
    public EVM(EVMStack stack, Map<String, BigInteger> storage, GasChargeService gasChargeService, EVMCode[] codes, PairOperationService pairOperationService, SingleOperationService singleOperationService) {
        this.stack = stack;
        this.storage = storage;
        this.gasChargeService = gasChargeService;
        this.codes = codes;
        this.pairOperationService = pairOperationService;
        this.singleOperationService = singleOperationService;
        this.memory = new BigInteger[8];
    }

    private Result<List<BigInteger>> getNums(){
        Result<BigInteger> x = stack.pop();
        Result<BigInteger> y = stack.pop();
        if(x.isFailure() || y.isFailure()){
            return Result.failure("We messed up");
        }
        return  Result.success(List.of(x.data(),y.data()));
    }
    private void pushGuard(BigInteger masked) throws StackSizeException{
        Result<BigInteger> foo = stack.push(masked);
        if(foo.isFailure()){
            throw new StackSizeException("Stack size overflow");
        }
        System.out.println("Successfully pushed masked num onto stack");
    }
    public void run(){
        while(PC < codes.length){
            EVMCode currentCode = codes[PC];
            PC +=1;
            gas= gasChargeService.charge(currentCode,gas);
            switch (currentCode.opCode()){
                case OPCode code when miscOpCodes.contains(code) ->{
                    break;
                }
                case  OPCode code when arithmeticCodes.contains(code)->{
                    break;
                }
                case OPCode code when booleanArithmeticCodes.contains(code)->{
                    break;
                }
                case OPCode code when memOpCodes.contains(code)->{
                    break;
                }
                case OPCode code when storeOpCodes.contains(code)->{
                    break;
                }
                case OPCode code when jumpOpCodes.contains(code)->{
                    break;
                }
                case OPCode code when pushOpCodes.contains(code)->{}
                case OPCode code when swapOpCodes.contains(code) ->{}
                case OPCode code when dupOpCOdes.contains(code)->{}
                default ->
                        throw new IllegalStateException("Unexpected value: " + currentCode.opCode());
            }
        }
    }
}
