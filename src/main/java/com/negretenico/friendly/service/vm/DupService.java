package com.negretenico.friendly.service.vm;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.config.DupOpCodeHandler;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.OPCode;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
public class DupService {
    private final Map<OPCode, Function<EVMContext, Result<?>>> handlers =
            Map.of(OPCode.DUP1, DupOpCodeHandler::dup1);

    public void handle(EVMContext context, OPCode opCode) {
        handlers.get(opCode).apply(context);
    }
}