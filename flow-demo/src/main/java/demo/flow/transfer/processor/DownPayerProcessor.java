/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:54 创建
 */
package demo.flow.transfer.processor;

import demo.entity.ModifyAccount;
import demo.entity.Transfer;
import demo.enums.Direction;
import demo.enums.ModifyAccountType;
import demo.enums.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import top.bekit.flow.FlowEngine;
import top.bekit.flow.annotation.processor.*;
import top.bekit.flow.annotation.processor.Error;
import top.bekit.flow.engine.TargetContext;

import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 *
 */
@Processor
public class DownPayerProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DownPayerProcessor.class);
    private static final Random RANDOM = new Random();

    @Autowired
    private FlowEngine flowEngine;

    @Before
    public void before(TargetContext<Transfer> targetContext) {
        logger.info("执行DownPayerProcessor.before");
    }

    @Execute
    public ResultStatus execute(TargetContext<Transfer> targetContext) throws TimeoutException {
        logger.info("执行DownPayerProcessor.execute");

        ModifyAccount modifyAccount = buildModifyAccount(targetContext.getTarget());
        modifyAccount = flowEngine.insertTargetAndStart("modifyAccountFlow", modifyAccount, null);
        switch (modifyAccount.getStatus()) {
            case SUCCESS:
                return ResultStatus.SUCCESS;
            case FAIL:
                return ResultStatus.FAIL;
            default:
                return ResultStatus.PROCESS;
        }

//        switch (RANDOM.nextInt(4)) {
//            case 0:
//                return ResultStatus.SUCCESS;
//            case 1:
//                return ResultStatus.FAIL;
//            case 2:
//                return ResultStatus.PROCESS;
//            default:
//                throw new TimeoutException("模拟调用账务系统超时");
//        }
    }

    private ModifyAccount buildModifyAccount(Transfer transfer) {
        ModifyAccount modifyAccount = new ModifyAccount();
        modifyAccount.setTransferBizNo(transfer.getBizNo());
        modifyAccount.setTransferStatus(transfer.getStatus());
        modifyAccount.setType(ModifyAccountType.NORMAL);
        modifyAccount.setAccountNo(transfer.getPayerAccountNo());
        modifyAccount.setDirection(Direction.DOWN);
        modifyAccount.setAmount(transfer.getAmount());

        return modifyAccount;
    }

    @After
    public void after(TargetContext<Transfer> targetContext) {
        logger.info("执行DownPayerProcessor.after");
    }

    @End
    public void end(TargetContext<Transfer> targetContext) {
        logger.info("执行DownPayerProcessor.end");
    }

    @Error
    public void error(TargetContext<Transfer> targetContext) {
        logger.error("执行DownPayerProcessor.error");
    }
}
