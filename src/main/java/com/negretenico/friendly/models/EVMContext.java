package com.negretenico.friendly.models;

import com.common.functionico.either.Either;
import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.exception.StackSizeException;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.IntStream;

public record EVMContext(EVMStack stack, Map<String, BigInteger> storage,
                         byte[] memory, int PC) {
    public EVMContext ensureMemoryCapacity(int idx) {
        if (idx < 0) throw new RuntimeException("negative memory index");
        if (idx < memory.length) {return this;}
        int newSize = Math.max(memory.length * 2, idx + 1);
        byte[] newMem = new byte[newSize];
        System.arraycopy(memory, 0, newMem, 0, memory.length);
        return new EVMContext(stack,storage,newMem,PC);
    }
    public BigInteger pushGuard(BigInteger masked) throws StackSizeException {
        Result<BigInteger> foo = stack.push(masked);
        if(foo.isFailure()){
            throw new StackSizeException("Stack size overflow");
        }
        System.out.println("Successfully pushed masked num onto stack");
        return foo.data();
    }
    public void storeBigIntegerAsByteArray(int addressStart, byte [] bigInt){
        IntStream.range(0,bigInt.length).forEach(s->memory[addressStart+s]=
                bigInt[s]);
    }
    public void storeLSB(int addressStart, byte lsb){
        memory[addressStart] = lsb;
    }
    public EVMContext updatePC(int destination){
        return  new EVMContext(stack,storage,memory,destination);
    }
    public Either<Boolean, Integer> jumpDestination(BigInteger dest) {
        // For now, let's assume a simple check: valid if dest >= 0 and dest < memory.length
        if(dest.compareTo(BigInteger.ZERO) < 0 || dest.intValue() >= memory.length){
            return Either.left(false);
        }
        return Either.right(dest.intValue());
    }

}
