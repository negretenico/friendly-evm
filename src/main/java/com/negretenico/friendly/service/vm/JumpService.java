package com.negretenico.friendly.service.vm;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.config.JumpOpCodeHandler;
import com.negretenico.friendly.config.NoOpHandler;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.OPCode;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
public class JumpService {

    private final Map<OPCode, Function<EVMContext, Result<EVMContext>>> handlers =
            Map.ofEntries(
                    Map.entry(OPCode.JUMP,  JumpOpCodeHandler::jump),
                    Map.entry(OPCode.JUMPI, JumpOpCodeHandler::jumpI)
            );

    public Result<EVMContext> handle(EVMContext context, OPCode opCode) {
        return handlers
                .getOrDefault(opCode, ctx -> Result.failure("Invalid jump opcode"))
                .apply(context);
    }
}
