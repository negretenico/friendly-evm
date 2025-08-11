package com.negretenico.friendly.service;

import com.negretenico.friendly.exception.GasChargeException;
import com.negretenico.friendly.models.EVMCode;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class GasChargeService {
    public BigInteger charge(EVMCode opcode,BigInteger gas){
        if(opcode.minGas().compareTo(gas)>=0){
            throw new GasChargeException(String.format("We could not charge " +
                    "%s, because it cost %s and we have %s left",
                    opcode.name(),opcode.minGas(),gas));
        }
        return gas.subtract(opcode.minGas());
    }
}
