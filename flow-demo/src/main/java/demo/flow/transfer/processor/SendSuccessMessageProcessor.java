/* 
 * 作者：钟勋 (email:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2020-02-08 18:05 创建
 */
package demo.flow.transfer.processor;

import demo.entity.Transfer;
import lombok.extern.slf4j.Slf4j;
import org.bekit.flow.annotation.processor.Processor;
import org.bekit.flow.annotation.processor.ProcessorExecute;
import org.bekit.flow.engine.FlowContext;

/**
 * 发送成功通知处理器
 */
@Processor
@Slf4j
public class SendSuccessMessageProcessor {
    @ProcessorExecute
    public void execute(FlowContext<Transfer> context) {
        log.info("执行处理器[sendSuccessMessageProcessor]");
        log.info("模拟发送转账成功通知");
    }
}
