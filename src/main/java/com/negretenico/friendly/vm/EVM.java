package com.negretenico.friendly.vm;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.exception.StackSizeException;
import com.negretenico.friendly.models.EVMCode;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.EVMStack;
import com.negretenico.friendly.models.OPCode;
import com.negretenico.friendly.service.GasChargeService;
import com.negretenico.friendly.service.StackOperationService;
import com.negretenico.friendly.service.vm.*;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.negretenico.friendly.config.OpCodeConfig.*;

@Component
public class EVM {
    private   BigInteger gas = new BigInteger("1000000");
    private final GasChargeService gasChargeService;
    private final EVMCode[] codes;
    private final StorageService service;
    private  final MemService memService;
    private EVMContext context;
    private final MiscService miscService;
    private final ArithmeticService arithmeticService;
    private final BooleanArithmeticService booleanArithmeticService;
    private  final JumpService jumpService;
    private final SwapService swapService;
    private final PushService pushService;
    private final DupService dupService;
    public EVM(GasChargeService gasChargeService, EVMCode[] codes, StorageService service, MemService memService, EVMContext context, MiscService miscService, ArithmeticService arithmeticService, BooleanArithmeticService booleanArithmeticService, JumpService jumpService, SwapService swapService, PushService pushService, DupService dupService) {
        this.gasChargeService = gasChargeService;
        this.codes = codes;
        this.service = service;
        this.memService = memService;
        this.context = context;
        this.miscService = miscService;
        this.arithmeticService = arithmeticService;
        this.booleanArithmeticService = booleanArithmeticService;
        this.jumpService = jumpService;
        this.swapService = swapService;
        this.pushService = pushService;
        this.dupService = dupService;
    }

    public void run(){
        int PC = context.PC();
        while(PC < codes.length){
            EVMCode currentCode = codes[context.PC()];
            context = context.updatePC(context.PC() + 1); // ne

            gas= gasChargeService.charge(currentCode,gas);
            switch (currentCode.opCode()){
                case STOP -> {
                    return;
                }
                case OPCode code when miscOpCodes.contains(code) ->{
                    Result<BigInteger> update = miscService.handle(context,
                            code);
                    context = context.updatePC(update.data().intValue());
                }
                case  OPCode code when arithmeticCodes.contains(code)->{
                    arithmeticService.handle(context,code);
                }
                case OPCode code when booleanArithmeticCodes.contains(code)->{
                    booleanArithmeticService.handle(context,code);
                }
                case OPCode code when memOpCodes.contains(code)->{
                    memService.handle(context,code);
                }
                case OPCode code when storeOpCodes.contains(code)->{
                    service.handle(context,code);
                }
                case OPCode code when jumpOpCodes.contains(code)->{
                    jumpService.handle(context,code);
                }
                case OPCode code when pushOpCodes.contains(code)->{
                    pushService.handle(context,code);
                }
                case OPCode code when swapOpCodes.contains(code) ->{
                    swapService.handle(context,code);
                }
                case OPCode code when dupOpCOdes.contains(code)->{
                    dupService.handle(context,code);
                }
                default ->
                        throw new IllegalStateException("Unexpected value: " + currentCode.opCode());
            }
        }
    }
}
