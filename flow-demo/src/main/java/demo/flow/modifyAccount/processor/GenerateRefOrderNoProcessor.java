/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 01:54 创建
 */
package demo.flow.modifyAccount.processor;

import demo.dao.ModifyAccountDao;
import demo.entity.ModifyAccount;
import demo.enums.ResultStatus;
import demo.utils.OID;
import org.bekit.flow.annotation.processor.Processor;
import org.bekit.flow.annotation.processor.ProcessorExecute;
import org.bekit.flow.engine.FlowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeoutException;

/**
 * 生成调用账务系统的订单号处理器
 */
@Processor
public class GenerateRefOrderNoProcessor {
    private static final Logger logger = LoggerFactory.getLogger(GenerateRefOrderNoProcessor.class);

    @Autowired
    private ModifyAccountDao modifyAccountDao;

    @ProcessorExecute
    public ResultStatus execute(FlowContext<ModifyAccount> context) throws TimeoutException {
        logger.info("执行GenerateRefOrderNoProcessor.execute");

        ModifyAccount modifyAccount = context.getTarget();
        modifyAccount.setRefOrderNo(OID.newId());
        modifyAccountDao.save(modifyAccount);

        return ResultStatus.SUCCESS;
    }
}
