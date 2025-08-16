package com.negretenico.friendly.service.vm;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.config.MemOpCodeHandler;
import com.negretenico.friendly.config.NoOpHandler;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.OPCode;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;
import java.util.function.Function;

@Service
public class MemService {

    private final Map<OPCode, Function<EVMContext, Result<?>>> handlers =
            Map.ofEntries(
                    Map.entry(OPCode.MLOAD,   MemOpCodeHandler::memLoad),
                    Map.entry(OPCode.MSTORE,  MemOpCodeHandler::memStore),
                    Map.entry(OPCode.MSTORE8, MemOpCodeHandler::memStore8)
            );

    public void handle(EVMContext context, OPCode opCode) {
        Result<?> res = handlers
                .getOrDefault(opCode, ctx -> NoOpHandler.noOp(null,null))
                .apply(context);
        if (opCode != OPCode.MLOAD || res.isFailure()) {
            return;
        }
        context.pushGuard((BigInteger) res.data());
    }
}
