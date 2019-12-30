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
import org.bekit.flow.annotation.processor.Processor;
import org.bekit.flow.annotation.processor.ProcessorExecute;
import org.bekit.flow.engine.FlowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.TimeoutException;

/**
 * 修改用户账处理器
 */
@Processor
public class ModifyProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ModifyProcessor.class);
    private static final Random RANDOM = new Random();

    @ProcessorExecute
    public ResultStatus execute(FlowContext<ModifyAccount> context) throws TimeoutException {
        logger.info("执行ModifyProcessor.execute");

        // 这里以随机数模拟调用账务系统的结果
        switch (RANDOM.nextInt(4)) {
            case 0:
                logger.info("模拟账务系统返回成功");
                return ResultStatus.SUCCESS;
            case 1:
                logger.info("模拟账务系统返回失败");
                return ResultStatus.FAIL;
            case 2:
                logger.info("模拟账务系统返回处理中");
                return ResultStatus.PROCESS;
            default:
                throw new TimeoutException("模拟调用账务系统超时");
        }
    }
}
