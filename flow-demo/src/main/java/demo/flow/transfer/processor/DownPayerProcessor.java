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
import demo.enums.ModifyAccountStatus;
import demo.enums.ModifyAccountType;
import demo.enums.ResultStatus;
import demo.utils.OID;
import org.bekit.flow.FlowEngine;
import org.bekit.flow.annotation.processor.*;
import org.bekit.flow.annotation.processor.Error;
import org.bekit.flow.engine.TargetContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeoutException;

/**
 * 付款方下账处理器
 */
@Processor      // @Processor是处理器注解，执行顺序：@Before——>@Execute——>@After——>@End，当发生异常时会执行@Error
public class DownPayerProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DownPayerProcessor.class);

    @Autowired
    private FlowEngine flowEngine;

    @Before     // 可选
    public void before(TargetContext<Transfer> targetContext) {
        // 为方便演示，只打印日志
        logger.info("执行DownPayerProcessor.before");
    }

    @Execute    // 必选（本方法的返回值会作为整个处理器的返回值）
    public ResultStatus execute(TargetContext<Transfer> targetContext) throws TimeoutException {
        logger.info("执行DownPayerProcessor.execute");

        // 在这里调用修改账务子流程进行具体的修改用户的账
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
    }

    @After      // 可选
    public void after(TargetContext<Transfer> targetContext) {
        logger.info("执行DownPayerProcessor.after");
    }

    @End        // 可选
    public void end(TargetContext<Transfer> targetContext) {
        logger.info("执行DownPayerProcessor.end");
    }

    @Error      // 可选
    public void error(TargetContext<Transfer> targetContext) {
        logger.error("执行DownPayerProcessor.error");
    }

    private ModifyAccount buildModifyAccount(Transfer transfer) {
        ModifyAccount modifyAccount = new ModifyAccount();
        modifyAccount.setTransferBizNo(transfer.getBizNo());
        modifyAccount.setTransferStatus(transfer.getStatus());
        modifyAccount.setType(ModifyAccountType.NORMAL);
        modifyAccount.setAccountNo(transfer.getPayerAccountNo());
        modifyAccount.setDirection(Direction.DOWN);
        modifyAccount.setAmount(transfer.getAmount());
        modifyAccount.setStatus(ModifyAccountStatus.MODIFY);
        modifyAccount.setRefOrderNo(OID.newId());

        return modifyAccount;
    }

}
