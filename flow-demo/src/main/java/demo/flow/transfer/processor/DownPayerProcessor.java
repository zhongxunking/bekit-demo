/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:54 创建
 */
package demo.flow.transfer.processor;

import demo.dao.ModifyAccountDao;
import demo.entity.ModifyAccount;
import demo.entity.Transfer;
import demo.enums.Direction;
import demo.enums.ModifyAccountStatus;
import demo.enums.ModifyAccountType;
import demo.enums.ResultStatus;
import demo.utils.OID;
import org.bekit.flow.FlowEngine;
import org.bekit.flow.annotation.processor.Processor;
import org.bekit.flow.annotation.processor.ProcessorExecute;
import org.bekit.flow.engine.FlowContext;
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
    private ModifyAccountDao modifyAccountDao;
    @Autowired
    private FlowEngine flowEngine;

    @ProcessorExecute    // 必选（本方法的返回值会作为整个处理器的返回值）
    public ResultStatus execute(FlowContext<Transfer> context) throws TimeoutException {
        logger.info("执行DownPayerProcessor.execute");

        // 在这里调用修改账务子流程进行具体的修改用户的账
        ModifyAccount modifyAccount = buildModifyAccount(context.getTarget());
        modifyAccountDao.save(modifyAccount);
        modifyAccount = flowEngine.execute("modifyAccountFlow", modifyAccount);
        switch (modifyAccount.getStatus()) {
            case SUCCESS:
                return ResultStatus.SUCCESS;
            case FAIL:
                return ResultStatus.FAIL;
            default:
                return ResultStatus.PROCESS;
        }
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
