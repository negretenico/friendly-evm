package com.negretenico.friendly.service.vm;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.config.PcOpCodeHandler;
import com.negretenico.friendly.config.PushOpCodeHandler;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.OPCode;
import com.negretenico.friendly.vm.EVM;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;
import java.util.function.Function;

import static com.negretenico.friendly.config.PcOpCodeHandler.pc;

@Service
public class MiscService {

    public Result<BigInteger> handle(EVMContext context, OPCode opCode) {
        return pc(context);
    }
}