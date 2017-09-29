/* 
 * 作者：钟勋 (e-mail:zhongxunking@163.com)
 */

/*
 * 修订记录:
 * @author 钟勋 2017-04-08 02:40 创建
 */
package demo.flow.modifyAccount;

import demo.dao.ModifyAccountDao;
import demo.entity.ModifyAccount;
import demo.enums.ModifyAccountStatus;
import org.bekit.flow.annotation.listener.ListenFlowException;
import org.bekit.flow.annotation.listener.ListenNodeDecided;
import org.bekit.flow.annotation.listener.TheFlowListener;
import org.bekit.flow.engine.TargetContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 修改账务流程监听器
 */
@TheFlowListener(flow = "modifyAccountFlow", priority = 2)
public class ModifyAccountListener2 {
    private static final Logger logger = LoggerFactory.getLogger(ModifyAccountListener2.class);

    @Autowired
    private ModifyAccountDao modifyAccountDao;

    @ListenNodeDecided
    public void listenNodeDecide(String node, TargetContext<ModifyAccount> targetContext) {
        logger.info("ModifyAccountListener2.listenNodeDecide");
    }

    @ListenFlowException(priorityAsc = false)
    public void listenFlowException(Throwable throwable, TargetContext<ModifyAccount> targetContext) {
        logger.info("ModifyAccountListener2.listenFlowException");
    }

}
