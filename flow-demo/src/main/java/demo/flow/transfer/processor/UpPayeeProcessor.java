/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:54 创建
 */
package demo.flow.transfer.processor;

import demo.entity.Transfer;
import demo.enums.ResultStatus;
import demo.utils.AccountMock;
import lombok.extern.slf4j.Slf4j;
import org.bekit.flow.annotation.processor.Processor;
import org.bekit.flow.annotation.processor.ProcessorExecute;
import org.bekit.flow.engine.FlowContext;

import java.util.concurrent.TimeoutException;

/**
 * 收款人上账处理器
 */
@Processor
@Slf4j
public class UpPayeeProcessor {

    @ProcessorExecute
    public ResultStatus execute(FlowContext<Transfer> context) throws TimeoutException {
        log.info("执行处理器[UpPayeeProcessor]");
        Transfer transfer = context.getTarget();
        return AccountMock.upAccount(transfer.getPayeeAccountId(), transfer.getAmount());
    }
}
