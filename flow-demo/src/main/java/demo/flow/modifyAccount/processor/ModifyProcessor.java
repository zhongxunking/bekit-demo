/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:54 创建
 */
package demo.flow.modifyAccount.processor;

import demo.entity.ModifyAccount;
import demo.enums.ResultStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.bekit.flow.annotation.processor.*;
import top.bekit.flow.annotation.processor.Error;
import top.bekit.flow.engine.TargetContext;

import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 *
 */
@Processor
public class ModifyProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ModifyProcessor.class);
    private static final Random RANDOM = new Random();

    @Before
    public void before(TargetContext<ModifyAccount> targetContext) {
        logger.info("执行ModifyProcessor.before");
    }

    @Execute
    public ResultStatus execute(TargetContext<ModifyAccount> targetContext) throws TimeoutException {
        logger.info("执行ModifyProcessor.execute");

        switch (RANDOM.nextInt(4)) {
            case 0:
                return ResultStatus.SUCCESS;
            case 1:
                return ResultStatus.FAIL;
            case 2:
                return ResultStatus.PROCESS;
            default:
                return ResultStatus.PROCESS;
//                throw new TimeoutException("模拟调用账务系统超时");
        }
    }

    @After
    public void after(TargetContext<ModifyAccount> targetContext) {
        logger.info("执行ModifyProcessor.after");
    }

    @End
    public void end(TargetContext<ModifyAccount> targetContext) {
        logger.info("执行ModifyProcessor.end");
    }

    @Error
    public void error(TargetContext<ModifyAccount> targetContext) {
        logger.error("执行ModifyProcessor.error");
    }
}
