package com.negretenico.friendly.service.vm;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.config.StoreOpCodeHandler;
import com.negretenico.friendly.config.NoOpHandler;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.OPCode;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
public class StorageService {

    // Map of opcodes to their storage handler functions
    private final Map<OPCode, Function<EVMContext, Result<?>>> handlers = Map.ofEntries(
            Map.entry(OPCode.SSTORE, StoreOpCodeHandler::sstore),
            Map.entry(OPCode.SLOAD,  StoreOpCodeHandler::sload)
    );

    public void handle(EVMContext context, OPCode opCode) {
        Function<EVMContext, Result<?>> handler = handlers
                .getOrDefault(opCode, ctx -> NoOpHandler.noOp(null, null));

        Result<?> res = handler.apply(context);
        if(res.isSuccess()){
            System.out.printf("Successfully performed %s",opCode);
        }
        System.out.printf("Failed to perform operation %s",opCode);
    }
}
