package com.negretenico.friendly.models;

public enum OPCode {
    STOP(0x000),
    ADD(0x001),
    SUB(0x002),
    MUL(0x003),
    DIV(0x004),
    MOD(0x005),
    SDIV(0x006),
    SMOD(0x007),
    ADDMOD(0x008),
    MULMOD(0x009),

    PUSH1(0x00A), // go to 32
    DUP1(0x100), // go to 16
    SWAP1(0x020), // go to 16

    MLOAD(0x030),
    MSTORE(0x031),
    MSTORE8(0x032),
    SLOAD(0x033),
    SSTORE(0x034),
    JUMP(0x035),
    JUMPI(0x036),
    PC(0x037),
    JUMPDEST(0x038),
    EQ(0x039),
    LT(0x03A),
    GT(0x03B),
    ISZERO(0x03C),
    AND(0x03D),
    OR(0x03E),
    XOR(0x03F),
    NOT(0x040);
    public final int hex;
    OPCode(int hex) {
        this.hex=hex;
    }
}