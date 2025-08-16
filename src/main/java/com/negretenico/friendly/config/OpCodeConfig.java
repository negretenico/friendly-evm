package com.negretenico.friendly.config;

import com.negretenico.friendly.models.OPCode;

import java.util.EnumSet;

public class OpCodeConfig {
    public static final EnumSet<OPCode> arithmeticCodes =
            EnumSet.of(
                    OPCode.ADD,
                    OPCode.SUB,
                    OPCode.MUL,
                    OPCode.DIV,
                    OPCode.MOD,
                    OPCode.SDIV,
                    OPCode.SMOD);
    public static EnumSet<OPCode> booleanArithmeticCodes = EnumSet.of(
            OPCode.GT,
            OPCode.NOT,
            OPCode.EQ,
            OPCode.LT,
            OPCode.AND,
            OPCode.OR,
            OPCode.ISZERO,
            OPCode.XOR
    );
    public static final EnumSet<OPCode> memOpCodes = EnumSet.of(
            OPCode.MLOAD,
            OPCode.MSTORE,
            OPCode.MSTORE8);
    public static final EnumSet<OPCode> storeOpCodes =
            EnumSet.of(OPCode.SSTORE,
                    OPCode.SLOAD);
    public static final EnumSet<OPCode> jumpOpCodes = EnumSet.of(
            OPCode.JUMP,
            OPCode.JUMPI,
            OPCode.JUMPDEST
    );
    public static final EnumSet<OPCode> miscOpCodes = EnumSet.of(OPCode.PC,
            OPCode.STOP);
    public static final EnumSet<OPCode> pushOpCodes = EnumSet.of(OPCode.PUSH1);
    public static final EnumSet<OPCode> swapOpCodes = EnumSet.of(OPCode.SWAP1);
    public static final EnumSet<OPCode> dupOpCOdes = EnumSet.of(OPCode.DUP1);
}
