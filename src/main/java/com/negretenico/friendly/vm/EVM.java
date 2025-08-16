package com.negretenico.friendly.vm;

import com.common.functionico.evaluation.Result;
import com.negretenico.friendly.models.EVMCode;
import com.negretenico.friendly.models.EVMContext;
import com.negretenico.friendly.models.OPCode;
import com.negretenico.friendly.service.GasChargeService;
import com.negretenico.friendly.service.vm.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

import static com.negretenico.friendly.config.OpCodeConfig.*;

@Component
public class EVM {
    private   BigInteger gas = new BigInteger("1000000");
    private final GasChargeService gasChargeService;
    private final EVMCode[] codes;
    private final StorageService service;
    private  final MemService memService;
    private EVMContext evmContext;
    private final MiscService miscService;
    private final ArithmeticService arithmeticService;
    private final BooleanArithmeticService booleanArithmeticService;
    private  final JumpService jumpService;
    private final SwapService swapService;
    private final PushService pushService;
    private final DupService dupService;
    public EVM(GasChargeService gasChargeService, EVMCode[] codes, StorageService service, MemService memService, EVMContext evmContext, MiscService miscService, ArithmeticService arithmeticService, BooleanArithmeticService booleanArithmeticService, JumpService jumpService, SwapService swapService, PushService pushService, DupService dupService) {
        this.gasChargeService = gasChargeService;
        this.codes = codes;
        this.service = service;
        this.memService = memService;
        this.evmContext = evmContext;
        this.miscService = miscService;
        this.arithmeticService = arithmeticService;
        this.booleanArithmeticService = booleanArithmeticService;
        this.jumpService = jumpService;
        this.swapService = swapService;
        this.pushService = pushService;
        this.dupService = dupService;
    }

    public void run(){
        int PC = evmContext.PC();
        System.out.println("Starting the EVM");
        System.out.println("Program counter "+PC);
        System.out.println("Codes "+codes.length);
        while(PC < codes.length){
            EVMCode currentCode = codes[evmContext.PC()];
            evmContext = evmContext.updatePC(evmContext.PC() + 1); // ne

            gas= gasChargeService.charge(currentCode,gas);
            System.out.println("Exectuing command "+currentCode.name());
            switch (currentCode.opCode()){
                case STOP -> {
                    return;
                }
                case OPCode code when miscOpCodes.contains(code) ->{
                    Result<BigInteger> update = miscService.handle(evmContext,
                            code);
                    evmContext = evmContext.updatePC(update.data().intValue());
                }
                case  OPCode code when arithmeticCodes.contains(code)->{
                    arithmeticService.handle(evmContext,code);
                }
                case OPCode code when booleanArithmeticCodes.contains(code)->{
                    booleanArithmeticService.handle(evmContext,code);
                }
                case OPCode code when memOpCodes.contains(code)->{
                    memService.handle(evmContext,code);
                }
                case OPCode code when storeOpCodes.contains(code)->{
                    service.handle(evmContext,code);
                }
                case OPCode code when jumpOpCodes.contains(code)->{
                    jumpService.handle(evmContext,code);
                }
                case OPCode code when pushOpCodes.contains(code)->{
                    pushService.handle(evmContext,code);
                }
                case OPCode code when swapOpCodes.contains(code) ->{
                    swapService.handle(evmContext,code);
                }
                case OPCode code when dupOpCOdes.contains(code)->{
                    dupService.handle(evmContext,code);
                }
                default ->
                        throw new IllegalStateException("Unexpected value: " + currentCode.opCode());
            }
        }
    }
}
