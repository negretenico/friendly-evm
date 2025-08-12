package com.negretenico.friendly.service.vm;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.config.ArithmeticOpCodeHandler;
import com.negretenico.friendly.config.NoOpHandler;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.OPCode;
import com.negretenico.friendly.service.StackOperationService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;
import java.util.function.BiFunction;

@Service
public class ArithmeticService {

    private final StackOperationService pairOperationService;

    // Each handler pops operands from the stack and applies the arithmetic function
    private final Map<OPCode, BiFunction<EVMContext, StackOperationService, Result<BigInteger>>> handlers =
            Map.ofEntries(
                    Map.entry(OPCode.ADD, (ctx, pos) -> pos.handle(ctx.stack(), ArithmeticOpCodeHandler::add)),
                    Map.entry(OPCode.SUB, (ctx, pos) -> pos.handle(ctx.stack(), ArithmeticOpCodeHandler::sub)),
                    Map.entry(OPCode.MUL, (ctx, pos) -> pos.handle(ctx.stack(), ArithmeticOpCodeHandler::mult)),
                    Map.entry(OPCode.DIV, (ctx, pos) -> pos.handle(ctx.stack(), ArithmeticOpCodeHandler::div)),
                    Map.entry(OPCode.MOD, (ctx, pos) -> pos.handle(ctx.stack(), ArithmeticOpCodeHandler::mod)),
                    Map.entry(OPCode.SDIV, (ctx, pos) -> pos.handle(ctx.stack(), ArithmeticOpCodeHandler::signDiv)),
                    Map.entry(OPCode.SMOD, (ctx, pos) -> pos.handle(ctx.stack(), ArithmeticOpCodeHandler::signMod)),
                    Map.entry(OPCode.ADDMOD, (ctx, pos) -> pos.handleThree(ctx.stack(), ArithmeticOpCodeHandler::addMod)),
                    Map.entry(OPCode.MULMOD, (ctx, pos) -> pos.handleThree(ctx.stack(), ArithmeticOpCodeHandler::multMod))
            );

    public ArithmeticService(StackOperationService pairOperationService) {
        this.pairOperationService = pairOperationService;
    }

    public void handle(EVMContext context, OPCode opCode) {
        Result<BigInteger> res = handlers
                .getOrDefault(opCode, (ctx, pos) -> NoOpHandler.noOp(null, null))
                .apply(context, pairOperationService);

        if (res.isFailure()) {
            return;
        }
        context.pushGuard(res.data());
    }
}
