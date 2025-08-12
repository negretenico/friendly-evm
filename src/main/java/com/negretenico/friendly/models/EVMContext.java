package com.negretenico.friendly.models;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.exception.StackSizeException;

import java.math.BigInteger;
import java.util.Map;
public record EVMContext(EVMStack stack, Map<String, BigInteger> storage,
                         BigInteger[] memory, int PC) {
    public EVMContext ensureMemoryCapacity(int idx) {
        if (idx < 0) throw new RuntimeException("negative memory index");
        if (idx < memory.length) {return this;}
        int newSize = Math.max(memory.length * 2, idx + 1);
        BigInteger[] newMem = new BigInteger[newSize];
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
    }}
