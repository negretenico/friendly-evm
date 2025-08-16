package com.negretenico.friendly.models;

import java.math.BigInteger;

public record EVMCode (OPCode opCode, String name, BigInteger minGas){
}
