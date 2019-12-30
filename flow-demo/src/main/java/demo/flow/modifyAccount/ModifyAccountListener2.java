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
import org.bekit.event.listener.PriorityType;
import org.bekit.flow.annotation.listener.ListenDecidedNode;
import org.bekit.flow.annotation.listener.ListenFlowException;
import org.bekit.flow.annotation.listener.TheFlowListener;
import org.bekit.flow.engine.FlowContext;
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

    @ListenDecidedNode
    public void listenNodeDecided(String node, FlowContext<ModifyAccount> context) {
        logger.info("ModifyAccountListener2.listenNodeDecide");
    }

    @ListenFlowException(priorityType = PriorityType.DESC)
    public void listenFlowException(Throwable throwable, FlowContext<ModifyAccount> context) {
        logger.info("ModifyAccountListener2.listenFlowException");
    }

}
