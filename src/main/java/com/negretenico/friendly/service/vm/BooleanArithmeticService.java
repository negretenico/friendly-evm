package com.negretenico.friendly.service.vm;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.config.BooleanArithmeticOpCodeHandler;
import com.negretenico.friendly.config.NoOpHandler;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.OPCode;
import com.negretenico.friendly.service.StackOperationService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;
import java.util.function.BiFunction;

@Service
public class BooleanArithmeticService {

    private final StackOperationService pairOperationService;

    private final Map<OPCode, BiFunction<EVMContext, StackOperationService, Result<BigInteger>>> handlers =
            Map.ofEntries(
                    Map.entry(OPCode.AND, (ctx, pos) -> pos.handle(ctx.stack(), BooleanArithmeticOpCodeHandler::and)),
                    Map.entry(OPCode.OR,  (ctx, pos) -> pos.handle(ctx.stack(), BooleanArithmeticOpCodeHandler::or)),
                    Map.entry(OPCode.XOR, (ctx, pos) -> pos.handle(ctx.stack(), BooleanArithmeticOpCodeHandler::xor)),
                    Map.entry(OPCode.EQ,  (ctx, pos) -> pos.handle(ctx.stack(), BooleanArithmeticOpCodeHandler::equals)),
                    Map.entry(OPCode.LT,  (ctx, pos) -> pos.handle(ctx.stack(), BooleanArithmeticOpCodeHandler::lessThan)),
                    Map.entry(OPCode.GT,  (ctx, pos) -> pos.handle(ctx.stack(), BooleanArithmeticOpCodeHandler::greaterThan)),
                    Map.entry(OPCode.ISZERO, (ctx, pos) -> pos.handleOne(ctx.stack(), BooleanArithmeticOpCodeHandler::isZero)),
                    Map.entry(OPCode.NOT,    (ctx, pos) -> pos.handleOne(ctx.stack(), BooleanArithmeticOpCodeHandler::not))
            );

    public BooleanArithmeticService(StackOperationService pairOperationService) {
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
