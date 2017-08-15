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
import org.bekit.flow.annotation.processor.*;
import org.bekit.flow.annotation.processor.Error;
import org.bekit.flow.engine.TargetContext;
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

    @Before
    public void before(TargetContext<ModifyAccount> targetContext) {
        logger.info("执行GenerateRefOrderNoProcessor.before");
    }

    @Execute
    public ResultStatus execute(TargetContext<ModifyAccount> targetContext) throws TimeoutException {
        logger.info("执行GenerateRefOrderNoProcessor.execute");

        ModifyAccount modifyAccount = targetContext.getTarget();
        modifyAccount.setRefOrderNo(OID.newId());
        modifyAccountDao.save(modifyAccount);

        return ResultStatus.SUCCESS;
    }

    @After
    public void after(TargetContext<ModifyAccount> targetContext) {
        logger.info("执行GenerateRefOrderNoProcessor.after");
    }

    @End
    public void end(TargetContext<ModifyAccount> targetContext) {
        logger.info("执行GenerateRefOrderNoProcessor.end");
    }

    @Error
    public void error(TargetContext<ModifyAccount> targetContext) {
        logger.error("执行GenerateRefOrderNoProcessor.error");
    }
}
