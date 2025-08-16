package com.negretenico.friendly.config;

import com.negretenico.friendly.models.EVMCode;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.EVMStack;
import com.negretenico.friendly.models.OPCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Stack;

@Configuration
public class EVMContextConfig {
    @Bean
    public EVMContext evmContext(){
        return  new EVMContext(new EVMStack(new Stack<>()),new HashMap<>(),
                new byte[0],0);
    }

    @Bean
    public EVMCode[] codes() {
        // Example program:
        // PUSH1 0x02
        // PUSH1 0x03
        // ADD
        // STOP
        //
        // We store the PUSH1 immediate in EVMCode.name() as hex *without* "0x"
        // e.g. "02", "03". minGas is just a placeholder here.
        return new EVMCode[] {
                new EVMCode(OPCode.PUSH1, "02", BigInteger.ONE),
                new EVMCode(OPCode.PUSH1, "03", BigInteger.ONE),
                new EVMCode(OPCode.ADD,   "ADD", BigInteger.ONE),
                new EVMCode(OPCode.STOP,  "STOP", BigInteger.ZERO)
        };
    }
}
