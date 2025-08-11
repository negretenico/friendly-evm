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
                case STOP -> {
                    return;
                }
                case ADD -> {
                    Result<BigInteger> res =
                            pairOperationService.handle(stack,
                                    num-> num.stream().reduce(BigInteger::add).get());
                    if(res.isFailure()) continue;
                    pushGuard(mask(res.data()));
                }
                case SUB -> {
                    Result<BigInteger> res =
                            pairOperationService.handle(stack,
                                    num-> num.stream().reduce(BigInteger::subtract).get());
                    if(res.isFailure()) continue;
                    pushGuard(mask(res.data()));
                }
                case MUL -> {
                    Result<BigInteger> res = pairOperationService.handle(stack,
                            (nums)->  nums.stream().reduce(BigInteger::multiply
                    ).get());
                    if(res.isFailure()) continue;
                    pushGuard(mask(res.data()));

                }
                case DIV -> {
                    Result<BigInteger> res =
                            pairOperationService.handle(stack,
                                    num-> num.stream().reduce(BigInteger::divide).get());
                    if(res.isFailure()) continue;
                    pushGuard(mask(res.data()));

                }
                case MOD -> {
                    Result<BigInteger> res =
                            pairOperationService.handle(stack,
                                    num-> num.stream().reduce(BigInteger::mod).get());
                    if(res.isFailure()) continue;
                    pushGuard(mask(res.data()));
                }
                case SDIV -> {
                }
                case SMOD -> {
                }
                case ADDMOD -> {
                }
                case MULMOD -> {
                }
                case PUSH1 -> {
                    if (PC >= codes.length) continue;
                    EVMCode nextCode = codes[PC];
                    PC += 1;
                    BigInteger value = new BigInteger(nextCode.name(),16);
                    pushGuard(mask(value));
                }
                case DUP1 -> {
                }
                case SWAP1 -> {
                }
                case MLOAD -> {
                }
                case MSTORE -> {
                }
                case MSTORE8 -> {
                }
                case SLOAD -> {
                }
                case SSTORE -> {
                }
                case JUMP -> {
                }
                case JUMPI -> {
                }
                case PC -> {
                }
                case JUMPDEST -> {
                }
                case EQ -> {
                    Result<BigInteger> couple =
                            pairOperationService.handle(stack, l ->{
                                BigInteger first= l.getFirst();
                                BigInteger second = l.getLast();
                                int op = first.compareTo(second);
                                return op == 0 ? BigInteger.ONE:
                                        BigInteger.ZERO;
                            });
                    if(couple.isFailure()){
                        continue;
                    }
                    pushGuard(mask(couple.data()));
                }
                case LT -> {
                    Result<BigInteger> couple =
                            pairOperationService.handle(stack, l ->{
                                BigInteger first= l.getFirst();
                                BigInteger second = l.getLast();
                                int op = first.compareTo(second);
                                return op <  0 ? BigInteger.ONE:
                                        BigInteger.ZERO;
                            });
                    if(couple.isFailure()){
                        continue;
                    }
                    pushGuard(mask(couple.data()));
                }
                case GT -> {
                    Result<BigInteger> couple =
                            pairOperationService.handle(stack, l ->{
                                BigInteger first= l.getFirst();
                                BigInteger second = l.getLast();
                                int op = first.compareTo(second);
                                return op > 0 ? BigInteger.ONE: BigInteger.ZERO;
                            });
                    if(couple.isFailure()){
                        continue;
                    }
                    pushGuard(mask(couple.data()));

                }
                case ISZERO -> {
                    Result<BigInteger> single =
                            singleOperationService.handle(stack,bigInt->{
                                int op = bigInt.compareTo(BigInteger.ZERO);
                                return op==0? BigInteger.ONE: BigInteger.ZERO;
                            });
                    if(single.isFailure()){
                        continue;
                    }
                    pushGuard(mask(single.data()));
                }
                case AND -> {
                    Result<BigInteger> res =
                            pairOperationService.handle(stack,
                                    num-> num.stream().reduce(BigInteger::and).get());
                    if(res.isFailure()) continue;
                    pushGuard(mask(res.data()));
                }
                case OR -> {
                    Result<BigInteger> res =
                            pairOperationService.handle(stack,
                                    num-> num.stream().reduce(BigInteger::or).get());
                    if(res.isFailure()) continue;
                    pushGuard(mask(res.data()));
                }
                case XOR -> {
                    Result<BigInteger> res =
                            pairOperationService.handle(stack,
                                    num-> num.stream().reduce(BigInteger::xor).get());
                    if(res.isFailure()) continue;
                    pushGuard(mask(res.data()));
                }
                case NOT -> {
                   Result<BigInteger> single =
                           singleOperationService.handle(stack,
                                   BigInteger::not);
                   if(single.isFailure()){
                       continue;
                   }
                    pushGuard(mask(single.data()));
                }
                default -> throw new RuntimeException();
            }
        }
    }
}
