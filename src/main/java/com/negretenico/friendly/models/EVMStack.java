package com.negretenico.friendly.models;

import com.common.functionico.evaluation.Result;

import java.math.BigInteger;
import java.util.Stack;

public class EVMStack {
    private final int LIMIT = 1024;
    private  final Stack<BigInteger> stack;

    public EVMStack(Stack<BigInteger> stack) {
        this.stack = stack;
    }
    public Result<BigInteger> pop(){
        if(stack.isEmpty()){
            return  Result.failure("Stack is mt, nothing to pop off");
        }
        return Result.success(stack.pop());
    }
    public Result<BigInteger> peek(){
        if(stack.isEmpty()){
            return Result.failure("Stack: Underflow");
        }
        return Result.success(stack.peek());
    }
    public Result<BigInteger> peek(int i){
        if (stack.isEmpty()){
            return Result.failure("Stack:Peek :Underflow");
        }
        return Result.success(stack.elementAt(i));
    }
    public Result<BigInteger> push(BigInteger item){
        if(stack.size()>=LIMIT){
            return Result.failure("We cannot add any items we've reached the " +
                    "limit");
        }
        return Result.success(stack.push(item));
    }
}
