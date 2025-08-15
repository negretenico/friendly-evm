package com.negretenico.friendly.config;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.models.EVMContext;

import java.math.BigInteger;
import java.util.Arrays;

import static com.negretenico.friendly.service.MaskingService.mask;
import static com.negretenico.friendly.service.SignService.toUnsigned;

/*
MLOAD
- Pop the address from the stack.
- Read 32 bytes starting at this address in memory.
- Convert those 32 bytes into a 256-bit unsigned BigInteger.
- Push this BigInteger onto the stack.

MSTORE
- Pop the address from the stack.
- Pop the value from the stack.
- Convert the value to a 32-byte unsigned byte array.
- Store these 32 bytes in memory starting at the given address.

MSTORE8
- Pop the address from the stack.
- Pop the value from the stack.
- Extract the least significant byte (8 bits) from the value.
- Store this single byte at the given address in memory.
 */
public class MemOpCodeHandler {
    private static BigInteger byteArrayToBigInteger(byte[] b){
        return toUnsigned( new BigInteger(1,b));
    }
    public static Result<BigInteger> memLoad(EVMContext context) {
        Result<BigInteger> addressRes = context.stack().pop();
        if (addressRes.isFailure()) {
            return Result.failure("MLOAD: stack underflow");
        }

        int address = addressRes.data().intValue();
        context.ensureMemoryCapacity(address + 32); // load 32 bytes
        byte [] toBeConvereted= Arrays.copyOfRange(context.memory(),address,
                address+33);
        return Result.success(context.pushGuard(mask(byteArrayToBigInteger(toBeConvereted))));
    }

    public static Result<BigInteger> memStore(EVMContext context) {
        Result<BigInteger> valueRes = context.stack().pop();
        Result<BigInteger> addressRes = context.stack().pop();

        if (addressRes.isFailure() || valueRes.isFailure()) {
            return Result.failure("MSTORE: stack underflow");
        }
        int address = addressRes.data().intValue();
        context.ensureMemoryCapacity(address + 32);
        byte [] word = toUnsigned(valueRes.data()).toByteArray();
        context.storeBigIntegerAsByteArray(address,word );
        return Result.success(valueRes.data());
    }

    public static Result<BigInteger> memStore8(EVMContext context) {
        Result<BigInteger> valueRes = context.stack().pop();
        Result<BigInteger> addressRes = context.stack().pop();

        if (addressRes.isFailure() || valueRes.isFailure()) {
            return Result.failure("MSTORE8: stack underflow");
        }

        int address = addressRes.data().intValue();
        context.ensureMemoryCapacity(address + 1);

        // Only lowest byte
        BigInteger byteValue = valueRes.data().and(BigInteger.valueOf(0xFF));
        context.storeLSB(address,byteValue.byteValue() );
        return Result.success(byteValue);
    }
}
