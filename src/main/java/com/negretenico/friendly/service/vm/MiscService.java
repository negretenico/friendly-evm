package com.negretenico.friendly.service.vm;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.config.PcOpCodeHandler;
import com.negretenico.friendly.config.PushOpCodeHandler;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.OPCode;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
public class MiscService {
    private final Map<OPCode, Function<EVMContext, Result<?>>> handlers =
            Map.ofEntries(
                    Map.entry(OPCode.PC, PcOpCodeHandler::pc),
                    Map.entry(OPCode.STOP, ctx -> Result.success(ctx)) // STOP just halts
            );

    public void handle(EVMContext context, OPCode opCode) {
        handlers.getOrDefault(opCode, ctx -> Result.failure("Unknown misc opcode"))
                .apply(context);
    }
}